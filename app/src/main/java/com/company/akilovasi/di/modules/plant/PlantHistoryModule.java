package com.company.akilovasi.di.modules.plant;

import com.company.akilovasi.data.local.dao.PlantDao;
import com.company.akilovasi.data.local.dao.PlantHistoryDao;
import com.company.akilovasi.data.remote.repositories.PlantHistoryRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.PlantHistoryRepositoryImpl;
import com.company.akilovasi.data.remote.repositoriesImpl.PlantRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class PlantHistoryModule {

    @Provides
    @Singleton
    PlantHistoryRepository providePlantHistoryRepository(Retrofit retrofit, PlantHistoryDao plantHistoryDao){
        return new PlantHistoryRepositoryImpl(retrofit, plantHistoryDao);
    }

}
