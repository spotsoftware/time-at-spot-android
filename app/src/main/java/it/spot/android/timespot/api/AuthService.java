package it.spot.android.timespot.api;

import it.spot.android.timespot.api.request.AuthRequest;
import it.spot.android.timespot.domain.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;

/**
 * @author a.rinaldi
 */
public interface AuthService {

    @POST("api/session/google/validate")
    Call<User> auth(@Body AuthRequest request);

    @DELETE("api/session")
    Call<Void> logout();
}
