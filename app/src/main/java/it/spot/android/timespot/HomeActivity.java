package it.spot.android.timespot;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import it.spot.android.timespot.databinding.ActivityHomeBinding;

/**
 * @author a.rinaldi
 */
public class HomeActivity
        extends AppCompatActivity
        implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mBinding.navigation.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        mBinding.drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();
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

    // region NavigationView.OnNavigationItemSelectedListener implementation

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        item.setChecked(!item.isChecked());
        mBinding.drawerLayout.closeDrawers();

        switch (item.getItemId()) {
            case R.id.work_entries:
                getSupportFragmentManager().beginTransaction()
                        .replace(mBinding.content.getId(), new WorkEntriesFragment())
                        .commit();
                break;

            default:
                break;
        }

        return true;
    }

    // endregion
}
