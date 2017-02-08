package it.spot.android.timespot.project;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.spot.android.timespot.databinding.ListItemProjectBinding;
import it.spot.android.timespot.domain.Project;

/**
 * @author a.rinaldi
 */
public class ProjectsAdapter
        extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Project> mEntries;

    // region Construction

    public ProjectsAdapter(Context context) {
        super();
        mEntries = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    // endregion

    // region Public methods

    public void setProjects(List<Project> entries) {
        mEntries.clear();
        mEntries.addAll(entries);
        notifyItemRangeInserted(mEntries.size() - entries.size(), mEntries.size());
    }

    // endregion

    // region RecyclerView.Adapter implementation

    @Override
    public ProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ListItemProjectBinding.inflate(mInflater, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(ProjectsAdapter.ViewHolder holder, int position) {
        Project entry = mEntries.get(position);
        holder.mBinding.name.setText(entry.getName());
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    // endregion

    protected static class ViewHolder
            extends RecyclerView.ViewHolder {

        ListItemProjectBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
