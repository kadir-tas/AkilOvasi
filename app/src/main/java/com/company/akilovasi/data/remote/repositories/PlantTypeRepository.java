package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.PlantType;
import java.util.List;

public interface PlantTypeRepository {

    public LiveData<Resource<List<PlantType>>> getAllPlantTypes();

    public LiveData<List<PlantType>> getPlantByCategories(String plantCategory);

    public LiveData<List<String>> getListOfPlantCategories();

    public LiveData<PlantType> getPlantType(Long plantId);

}
