package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.LoginRequest;
import com.company.akilovasi.data.remote.models.requests.LogoutRequest;
import com.company.akilovasi.data.remote.models.responses.LoginResponse;
import com.company.akilovasi.data.remote.models.responses.Response;

public interface UserRepository {

    public LiveData<Resource<LoginResponse>> doAuth(LoginRequest loginRequest);

    public LiveData<Resource<Response<Message>>> logout(LogoutRequest logoutRequest);

    public String refreshToken(String refreshToken);

}