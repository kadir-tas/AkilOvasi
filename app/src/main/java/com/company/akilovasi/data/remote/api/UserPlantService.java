package com.company.akilovasi.data.remote.api;


import com.company.akilovasi.data.local.entities.UserPlant;
import com.company.akilovasi.data.remote.models.requests.PlantAddRequest;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserPlantService {

    @POST("/garden/add")
    Call<ResponseBody> addUserPlant(@Body PlantAddRequest plantAddRequest);

    @Multipart
    @POST("/garden/img/update")
    Call<ResponseBody> uploadImage(@Part("userId") RequestBody userId,
                                   @Part("userPlantId") RequestBody userPlantId,
                                   @Part MultipartBody.Part image);

    @GET("/garden/get?userId={userId}")
    Call<List<UserPlant>> getUserPlants(@Path("userId") Long userId);

}
