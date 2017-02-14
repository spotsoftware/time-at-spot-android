package it.spot.android.timespot.customer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import it.spot.android.timespot.api.ClientService;
import it.spot.android.timespot.api.TimeEndpoint;
import it.spot.android.timespot.databinding.FragmentClientsBinding;
import it.spot.android.timespot.domain.Client;
import it.spot.android.timespot.storage.Storage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a.rinaldi
 */
public class ClientsFragment
        extends Fragment
        implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, RealmChangeListener<RealmResults<Client>> {

    private ClientsAdapter mAdapter;
    private FragmentClientsBinding mBinding;

    // region Fragment life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentClientsBinding.inflate(inflater, container, false);

        mAdapter = new ClientsAdapter(getActivity());
        mBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.list.setHasFixedSize(true);
        mBinding.list.setAdapter(mAdapter);

        mBinding.setListener(this);
        mBinding.swipeRefresh.setOnRefreshListener(this);

        mBinding.addButton.setTransitionName("reveal");

        Realm.getDefaultInstance().where(Client.class).equalTo("active", true).findAllAsync().addChangeListener(this);

        return mBinding.getRoot();
    }

    // endregion

    // region Private methods

    private void queryClients() {

        TimeEndpoint.getInstance(getActivity())
                .create(ClientService.class)
                .get(Storage.init(getActivity()).getCurrentOrganizationId())
                .enqueue(new Callback<List<Client>>() {

                    @Override
                    public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getActivity(), "success " + response.body().size(), Toast.LENGTH_LONG).show();
                            Log.e("CLIENTS", "success " + response.body().size());
                            mAdapter.setClients(response.body());
                            mBinding.swipeRefresh.setRefreshing(false);

                        } else {
                            Log.e("CLIENTS", "error");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Client>> call, Throwable t) {
                        Log.e("CLIENTS", "errorrrrr");
                    }
                });
    }

    // endregion

    // region View.OnClickListener implementation

    @Override
    public void onClick(View v) {
        // INF: Empty
    }

    // endregion

    // region SwipeRefreshLayout.OnRefreshListener implementation

    @Override
    public void onRefresh() {
        mBinding.swipeRefresh.setRefreshing(true);
        queryClients();
    }

    // endregion

    // region RealmChangeListener<RealmResults<Project>> implementation

    @Override
    public void onChange(RealmResults<Client> element) {
        if (element != null && element.size() > 0) {
            mAdapter.setClients(element);

        } else {
            queryClients();
        }
    }

    // endregion
}