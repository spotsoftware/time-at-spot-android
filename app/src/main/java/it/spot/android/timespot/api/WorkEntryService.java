package it.spot.android.timespot.api;

import java.util.List;

import it.spot.android.timespot.api.request.WorkEntriesRequest;
import it.spot.android.timespot.api.response.WorkEntriesResponse;
import it.spot.android.timespot.domain.WorkEntry;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author a.rinaldi
 */
public interface WorkEntryService {

    @POST("api/organizations/{id}/workEntries")
    Call<WorkEntriesResponse> get(@Path("id") String organizationId, @Query("page") int page, @Body WorkEntriesRequest request);
}
