package com.company.akilovasi.data.remote.api;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.PlantAddRequest;
import com.company.akilovasi.data.remote.models.responses.PlantResponse;
import com.company.akilovasi.data.remote.models.responses.Response;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlantService {

    @Headers("Cache-Control: public, max-stale=604800")
    @GET("garden/get")
    Call<PlantResponse> getUserPlants(@Query("userId") Long userId);


    @POST("/garden/add")
    Call<Response<Message>> addUserPlant(@Body PlantAddRequest plantAddRequest);

    @Multipart
    @POST("/garden/addwithimg")
    Call<Response<Message>> addUserPlantWithImage(@Part("addPlant") PlantAddRequest plantAddRequest, @Part MultipartBody.Part image);


    @Multipart
    @POST("/garden/img/update")
    Call<Response<Message>> uploadImage(@Part("userId") RequestBody userId,
                                        @Part("userPlantId") RequestBody userPlantId,
                                        @Part MultipartBody.Part image);

    @Multipart
    @POST("/garden/sensor/update")
    Call<Response<Message>> updatePlantSensorValue(
            @Part("userId") RequestBody userId,
            @Part("userPlantId") RequestBody userPlantId,
            @Part MultipartBody.Part image,
            @Part("sensPh") RequestBody sensPh,
            @Part("sensTemp") RequestBody sensTemp,
            @Part("sensHumidity") RequestBody sensHumidity,
            @Part("sensLight") RequestBody sensLight,
            @Part("plantSize") RequestBody plantSize,
            @Part("plantPotSize") RequestBody plantPotSize
    );
}
