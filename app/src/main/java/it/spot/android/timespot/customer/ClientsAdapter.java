package it.spot.android.timespot.customer;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.spot.android.timespot.databinding.ListItemClientBinding;
import it.spot.android.timespot.databinding.ListItemProjectBinding;
import it.spot.android.timespot.domain.Client;
import it.spot.android.timespot.domain.Project;

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
