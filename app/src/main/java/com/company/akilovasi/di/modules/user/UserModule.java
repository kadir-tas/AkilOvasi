package com.company.akilovasi.di.modules.user;

import android.content.SharedPreferences;

import com.company.akilovasi.data.local.dao.UserDao;
import com.company.akilovasi.data.remote.repositories.UserRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.UserRepositoryImpl;
import com.company.akilovasi.di.SecretPrefs;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class UserModule {

    @Provides
    @Singleton
    UserRepository provideUserRepository(Retrofit retrofit, Gson gson, UserDao userDao){
        return new UserRepositoryImpl(retrofit,gson,userDao);
    }


}
