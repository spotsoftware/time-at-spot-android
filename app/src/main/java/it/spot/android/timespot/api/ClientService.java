package it.spot.android.timespot.api;

import java.util.List;

import it.spot.android.timespot.domain.Client;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author a.rinaldi
 */
public interface ClientService {

    @GET("/api/organizations/{id}/clients/all")
    Call<List<Client>> get(@Path("id") String organizationId);
}
