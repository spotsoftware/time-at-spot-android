package it.spot.android.timespot.api;

import android.content.Context;

import it.spot.android.timespot.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author a.rinaldi
 */
public class TimeEndpoint {

    public static Retrofit getInstance(Context context) {
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
