package it.spot.android.timespot;

import android.Manifest;
import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

import it.spot.android.timespot.api.AuthService;
import it.spot.android.timespot.api.TimeEndpoint;
import it.spot.android.timespot.api.domain.User;
import it.spot.android.timespot.api.request.AuthRequest;
import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import it.spot.android.timespot.core.BaseActivity;
import it.spot.android.timespot.databinding.ActivityLoginBinding;
import it.spot.android.timespot.notifications.DailyWorkEntryNotificationScheduler;
import it.spot.android.timespot.organization.ChooseOrganizationActivity;
import it.spot.android.timespot.storage.Storage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a.rinaldi
 */
public class LoginActivity
        extends BaseActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int REQUEST_CODE_SIGN_IN_AGAIN = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    private GoogleApiClient mGoogleApiClient;
    private ActivityLoginBinding mBinding;

    // region Activity life cycle

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("328110632119-votutohgtdhcb7qlao6t07vn0njcfu7s.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();

        mBinding.googleLoginButton.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGN_IN_AGAIN) {
            if (resultCode != Activity.RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.reconnect();
            }

        } else if (requestCode == REQUEST_CODE_SIGN_IN) {

            final GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {

                mSignInClicked = false;
                Snackbar.make(mBinding.getRoot(), "Welcome " + result.getSignInAccount().getIdToken(), Snackbar.LENGTH_LONG).show();

                TimeEndpoint.getInstance(getApplicationContext())
                        .create(AuthService.class)
                        .auth(new AuthRequest().setId_token(result.getSignInAccount().getIdToken()))
                        .enqueue(new Callback<User>() {

                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    Account account = TimeAuthenticatorHelper.addAccount(getApplicationContext(), response.body());
                                    if (account != null) {
                                        TimeAuthenticatorHelper.updateToken(getApplicationContext(), account, response.headers().get("Set-Cookie"));
                                        Storage.init(LoginActivity.this).setLoggedUser(response.body());
                                        DailyWorkEntryNotificationScheduler.newInstance().schedule(getApplicationContext());
                                        ChooseOrganizationActivity.start(LoginActivity.this);
                                        finish();

                                    } else {
                                        Log.e("LOGINACTIVITY", "account is null");
                                    }

                                } else {
                                    Log.e("LOGINACTIVITY", "not success " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Log.e("LOGINACTIVITY", "failure " + t.getMessage());
                            }
                        });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                attemptLogin();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected String getDescription() {
        return "Login page";
    }

    // endregion

    // region GoogleApiClient.OnConnectionFailedListener implementation

    private boolean mIntentInProgress;
    private boolean mSignInClicked;

    @Override
    public void onConnected(Bundle bundle) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
    }

    @Override
    public void onConnectionSuspended(int i) {
        // INF: Empty
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!mIntentInProgress) {
            if (mSignInClicked && connectionResult.hasResolution()) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                try {
                    connectionResult.startResolutionForResult(this, REQUEST_CODE_SIGN_IN_AGAIN);
                    mIntentInProgress = true;

                } catch (IntentSender.SendIntentException e) {
                    // The intent was canceled before it was sent.  Return to the default
                    // state and attempt to connect to get an updated ConnectionResult.
                    mIntentInProgress = false;
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    // endregion

    // region View.OnClickListener implementation

    @Override
    public void onClick(View v) {
        if (v.equals(mBinding.googleLoginButton)) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, PERMISSION_REQUEST_CODE);

            } else {
                attemptLogin();
            }
        }
    }

    // endregion

    // region Private methods

    private void attemptLogin() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int status = api.isGooglePlayServicesAvailable(this);
        if (status == ConnectionResult.SUCCESS) {
            if (!mGoogleApiClient.isConnecting()) {
                mSignInClicked = true;
                mGoogleApiClient.connect();
            }
        }
    }

    // endregion
}
