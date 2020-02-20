package com.company.akilovasi.di.modules.banner;

import com.company.akilovasi.data.local.dao.BannerDao;
import com.company.akilovasi.data.remote.api.BannerService;
import com.company.akilovasi.data.remote.repositories.BannerRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.BannerRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class BannerModule {

    @Provides
    @Singleton
    BannerRepository provideBannerRepository(Retrofit retrofit, BannerDao bannerDao){
        return new BannerRepositoryImpl(retrofit, bannerDao);
    }


}