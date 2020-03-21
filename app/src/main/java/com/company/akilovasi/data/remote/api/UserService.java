package com.company.akilovasi.data.remote.api;

import com.company.akilovasi.data.local.entities.User;
import com.company.akilovasi.data.remote.models.requests.LoginRequest;
import com.company.akilovasi.data.remote.models.responses.LoginResponse;
import com.company.akilovasi.data.remote.models.responses.Response;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.company.akilovasi.data.remote.ApiConstants.AUTHORIZATION;
import static com.company.akilovasi.data.remote.ApiConstants.LOGIN_URL;
import static com.company.akilovasi.data.remote.ApiConstants.REFRESH_JWT_TOKEN_URL;

public interface UserService {

    @POST(LOGIN_URL)
    Call<Response<LoginResponse>> login(@Body LoginRequest loginRequest);

    @GET("user/{id}")
    Flowable<User> getUserData(@Header("Authorization") String token, @Path("id") int id);

    @POST(REFRESH_JWT_TOKEN_URL)
    Call<Void> refreshJwtToken(@Header(AUTHORIZATION) String refreshToken);

}
