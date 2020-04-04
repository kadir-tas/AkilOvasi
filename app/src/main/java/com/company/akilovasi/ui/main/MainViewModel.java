package com.company.akilovasi.ui.main;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.LogoutRequest;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.data.remote.repositories.BannerRepository;
import com.company.akilovasi.data.remote.repositories.PlantRepository;
import com.company.akilovasi.data.remote.repositories.UserRepository;
import com.company.akilovasi.di.SecretPrefs;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class MainViewModel extends ViewModel {

    private final UserRepository userRepository;

    private final BannerRepository bannerRepository;

    private final PlantRepository plantRepository;

    private MediatorLiveData<Resource<List<Banner>>> banners = new MediatorLiveData<>();

    @Inject
    @SecretPrefs
    SharedPreferences secretPreferences;

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

    public LiveData<Resource<Response<Message>>> logout(){
        return userRepository.logout(new LogoutRequest(getAuthenticatedUserId()));
    }

    public Long getAuthenticatedUserId(){
        return secretPreferences.getLong(ApiConstants.USER_ID,-1);
    }
}
