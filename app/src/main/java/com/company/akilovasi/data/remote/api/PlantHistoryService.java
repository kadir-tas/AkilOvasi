package com.company.akilovasi.data.remote.api;

import com.company.akilovasi.data.remote.models.responses.PlantHistoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlantHistoryService {

    @Headers("Cache-Control: public, max-stale=604800")
    @GET("historical/userPlant")
    Call<PlantHistoryResponse> getUserPlantHistory(@Query("userPlantId") Long userPlantId);

}
