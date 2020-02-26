package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.remote.models.responses.Response;

import java.util.List;

public interface PlantTypeRepository {

    LiveData<Resource<List<PlantType>>> getAllPlantTypes();
    LiveData<List<PlantType>> getPlantByCategories(String plantCategory);
    LiveData<List<String>> getListOfPlantCategories();
    LiveData<PlantType> getPlantType(Long plantId);

}
