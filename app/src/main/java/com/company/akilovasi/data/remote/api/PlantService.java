package com.company.akilovasi.data.remote.api;

import com.company.akilovasi.data.remote.models.responses.PlantResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PlantService {

    @Headers("Cache-Control: public, max-stale=604800")
    @GET("garden/get")
    Call<PlantResponse> getUserPlants(@Query("userId") Long userId);
}
