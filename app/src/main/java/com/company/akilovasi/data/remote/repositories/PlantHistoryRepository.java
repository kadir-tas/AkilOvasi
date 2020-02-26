package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.PlantHistory;

import java.util.List;

public interface PlantHistoryRepository {

    LiveData<Resource<List<PlantHistory>>> getUserPlantHistory(Long plantId);


}
