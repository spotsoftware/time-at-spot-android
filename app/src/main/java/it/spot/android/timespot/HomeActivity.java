package it.spot.android.timespot;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import it.spot.android.timespot.api.AuthService;
import it.spot.android.timespot.api.TimeEndpoint;
import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import it.spot.android.timespot.databinding.ActivityHomeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a.rinaldi
 */
public class HomeActivity
        extends AppCompatActivity
        implements View.OnClickListener {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
    }

    private ActivityHomeBinding mBinding;

    // region Activity life cycle

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
    }

    // endregion

    // region View.OnClickListener implementation

    @Override
    public void onClick(View v) {

//        if (v.equals(mBinding.logoutButton)) {
//
//            TimeEndpoint.getInstance(this)
//                    .create(AuthService.class)
//                    .logout()
//                    .enqueue(new Callback<Void>() {
//
//                        @Override
//                        public void onResponse(Call<Void> call, Response<Void> response) {
//                            if (response.isSuccessful()) {
//                                TimeAuthenticatorHelper.removeAccount(HomeActivity.this, TimeAuthenticatorHelper.getAccount(HomeActivity.this));
//                                LoginActivity.start(HomeActivity.this);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Void> call, Throwable t) {
//
//                        }
//                    });
//        }
    }

    // endregion
}
