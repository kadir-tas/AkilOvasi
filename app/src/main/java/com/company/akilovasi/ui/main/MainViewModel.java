package com.company.akilovasi.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.remote.NetworkBoundResource;
import com.company.akilovasi.data.remote.models.responses.BannerResponse;
import com.company.akilovasi.data.remote.repositories.BannerRepository;
import com.company.akilovasi.data.remote.repositories.UserRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class MainViewModel extends ViewModel {

    private final UserRepository userRepository;

    private final BannerRepository bannerRepository;

    @Inject
    public MainViewModel(UserRepository userRepository, BannerRepository bannerRepository) {
        this.userRepository = userRepository;
        this.bannerRepository = bannerRepository;
    }


    public LiveData<Resource<List<Banner>>> getAllBanners() {
        return bannerRepository.getAllBanners();
    }

}
