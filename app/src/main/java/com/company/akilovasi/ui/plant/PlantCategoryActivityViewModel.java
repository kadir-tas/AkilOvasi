package com.company.akilovasi.ui.plant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.remote.repositories.PlantTypeRepository;
import java.util.List;
import javax.inject.Inject;

public class PlantCategoryActivityViewModel extends ViewModel {

    private final PlantTypeRepository plantTypeRepository;

    @Inject
    public PlantCategoryActivityViewModel(PlantTypeRepository plantTypeRepository){
        this.plantTypeRepository = plantTypeRepository;
    }

    public LiveData<Resource<List<PlantType>>> getAllPlantTypes() {
        return plantTypeRepository.getAllPlantTypes();
    }

    public LiveData<List<String>> getListOfPlantCategories(){
        return plantTypeRepository.getListOfPlantCategories();
    }
}
