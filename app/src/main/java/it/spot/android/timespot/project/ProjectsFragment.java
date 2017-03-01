package it.spot.android.timespot.project;

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
import it.spot.android.timespot.api.ProjectService;
import it.spot.android.timespot.api.TimeEndpoint;
import it.spot.android.timespot.databinding.FragmentProjectsBinding;
import it.spot.android.timespot.domain.Project;
import it.spot.android.timespot.storage.Storage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a.rinaldi
 */
public class ProjectsFragment
        extends Fragment
        implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, RealmChangeListener<RealmResults<Project>> {

    private ProjectsAdapter mAdapter;
    private FragmentProjectsBinding mBinding;

    // region Fragment life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentProjectsBinding.inflate(inflater, container, false);

        mAdapter = new ProjectsAdapter(getActivity());
        mBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.list.setHasFixedSize(true);
        mBinding.list.setAdapter(mAdapter);

        mBinding.setListener(this);
        mBinding.swipeRefresh.setOnRefreshListener(this);

        mBinding.addButton.setTransitionName("reveal");

        Realm.getDefaultInstance().where(Project.class).findAllAsync().addChangeListener(this);

        return mBinding.getRoot();
    }

    // endregion

    // region Private methods

    private void queryProjects() {

        TimeEndpoint.getInstance(getActivity())
                .create(ProjectService.class)
                .get(Storage.init(getActivity()).getCurrentOrganizationId())
                .enqueue(new Callback<List<Project>>() {

                    @Override
                    public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getActivity(), "success " + response.body().size(), Toast.LENGTH_LONG).show();
                            Log.e("PROJECTS", "success " + response.body().size());
                            mAdapter.setProjects(response.body());
                            mBinding.swipeRefresh.setRefreshing(false);

                        } else {
                            Log.e("PROJECTS", "error");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Project>> call, Throwable t) {
                        Log.e("PROJECTS", "errorrrrr");
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
        queryProjects();
    }

    // endregion

    // region RealmChangeListener<RealmResults<Project>> implementation

    @Override
    public void onChange(RealmResults<Project> element) {
        if (element != null && element.size() > 0) {
            mAdapter.setProjects(element);

        } else {
            queryProjects();
        }
    }

    // endregion
}