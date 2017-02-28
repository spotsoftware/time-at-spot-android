package it.spot.android.timespot.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import it.spot.android.timespot.api.domain.Project;
import it.spot.android.timespot.core.BaseFragment;
import it.spot.android.timespot.core.HttpCallback;
import it.spot.android.timespot.databinding.FragmentProjectsBinding;
import it.spot.android.timespot.storage.IStorage;
import it.spot.android.timespot.storage.Storage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a.rinaldi
 */
public class ProjectsFragment
        extends BaseFragment
        implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, RealmChangeListener<Realm> {

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

        Realm.getDefaultInstance().addChangeListener(this);
        queryProjectsLocally();

        return mBinding.getRoot();
    }

    @Override
    protected String getDescription() {
        return "Projects list page";
    }

    // endregion

    // region Private methods

    private void queryProjects() {
        TimeEndpoint.getInstance(getActivity())
                .create(ProjectService.class)
                .get(Storage.init(getActivity()).getCurrentOrganizationId())
                .enqueue(new HttpCallback<>(new Callback<List<Project>>() {

                    @Override
                    public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {

                        if (response.isSuccessful()) {
                            Storage.init(getActivity()).setProjects(response.body());

                        } else {
                            Log.e("PROJECTS", "error");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Project>> call, Throwable t) {
                        Log.e("PROJECTS", "errorrrrr");
                    }
                }));
    }

    private void queryProjectsLocally() {
        Realm.getDefaultInstance().where(Project.class).equalTo("active", true).findAllSortedAsync("name").addChangeListener(new RealmChangeListener<RealmResults<Project>>() {

            @Override
            public void onChange(RealmResults<Project> element) {
                if (element != null && element.size() > 0) {
                    Toast.makeText(getActivity(), "success " + element.size(), Toast.LENGTH_LONG).show();
                    mAdapter.setProjects(element);
                    mBinding.swipeRefresh.setRefreshing(false);

                } else {
                    queryProjects();
                }
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
    public void onChange(Realm realm) {
        Log.e("Projects", "Realm.onChange triggered");
        queryProjectsLocally();
    }

    // endregion
}