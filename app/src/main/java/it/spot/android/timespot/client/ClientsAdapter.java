package it.spot.android.timespot.client;

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
import it.spot.android.timespot.databinding.ListItemClientBinding;
import it.spot.android.timespot.domain.Client;

/**
 * @author a.rinaldi
 */
public class ClientsAdapter
        extends RecyclerView.Adapter<ClientsAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Client> mClients;

    // region Construction

    public ClientsAdapter(Context context) {
        super();
        mClients = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    // endregion

    // region Public methods

    public void setClients(List<Client> clients) {
        mClients.clear();
        mClients.addAll(clients);
        notifyDataSetChanged();
    }

    // endregion

    // region RecyclerView.Adapter implementation

    @Override
    public ClientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ListItemClientBinding.inflate(mInflater, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(ClientsAdapter.ViewHolder holder, int position) {
        Client client = mClients.get(position);
        holder.mBinding.name.setText(client.getName());

        Picasso
                .with(holder.itemView.getContext())
                .cancelRequest(holder.mBinding.icon);

        if (!TextUtils.isEmpty(client.getIcon())) {
            Picasso
                    .with(holder.itemView.getContext())
                    .load(client.getIcon())
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
        return mClients.size();
    }

    // endregion

    protected static class ViewHolder
            extends RecyclerView.ViewHolder {

        ListItemClientBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
