package it.spot.android.timespot.api;

import java.util.List;

import it.spot.android.timespot.api.domain.Project;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author a.rinaldi
 */
public interface ProjectService {

    @GET("/api/organizations/{id}/projects/all")
    Call<List<Project>> get(@Path("id") String organizationId);
}
