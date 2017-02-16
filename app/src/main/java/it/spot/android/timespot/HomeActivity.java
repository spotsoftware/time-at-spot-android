package it.spot.android.timespot;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import it.spot.android.timespot.api.AuthService;
import it.spot.android.timespot.api.TimeEndpoint;
import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import it.spot.android.timespot.client.ClientsFragment;
import it.spot.android.timespot.core.BaseActivity;
import it.spot.android.timespot.databinding.ActivityHomeBinding;
import it.spot.android.timespot.project.ProjectsFragment;
import it.spot.android.timespot.storage.Storage;
import it.spot.android.timespot.workentry.WorkEntriesFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a.rinaldi
 */
public class HomeActivity
        extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        onNavigationItemSelected(mBinding.navigation.getMenu().getItem(0));

        drawerToggle.syncState();
    }

    @Override
    protected String getDescription() {
        return "Home page";
    }

    // endregion

    // region Private methods

    private void logout() {
        TimeEndpoint.getInstance(this)
                .create(AuthService.class)
                .logout()
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    TimeAuthenticatorHelper.removeAccount(HomeActivity.this, TimeAuthenticatorHelper.getAccount(HomeActivity.this));
                                    Storage.init(HomeActivity.this).clear();
                                    LoginActivity.start(HomeActivity.this);
                                }
                            }).start();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // INF: Empty
                    }
                });
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

            case R.id.clients:
                getSupportFragmentManager().beginTransaction()
                        .replace(mBinding.content.getId(), new ClientsFragment())
                        .commit();
                break;

            case R.id.projects:
                getSupportFragmentManager().beginTransaction()
                        .replace(mBinding.content.getId(), new ProjectsFragment())
                        .commit();
                break;

            case R.id.logout:
                logout();
                break;

            default:
                break;
        }

        return true;
    }

    // endregion
}
