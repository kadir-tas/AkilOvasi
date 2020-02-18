package com.company.akilovasi.data.remote.api;

import com.company.akilovasi.data.local.entities.User;
import com.company.akilovasi.data.remote.models.responses.LoginResponse;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @POST("login")
    Call<LoginResponse> login(String username, String password);

    @GET("user/{id}")
    Flowable<User> getUserData(@Header("Authorization") String token, @Path("id") int id);

}
