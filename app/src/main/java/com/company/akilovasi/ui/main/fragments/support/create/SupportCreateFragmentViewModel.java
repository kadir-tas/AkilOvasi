package com.company.akilovasi.ui.main.fragments.support.create;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.repositories.PlantRepository;
import com.company.akilovasi.di.SecretPrefs;

import java.util.List;

import javax.inject.Inject;

public class SupportCreateFragmentViewModel extends ViewModel {

    private PlantRepository plantRepository;

    @Inject
    @SecretPrefs
    SharedPreferences secretPreferences;

    @Inject
    public SupportCreateFragmentViewModel( PlantRepository plantRepository ) {
        this.plantRepository = plantRepository;
    }

    public LiveData<Resource<List<Plant>>> getAllUserPlants(){
        return plantRepository.getUserAllPlants( secretPreferences.getLong(ApiConstants.USER_ID, -1) );
    }
}
