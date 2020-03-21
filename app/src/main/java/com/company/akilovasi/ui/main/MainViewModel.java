package com.company.akilovasi.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.remote.repositories.BannerRepository;
import com.company.akilovasi.data.remote.repositories.PlantRepository;
import com.company.akilovasi.data.remote.repositories.UserRepository;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private final UserRepository userRepository;

    private final BannerRepository bannerRepository;

    private final PlantRepository plantRepository;

    private MediatorLiveData<Resource<List<Banner>>> banners = new MediatorLiveData<>();

    @Inject
    public MainViewModel(UserRepository userRepository, BannerRepository bannerRepository, PlantRepository plantRepository) {
        this.userRepository = userRepository;
        this.bannerRepository = bannerRepository;
        this.plantRepository = plantRepository;
//        initBanner();
    }

    public LiveData<Resource<List<Banner>>> getAllActiveBanners() {
        return bannerRepository.getAllActiveBanners();
    }

    public void initBanner(){
        banners = (MediatorLiveData<Resource<List<Banner>>>) bannerRepository.getAllActiveBanners();
    }

    public LiveData<Resource<List<Plant>>> getAllPlants() {
        return plantRepository.getUserAllPlants(getAuthenticatedUserId());
    }

    public Long getAuthenticatedUserId(){
        return 10L; //TODO: THIS ID IS FOR ONLY TEST . It will return authenticated userId
    }
}
