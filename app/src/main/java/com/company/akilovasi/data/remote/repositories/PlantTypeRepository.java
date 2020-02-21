package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.PlantType;
import java.util.List;

public interface PlantTypeRepository {

    public LiveData<Resource<List<PlantType>>> getAllPlantTypes();

}
