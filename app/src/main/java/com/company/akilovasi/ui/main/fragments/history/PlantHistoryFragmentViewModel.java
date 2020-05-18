package com.company.akilovasi.ui.main.fragments.history;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.repositories.PlantHistoryRepository;
import com.company.akilovasi.data.remote.repositories.PlantRepository;
import com.company.akilovasi.di.SecretPrefs;

import java.util.List;

import javax.inject.Inject;

public class PlantHistoryFragmentViewModel extends ViewModel {
    private static final String TAG = "PlantHistoryFragmentVie";
    PlantHistoryRepository plantHistoryRepository;


    @Inject
    @SecretPrefs
    SharedPreferences secretPreferences;

    @Inject
    public PlantHistoryFragmentViewModel(PlantHistoryRepository plantHistoryRepository) {
        this.plantHistoryRepository = plantHistoryRepository;
    }

    public LiveData<Resource<List<PlantHistory>>> getPlantHistory(Long userPlantsId) {
        return plantHistoryRepository.getUserPlantHistory(userPlantsId);
    }

    public LiveData<Resource<List<PlantHistory>>> getPlantHistoryPaged(Long userPlantId, int pageId){
        return plantHistoryRepository.getUserPlantHistoryPaged(userPlantId,pageId);
    }

    private Long getUserId() {
        return secretPreferences.getLong(ApiConstants.USER_ID, -1 );
    }
}