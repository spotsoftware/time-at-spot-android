package it.spot.android.timespot.organization;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import it.spot.android.timespot.HomeActivity;
import it.spot.android.timespot.R;
import it.spot.android.timespot.api.OrganizationService;
import it.spot.android.timespot.api.TimeEndpoint;
import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import it.spot.android.timespot.databinding.ActivityChooseOrganizationBinding;
import it.spot.android.timespot.domain.Organization;
import it.spot.android.timespot.storage.Storage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a.rinaldi
 */
public class ChooseOrganizationActivity
        extends AppCompatActivity
        implements ChooseOrganizationAdapter.Listener, RealmChangeListener<RealmResults<Organization>> {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ChooseOrganizationActivity.class);
        activity.startActivity(intent);
    }

    private ChooseOrganizationAdapter mAdapter;
    private ActivityChooseOrganizationBinding mBinding;

    // region Activity life cycle

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_organization);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        mAdapter = new ChooseOrganizationAdapter(this, this);

        mBinding.list.setLayoutManager(new LinearLayoutManager(this));
        mBinding.list.setHasFixedSize(true);
        mBinding.list.setAdapter(mAdapter);

        Realm.getDefaultInstance().where(Organization.class).findAllAsync().addChangeListener(this);
    }

    // endregion

    // region ChooseOrganizationAdapter.Listener implementation

    @Override
    public void onOrganizationClicked(Organization organization) {
        Storage.init(this).setCurrentOrganizationId(organization.get_id());
        HomeActivity.start(this);
    }

    // endregion

    // region RealmChangeListener implementation

    @Override
    public void onChange(RealmResults<Organization> element) {
        if (element.size() > 0) {
            mAdapter.setOrganizations(element);
            mAdapter.notifyDataSetChanged();

        } else {
            TimeEndpoint.getInstance(this)
                    .create(OrganizationService.class)
                    .get(TimeAuthenticatorHelper.getUserId(this, TimeAuthenticatorHelper.getAccount(this)))
                    .enqueue(new Callback<List<Organization>>() {

                        @Override
                        public void onResponse(Call<List<Organization>> call, Response<List<Organization>> response) {
                            if (response.isSuccessful()) {
                                Storage.init(ChooseOrganizationActivity.this).setOrganizations(response.body());

                            } else {
                                // INF: Empty
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Organization>> call, Throwable t) {
                            // INF: Empty
                        }
                    });
        }
    }

    // endregion
}
