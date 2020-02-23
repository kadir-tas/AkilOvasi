package com.company.akilovasi.di.modules.userPlant;

import com.company.akilovasi.data.local.dao.UserPlantDao;
import com.company.akilovasi.data.remote.repositories.UserPlantRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.UserPlantRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class UserPlantModule {

    @Provides
    @Singleton
    UserPlantRepository provideUserPlantRepository(Retrofit retrofit, UserPlantDao userPlantDao){
        return new UserPlantRepositoryImpl(retrofit, userPlantDao);
    }
}
