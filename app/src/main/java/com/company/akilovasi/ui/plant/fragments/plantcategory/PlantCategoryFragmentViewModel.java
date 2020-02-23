package com.company.akilovasi.ui.plant.fragments.plantcategory;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.remote.api.PlantTypeService;
import com.company.akilovasi.data.remote.repositories.PlantTypeRepository;

import java.util.List;

import javax.inject.Inject;

public class PlantCategoryFragmentViewModel extends ViewModel {
    private static final String TAG = "PlantCategoryFragmentVi";

    private String plantCategory;

    private PlantTypeRepository plantTypeRepository;

    @Inject
    public PlantCategoryFragmentViewModel(PlantTypeRepository plantTypeRepository) {
        this.plantTypeRepository = plantTypeRepository;
    }

    public void setPlantCategory(String plantCategory){
        this.plantCategory = plantCategory;
    }

    public LiveData<List<PlantType>> getPlants(){
        Log.d(TAG, "getPlants: getting the plant observers of type " + plantCategory);
        return plantTypeRepository.getPlantByCategories(plantCategory);
    }

}
