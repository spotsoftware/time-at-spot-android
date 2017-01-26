package it.spot.android.timespot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.spot.android.timespot.databinding.FragmentWorkEntriesBinding;

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

        mAdapter = new WorkEntriesAdapter();
        mBinding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.list.setHasFixedSize(true);
        mBinding.list.setAdapter(mAdapter);

        mBinding.setListener(this);

        return mBinding.getRoot();
    }

    // endregion

    // region View.OnClickListener implementation

    @Override
    public void onClick(View v) {

    }

    // endregion
}
