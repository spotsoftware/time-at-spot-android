package it.spot.android.timespot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import it.spot.android.timespot.api.TimeEndpoint;
import it.spot.android.timespot.api.WorkEntryService;
import it.spot.android.timespot.api.request.WorkEntriesRequest;
import it.spot.android.timespot.api.response.WorkEntriesResponse;
import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import it.spot.android.timespot.databinding.FragmentWorkEntriesBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a.rinaldi
 */
public class WorkEntriesFragment
        extends Fragment
        implements View.OnClickListener {

    private WorkEntriesAdapter mAdapter;
    private FragmentWorkEntriesBinding mBinding;

    // region Fragment life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentWorkEntriesBinding.inflate(inflater, container, false);

        mAdapter = new WorkEntriesAdapter(getActivity());
        mBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.list.setHasFixedSize(true);
        mBinding.list.setAdapter(mAdapter);

        mBinding.setListener(this);

        mBinding.addButton.setTransitionName("reveal");

        TimeEndpoint.getInstance(getActivity())
                .create(WorkEntryService.class)
                .get("54e3061b9f11ec0b0035107e", 1, new WorkEntriesRequest()
                        .setFrom("2017-01-21T23:00:00.000Z")
                        .setTo("2017-01-28T22:59:59.999Z")
                        .setUserId(TimeAuthenticatorHelper.getUserId(getActivity(), TimeAuthenticatorHelper.getAccount(getActivity()))))
                .enqueue(new Callback<WorkEntriesResponse>() {
                    @Override
                    public void onResponse(Call<WorkEntriesResponse> call, Response<WorkEntriesResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getActivity(), "success " + response.body().getItems().size(), Toast.LENGTH_LONG).show();
                            Log.e("WORKENTRIE", "success " + response.body().getItems().size());
                            mAdapter.setWorkEntries(response.body().getItems());

                        } else {
                            Log.e("WORKENTRIE", "error");
                        }
                    }

                    @Override
                    public void onFailure(Call<WorkEntriesResponse> call, Throwable t) {
                        Log.e("WORKENTRIE", "errorrrrr");
                    }
                });

        return mBinding.getRoot();
    }

    // endregion

    // region View.OnClickListener implementation

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), WorkEntryNewActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), mBinding.addButton, "reveal");
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    // endregion
}
