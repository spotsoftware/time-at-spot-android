package it.spot.android.timespot.organization;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import java.util.List;

import it.spot.android.timespot.HomeActivity;
import it.spot.android.timespot.R;
import it.spot.android.timespot.api.ClientService;
import it.spot.android.timespot.api.OrganizationService;
import it.spot.android.timespot.api.ProjectService;
import it.spot.android.timespot.api.TimeEndpoint;
import it.spot.android.timespot.api.domain.Client;
import it.spot.android.timespot.api.domain.Organization;
import it.spot.android.timespot.api.domain.Project;
import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import it.spot.android.timespot.core.BaseActivity;
import it.spot.android.timespot.databinding.ActivityChooseOrganizationBinding;
import it.spot.android.timespot.storage.Storage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a.rinaldi
 */
public class ChooseOrganizationActivity
        extends BaseActivity
        implements ChooseOrganizationAdapter.Listener {

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

        getSupportActionBar().setTitle(R.string.choose_organization);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        mAdapter = new ChooseOrganizationAdapter(this, this);

        mBinding.list.setLayoutManager(new LinearLayoutManager(this));
        mBinding.list.setHasFixedSize(true);
        mBinding.list.setAdapter(mAdapter);

        List<Organization> organizations = Storage.init(this).getOrganizations();
        if (organizations == null || organizations.size() == 0) {
            queryOrganizations();

        } else {
            mAdapter.setOrganizations(organizations);
        }
    }

    @Override
    protected String getDescription() {
        return "Select organization page";
    }

    // endregion

    // region Private methods

    private void queryOrganizations() {
        TimeEndpoint.getInstance(this)
                .create(OrganizationService.class)
                .get(TimeAuthenticatorHelper.getUserId(this, TimeAuthenticatorHelper.getAccount(this)))
                .enqueue(new Callback<List<Organization>>() {

                    @Override
                    public void onResponse(Call<List<Organization>> call, Response<List<Organization>> response) {
                        if (response.isSuccessful()) {
                            Storage.init(ChooseOrganizationActivity.this).setOrganizations(response.body());
                            mAdapter.setOrganizations(response.body());
                            mAdapter.notifyDataSetChanged();

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

    private void syncProjects(String organizationId) {

        TimeEndpoint.getInstance(this)
                .create(ProjectService.class)
                .get(organizationId)
                .enqueue(new Callback<List<Project>>() {

                    @Override
                    public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                        if (response.isSuccessful()) {
                            Storage.init(ChooseOrganizationActivity.this).setProjects(response.body());
                            HomeActivity.start(ChooseOrganizationActivity.this);
                            finish();

                        } else {
                            // INF: Empty
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Project>> call, Throwable t) {
                        Log.e("ChooseOrganizationActi", t.getMessage());
                    }
                });
    }

    private void syncClients(final String organizationId) {

        TimeEndpoint.getInstance(this)
                .create(ClientService.class)
                .get(organizationId)
                .enqueue(new Callback<List<Client>>() {

                    @Override
                    public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                        if (response.isSuccessful()) {
                            Storage.init(ChooseOrganizationActivity.this).setClients(response.body());
                            syncProjects(organizationId);

                        } else {
                            // INF: Empty
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Client>> call, Throwable t) {
                        Log.e("ChooseOrganizationActi", t.getMessage());
                    }
                });
    }

    // endregion

    // region ChooseOrganizationAdapter.Listener implementation

    @Override
    public void onAddOrganizationClicked() {

    }

    @Override
    public void onOrganizationClicked(Organization organization) {
        String organizationId = organization.get_id();
        Storage.init(this).setCurrentOrganizationId(organizationId);
        syncClients(organizationId);
    }

    // endregion

    // region RealmChangeListener implementation

//    @Override
//    public void onChange(RealmResults<Organization> element) {
//        if (element.size() > 0) {
//            mAdapter.setOrganizations(element);
//            mAdapter.notifyDataSetChanged();
//
//        } else {

//        }
//    }

    // endregion
}
