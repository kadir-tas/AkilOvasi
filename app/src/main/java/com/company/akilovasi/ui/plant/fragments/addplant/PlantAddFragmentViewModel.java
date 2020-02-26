package com.company.akilovasi.ui.plant.fragments.addplant;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.local.entities.UserPlant;
import com.company.akilovasi.data.remote.repositories.PlantTypeRepository;
import com.company.akilovasi.data.remote.repositories.UserPlantRepository;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class PlantAddFragmentViewModel extends ViewModel {
    private static final String TAG = "PlantAddFragmentViewMod";
    private PlantTypeRepository plantTypeRepository;

    private UserPlantRepository userPlantRepository;

    @Inject
    public PlantAddFragmentViewModel (PlantTypeRepository plantTypeRepository, UserPlantRepository userPlantRepository){
        this.plantTypeRepository = plantTypeRepository;
        this.userPlantRepository = userPlantRepository;
    }

    public LiveData<PlantType> getPlantType(Long plantId){
        return plantTypeRepository.getPlantType(plantId);
    }

    public Call<ResponseBody> saveUserPlant(Long plantId, Long userId, String plantName, float plantSize , float potSize){
        return userPlantRepository.addUserPlant( plantId, userId, plantName , plantSize, potSize);
    }

    public Call<ResponseBody>  saveUserPlantImage(String imageFilePath,Long plantId, Long userId){
        return userPlantRepository.updateUserPlantImage(imageFilePath , plantId, userId);
    }

    //TODO: Get currently logged in users id
    //TODO: DONT FORGET HERE
    public Long getUserId(){
        Log.e(TAG, "getUserId: DON'T FORGET TO UPDATE HERE THIS IS PLACE HOLDER" );
        return 10L;
    }
}
