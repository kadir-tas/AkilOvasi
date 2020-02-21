package com.company.akilovasi.data.remote.repositoriesImpl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.dao.BannerDao;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.remote.NetworkBoundResource;
import com.company.akilovasi.data.remote.api.BannerService;
import com.company.akilovasi.data.remote.models.responses.BannerResponse;
import com.company.akilovasi.data.remote.repositories.BannerRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;

public class BannerRepositoryImpl implements BannerRepository {

    Retrofit retrofit;

    BannerService bannerService;

    BannerDao bannerDao;

    @Inject
    public BannerRepositoryImpl(Retrofit retrofit, BannerDao bannerDao) {
        this.retrofit = retrofit;
        this.bannerDao = bannerDao;
        this.bannerService = retrofit.create(BannerService.class);
    }

    public LiveData<Resource<List<Banner>>> getAllBanners() {
        return new NetworkBoundResource<List<Banner>, BannerResponse>() {

            @Override
            protected void saveCallResult(@NonNull BannerResponse item) {
                bannerDao.saveBanners(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<Banner>> loadFromDb() {
                LiveData<List<Banner>> banners = bannerDao.loadBanners();
                Log.d("KKK", ""+banners);
                Log.d("KKK", ""+banners.getValue());
                return banners;
            }

            @NonNull
            @Override
            protected Call<BannerResponse> createCall() {
                return bannerService.loadBanners();
            }
        }.getAsLiveData();
    }

}
