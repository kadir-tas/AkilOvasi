package com.company.akilovasi.di.modules.user;

import com.company.akilovasi.data.remote.repositories.UserRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.UserRepositoryImpl;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class UserModule {

    @Provides
    @Singleton
    UserRepository provideUserRepository(Retrofit retrofit, Gson gson){
        return new UserRepositoryImpl(retrofit,gson);
    }


}
