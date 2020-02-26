package com.company.akilovasi.ui.main.fragments.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.data.remote.repositories.PlantHistoryRepository;
import com.company.akilovasi.data.remote.repositories.PlantRepository;

import java.util.List;

import javax.inject.Inject;

public class PlantHistoryFragmentViewModel extends ViewModel {

    PlantHistoryRepository plantHistoryRepository;

    @Inject
    public PlantHistoryFragmentViewModel(PlantHistoryRepository plantHistoryRepository) {
        this.plantHistoryRepository = plantHistoryRepository;
    }

    LiveData<Resource<List<PlantHistory>>> getPlantHistory(Long userPlantsId) {
        return plantHistoryRepository.getUserPlantHistory(userPlantsId);
    }

    private Long getUserId() {
        return 1L;
    }
}