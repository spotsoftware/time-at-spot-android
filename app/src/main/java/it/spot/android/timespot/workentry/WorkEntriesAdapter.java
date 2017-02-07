package it.spot.android.timespot.workentry;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.spot.android.timespot.databinding.ListItemWorkEntryBinding;
import it.spot.android.timespot.domain.WorkEntry;

/**
 * @author a.rinaldi
 */
public class WorkEntriesAdapter
        extends RecyclerView.Adapter<WorkEntriesAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<WorkEntry> mEntries;

    // region Construction

    public WorkEntriesAdapter(Context context) {
        super();
        mEntries = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    // endregion

    // region Public methods

    public void setWorkEntries(List<WorkEntry> entries) {
        mEntries.addAll(entries);
        notifyItemRangeInserted(mEntries.size() - entries.size(), mEntries.size());
    }

    // endregion

    // region RecyclerView.Adapter implementation

    @Override
    public WorkEntriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ListItemWorkEntryBinding.inflate(mInflater, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(WorkEntriesAdapter.ViewHolder holder, int position) {
        WorkEntry entry = mEntries.get(position);
        holder.mBinding.description.setText(entry.getDescription());
        holder.mBinding.amount.setText(String.format("Ore: %f", entry.getAmount()));
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    // endregion

    protected static class ViewHolder
            extends RecyclerView.ViewHolder {

        ListItemWorkEntryBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
