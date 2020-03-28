package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.data.local.entities.UserPlant;

import java.util.List;

public interface PlantRepository {

    public LiveData<Resource<List<Plant>>> getUserAllPlants(Long userId);

}
