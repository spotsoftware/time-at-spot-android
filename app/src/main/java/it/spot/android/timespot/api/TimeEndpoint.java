package it.spot.android.timespot.api;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import it.spot.android.timespot.R;
import it.spot.android.timespot.auth.TimeAuthenticatorHelper;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author a.rinaldi
 */
public class TimeEndpoint {

    public static Retrofit getInstance(final Context context) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {

                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Account account = TimeAuthenticatorHelper.getAccount(context);

                        Request request = chain.request();
                        Response response;
                        if (account == null) {
                            response = chain.proceed(request);

                        } else {
                            Log.e("TIMEENDPOINT", "added token " + TimeAuthenticatorHelper.getToken(context, account));
                            response = chain.proceed(request.newBuilder()
                                    .addHeader("Cookie", TimeAuthenticatorHelper.getToken(context, account))
                                    .build());

                            if (response.headers() != null && account != null) {
                                String cookie = response.headers().get("Set-Cookie");
                                if (!TextUtils.isEmpty(cookie)) {
                                    Log.e("TIMEENDPOINT", "updated token " + cookie);
                                    TimeAuthenticatorHelper.updateToken(context, account, cookie);
                                }
                            }
                        }

                        return response;
                    }

                }).build();

        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }
}
