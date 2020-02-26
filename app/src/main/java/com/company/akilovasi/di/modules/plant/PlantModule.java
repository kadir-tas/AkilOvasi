package com.company.akilovasi.di.modules.plant;

import com.company.akilovasi.data.local.dao.PlantDao;
import com.company.akilovasi.data.remote.repositories.PlantRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.PlantRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class PlantModule {

    @Provides
    @Singleton
    PlantRepository providePlantRepository(Retrofit retrofit, PlantDao plantDao){
        return new PlantRepositoryImpl(retrofit, plantDao);
    }

}
