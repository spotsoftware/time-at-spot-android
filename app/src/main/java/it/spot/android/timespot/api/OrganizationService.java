package it.spot.android.timespot.api;

import java.util.List;

import it.spot.android.timespot.api.domain.Organization;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author a.rinaldi
 */
public interface OrganizationService {

    @GET("api/organizations")
    Call<List<Organization>> get(@Query("userId") String userId);
}
