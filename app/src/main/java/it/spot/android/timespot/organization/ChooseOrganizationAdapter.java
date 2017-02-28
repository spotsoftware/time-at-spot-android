package it.spot.android.timespot.organization;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.spot.android.timespot.api.domain.Organization;
import it.spot.android.timespot.databinding.ListItemOrganizationAddBinding;
import it.spot.android.timespot.databinding.ListItemOrganizationBinding;

/**
 * @author a.rinaldi
 */
public class ChooseOrganizationAdapter
        extends RecyclerView.Adapter<ChooseOrganizationAdapter.ViewHolder>
        implements View.OnClickListener {

    private static final int VIEW_TYPE_ORGANIZATION = 0;
    private static final int VIEW_TYPE_ORGANIZATION_ADD = 1;

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
        if (viewType == VIEW_TYPE_ORGANIZATION) {
            return new OrganizationViewHolder(ListItemOrganizationBinding.inflate(mInflater, parent, false).getRoot());

        } else {
            return new AddOrganizationViewHolder(ListItemOrganizationAddBinding.inflate(mInflater, parent, false).getRoot());
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder instanceof OrganizationViewHolder) {
            ((OrganizationViewHolder)holder).mBinding.getRoot().setOnClickListener(this);
            ((OrganizationViewHolder)holder).mBinding.getRoot().setTag(String.valueOf(position));
            ((OrganizationViewHolder)holder).mBinding.name.setText(mOrganizations.get(position).getName());
            ((OrganizationViewHolder)holder).mBinding.executePendingBindings();

        }else {
            ((AddOrganizationViewHolder)holder).mBinding.getRoot().setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return mOrganizations.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mOrganizations.size()) {
            return VIEW_TYPE_ORGANIZATION;

        } else {
            return VIEW_TYPE_ORGANIZATION_ADD;
        }
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
            String tag = (String) v.getTag();
            if (TextUtils.isEmpty(tag)) {
                mListener.onAddOrganizationClicked();

            } else {
                mListener.onOrganizationClicked(mOrganizations.get(Integer.valueOf(tag)));
            }
        }
    }

    // endregion

    protected static class ViewHolder
            extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected static class OrganizationViewHolder
            extends ViewHolder {

        ListItemOrganizationBinding mBinding;

        public OrganizationViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    protected static class AddOrganizationViewHolder
            extends ViewHolder {

        ListItemOrganizationAddBinding mBinding;

        public AddOrganizationViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public interface Listener {

        void onAddOrganizationClicked();

        void onOrganizationClicked(Organization organization);
    }
}
