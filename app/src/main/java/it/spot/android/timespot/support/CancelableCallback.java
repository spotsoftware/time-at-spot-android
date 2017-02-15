package it.spot.android.timespot.support;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by a.rinaldi on 15/02/2017.
 */
public class CancelableCallback
        implements Callback {

    private Callback callback;

    private boolean canceled;

    public CancelableCallback(Callback callback) {
        this.callback = callback;
        canceled = false;
    }

    public void cancel() {
        canceled = true;
        callback = null;
    }

    @Override
    public void onResponse(Call call, Response response) {
        if (!canceled) {
            callback.onResponse(call, response);
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        if (!canceled) {
            callback.onFailure(call, t);
        }
    }
}
