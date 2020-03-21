package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.remote.models.requests.LoginRequest;
import com.company.akilovasi.data.remote.models.responses.LoginResponse;

public interface UserRepository {

    public LiveData<Resource<LoginResponse>> doAuth(LoginRequest loginRequest);

    public String refreshToken(String refreshToken);
}