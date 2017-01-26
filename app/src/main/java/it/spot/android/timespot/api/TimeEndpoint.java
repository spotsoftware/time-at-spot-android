package it.spot.android.timespot.api;

import android.accounts.Account;
import android.content.Context;

import java.io.IOException;

import it.spot.android.timespot.R;
import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author a.rinaldi
 */
public class TimeEndpoint {

    public static Retrofit getInstance(final Context context) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {

                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Account account = TimeAuthenticatorHelper.getAccount(context);

                        if (account == null) {
                            return chain.proceed(chain.request());

                        } else {
                            return chain.proceed(chain.request().newBuilder()
                                    .addHeader("Cookie", TimeAuthenticatorHelper.getToken(context, account))
                                    .build());
                        }
                    }

                }).build();

        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }
}
