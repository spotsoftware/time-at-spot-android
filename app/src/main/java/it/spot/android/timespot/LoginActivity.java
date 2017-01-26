package it.spot.android.timespot;

import android.Manifest;
import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import it.spot.android.timespot.api.AuthService;
import it.spot.android.timespot.api.TimeEndpoint;
import it.spot.android.timespot.api.request.AuthRequest;
import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import it.spot.android.timespot.databinding.ActivityLoginBinding;
import it.spot.android.timespot.domain.User;
import it.spot.android.timespot.storage.Storage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a.rinaldi
 */
public class LoginActivity
        extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int PERMISSION_REQUEST_CODE = 1;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
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
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();

        mBinding.googleLoginButton.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            final GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Snackbar.make(mBinding.getRoot(), "Welcome " + result.getSignInAccount().getIdToken(), Snackbar.LENGTH_LONG).show();

                TimeEndpoint.getInstance(getApplicationContext())
                        .create(AuthService.class)
                        .auth(new AuthRequest().setId_token(result.getSignInAccount().getIdToken()))
                        .enqueue(new Callback<User>() {

                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    Account account = TimeAuthenticatorHelper.addAccount(getApplicationContext(), response.body());
                                    if(account != null) {
                                        TimeAuthenticatorHelper.updateToken(getApplicationContext(), account, response.headers().get("Set-Cookie"));
                                        Storage.init(LoginActivity.this).setLoggedUser(response.body());
                                        HomeActivity.start(LoginActivity.this);
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

    // endregion

    // region GoogleApiClient.OnConnectionFailedListener implementation

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, connectionResult.getErrorMessage(), Toast.LENGTH_LONG).show();
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
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
    }

    // endregion
}
