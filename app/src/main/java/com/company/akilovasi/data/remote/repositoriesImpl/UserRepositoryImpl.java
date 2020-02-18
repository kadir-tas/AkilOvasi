package com.company.akilovasi.data.remote.repositoriesImpl;

import com.company.akilovasi.data.remote.api.UserService;
import com.company.akilovasi.data.remote.models.responses.LoginResponse;
import com.company.akilovasi.data.remote.repositories.UserRepository;
import com.google.gson.Gson;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class UserRepositoryImpl implements UserRepository {
    private Retrofit retrofit;
    private Gson gson;

    @Inject
    public UserService userService;

    public UserRepositoryImpl(Retrofit retrofit, Gson gson) {
        this.gson = gson;
        this.retrofit = retrofit;
    }

    @Override
    public LoginResponse doAuth(String username, String password) {
        retrofit.create(UserService.class).login(username,password);
        return null;
    }
}
