package com.company.akilovasi.di.modules.user;

import com.company.akilovasi.data.remote.repositories.LoginRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.LoginRepositoryImpl;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class LoginModule {

    @Provides
    @Singleton
    LoginRepository provideLoginRepository(Retrofit retrofit, Gson gson){
        return new LoginRepositoryImpl(retrofit,gson);
    }


}