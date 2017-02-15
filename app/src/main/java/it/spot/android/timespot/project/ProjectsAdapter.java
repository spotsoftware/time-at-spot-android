package it.spot.android.timespot.project;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import it.spot.android.timespot.R;
import it.spot.android.timespot.databinding.ListItemProjectBinding;
import it.spot.android.timespot.domain.Project;

/**
 * @author a.rinaldi
 */
public class ProjectsAdapter
        extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Project> mProjects;

    // region Construction

    public ProjectsAdapter(Context context) {
        super();
        mProjects = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    // endregion

    // region Public methods

    public void setProjects(List<Project> entries) {
        mProjects.clear();
        mProjects.addAll(entries);
        notifyDataSetChanged();
    }

    // endregion

    // region RecyclerView.Adapter implementation

    @Override
    public ProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ListItemProjectBinding.inflate(mInflater, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(ProjectsAdapter.ViewHolder holder, int position) {
        Project project = mProjects.get(position);
        holder.mBinding.name.setText(project.getName());

        Picasso
                .with(holder.itemView.getContext())
                .cancelRequest(holder.mBinding.icon);

        if (!TextUtils.isEmpty(project.getIcon())) {
            Picasso
                    .with(holder.itemView.getContext())
                    .load(project.getIcon())
                    .fit()
                    .centerCrop()
                    .into(holder.mBinding.icon);

        } else {
            holder.mBinding.icon.setImageResource(R.drawable.ic_unknown);
        }

        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
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
