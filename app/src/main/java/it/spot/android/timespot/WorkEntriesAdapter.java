package it.spot.android.timespot;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author a.rinaldi
 */
public class WorkEntriesAdapter
        extends RecyclerView.Adapter<WorkEntriesAdapter.ViewHolder> {

    // region Construction

    public WorkEntriesAdapter() {
        super();
    }

    // endregion

    // region RecyclerView.Adapter implementation

    @Override
    public WorkEntriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(WorkEntriesAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // endregion

    protected static class ViewHolder
            extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
