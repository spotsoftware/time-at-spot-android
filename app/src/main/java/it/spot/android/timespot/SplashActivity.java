package it.spot.android.timespot;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import it.spot.android.timespot.auth.TimeAuthenticatorHelper;

public class SplashActivity
        extends AppCompatActivity
        implements Runnable {

    private Handler mHandler;

    // region Activity life cycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(this, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(this);
    }

    // endregion

    // region Runnable implementation

    @Override
    public void run() {
        if (TimeAuthenticatorHelper.getAccount(this) != null) {
            HomeActivity.start(this);

        } else {
            // TODO - check login status
            LoginActivity.start(this);
        }
    }

    // endregion
}
