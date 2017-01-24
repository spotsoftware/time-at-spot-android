package it.spot.android.timespot;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.spot.android.timespot.databinding.ActivityLoginBinding;

/**
 * @author a.rinaldi
 */
public class LoginActivity
        extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int REQUEST_CODE_SIGN_IN = 0;

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

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://192.168.100.71:9000/api/session/google/validate");
                        try {
                            List nameValuePairs = new ArrayList(1);
                            nameValuePairs.add(new BasicNameValuePair("id_token", result.getSignInAccount().getIdToken()));
                            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                            HttpResponse response = httpClient.execute(httpPost);
                            int statusCode = response.getStatusLine().getStatusCode();
                            final String responseBody = EntityUtils.toString(response.getEntity());
                            Log.i("LOGINACTIVITY", "Signed in as: " + responseBody);
                        } catch (ClientProtocolException e) {
//                    Log.e(TAG, "Error sending ID token to backend.", e);
                        } catch (IOException e) {
//                    Log.e(TAG, "Error sending ID token to backend.", e);
                        }
                    }
                }).start();
            }
        }
    }

    // endregion

    // region GoogleApiClient.OnConnectionFailedListener implementation

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // endregion

    // region View.OnClickListener implementation

    @Override
    public void onClick(View v) {
        if (v.equals(mBinding.googleLoginButton)) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
        }
    }

    // endregion
}
