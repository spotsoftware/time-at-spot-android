package it.spot.android.timespot.organization;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.spot.android.timespot.databinding.ListItemOrganizationBinding;
import it.spot.android.timespot.api.domain.Organization;

/**
 * @author a.rinaldi
 */
public class ChooseOrganizationAdapter
        extends RecyclerView.Adapter<ChooseOrganizationAdapter.ViewHolder>
        implements View.OnClickListener {

    private Listener mListener;
    private LayoutInflater mInflater;
    private List<Organization> mOrganizations;

    // region Construction

    public ChooseOrganizationAdapter(Context context, Listener listener) {
        super();
        mListener = listener;
        mOrganizations = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    // endregion

    // region RecyclerView.Adapter implementation

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ListItemOrganizationBinding.inflate(mInflater, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mBinding.getRoot().setOnClickListener(this);
        holder.mBinding.getRoot().setTag(String.valueOf(position));
        holder.mBinding.name.setText(mOrganizations.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mOrganizations.size();
    }

    // endregion

    // region Public methods

    public void setOrganizations(List<Organization> organizations) {
        mOrganizations.clear();
        mOrganizations.addAll(organizations);
    }

    // endregion

    // region View.OnClickListener implementation

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onOrganizationClicked(mOrganizations.get(Integer.valueOf((String) v.getTag())));
        }
    }

    // endregion

    protected static class ViewHolder
            extends RecyclerView.ViewHolder {

        ListItemOrganizationBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public interface Listener {

        void onOrganizationClicked(Organization organization);
    }
}
