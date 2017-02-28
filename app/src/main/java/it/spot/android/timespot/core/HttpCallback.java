package it.spot.android.timespot.core;

import android.support.v7.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author a.rinaldi
 */
public class HttpCallback<T>
        implements Callback<T> {

    private boolean mCanceled;
    private Callback<T> mCallback;

    // region Construction

    public HttpCallback(Callback<T> callback) {
        super();
        mCanceled = false;
        mCallback = callback;
    }

    // endregion

    // region Public methods

    public void cancel() {
        mCanceled = true;
        mCallback = null;
    }

    // endregion

    // region Callback implementation

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!mCanceled) {
            mCallback.onResponse(call, response);
            cancel();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (!mCanceled) {
            mCallback.onFailure(call, t);
            cancel();
        }
    }

    // endregion
}
