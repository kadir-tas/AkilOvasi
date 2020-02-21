package com.company.akilovasi.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.remote.repositories.BannerRepository;
import com.company.akilovasi.data.remote.repositories.UserRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class MainViewModel extends ViewModel {

    private final UserRepository userRepository;

    private final BannerRepository bannerRepository;

    private MediatorLiveData<Resource<List<Banner>>> banners = new MediatorLiveData<>();

    @Inject
    public MainViewModel(UserRepository userRepository, BannerRepository bannerRepository) {
        this.userRepository = userRepository;
        this.bannerRepository = bannerRepository;
//        initBanner();
    }

    public LiveData<Resource<List<Banner>>> getAllBanners() {
        return bannerRepository.getAllBanners();
    }

    public void initBanner(){
        banners = (MediatorLiveData<Resource<List<Banner>>>) bannerRepository.getAllBanners();
    }

}
