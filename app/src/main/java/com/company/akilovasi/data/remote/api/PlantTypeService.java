package com.company.akilovasi.data.remote.api;

import com.company.akilovasi.data.local.entities.PlantType;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface PlantTypeService {

    @Headers("Cache-Control: public, max-stale=604800")
    @GET("plant/get/all")
    Call<List<PlantType>> loadAllPlantTypes();
}
