package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Banner;

import java.util.List;

public interface BannerRepository {

    public LiveData<Resource<List<Banner>>> getAllActiveBanners();

}
