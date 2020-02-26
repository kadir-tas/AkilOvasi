package com.company.akilovasi.data.remote.api;

import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.remote.models.responses.Response;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface PlantTypeService {

    @Headers("Cache-Control: public, max-stale=604800")
    @GET("plant/get/all")
    Call<Response<List<PlantType>>> loadAllPlantTypes();
}
