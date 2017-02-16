package it.spot.android.timespot;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import it.spot.android.timespot.core.BaseActivity;
import it.spot.android.timespot.organization.ChooseOrganizationActivity;
import it.spot.android.timespot.storage.Storage;

public class SplashActivity
        extends BaseActivity
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
        mHandler.postDelayed(this, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(this);
    }

    @Override
    protected String getDescription() {
        return "Splash page";
    }

    // endregion

    // region Runnable implementation

    @Override
    public void run() {
        if (TimeAuthenticatorHelper.getAccount(this) != null) {
            if (TextUtils.isEmpty(Storage.init(this).getCurrentOrganizationId())) {
                ChooseOrganizationActivity.start(this);

            } else {
                HomeActivity.start(this);
            }

        } else {
            // TODO - check login status
            LoginActivity.start(this);
        }

        finish();
    }

    // endregion
}
