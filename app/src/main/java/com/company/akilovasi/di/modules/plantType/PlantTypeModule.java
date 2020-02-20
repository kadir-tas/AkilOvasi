package com.company.akilovasi.di.modules.plantType;

import com.company.akilovasi.data.local.dao.PlantTypeDao;
import com.company.akilovasi.data.remote.repositories.PlantTypeRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.PlantTypeRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class PlantTypeModule {

    @Provides
    @Singleton
    PlantTypeRepository providePlantTypeRepository(Retrofit retrofit, PlantTypeDao plantTypeDao){
        return new PlantTypeRepositoryImpl(retrofit, plantTypeDao);
    }
}
