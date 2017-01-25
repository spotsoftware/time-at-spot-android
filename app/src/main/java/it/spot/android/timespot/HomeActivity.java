package it.spot.android.timespot;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import it.spot.android.timespot.databinding.ActivityHomeBinding;

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
        mBinding.setListener(this);
    }

    // endregion

    // region View.OnClickListener implementation

    @Override
    public void onClick(View v) {
        if (v.equals(mBinding.logoutButton)) {
            TimeAuthenticatorHelper.removeAccount(this, TimeAuthenticatorHelper.getAccount(this));
            LoginActivity.start(this);
        }
    }

    // endregion
}
