package com.company.akilovasi.ui.plant.fragments.addplant;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.local.entities.UserPlant;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.data.remote.repositories.PlantTypeRepository;
import com.company.akilovasi.data.remote.repositories.UserPlantRepository;
import com.company.akilovasi.di.SecretPrefs;

import javax.inject.Inject;

import retrofit2.Call;


public class PlantAddFragmentViewModel extends ViewModel {
    private static final String TAG = "PlantAddFragmentViewMod";
    private PlantTypeRepository plantTypeRepository;

    private UserPlantRepository userPlantRepository;

    @Inject
    @SecretPrefs
    SharedPreferences secretPreferences;

    @Inject
    public PlantAddFragmentViewModel (PlantTypeRepository plantTypeRepository, UserPlantRepository userPlantRepository){
        this.plantTypeRepository = plantTypeRepository;
        this.userPlantRepository = userPlantRepository;
    }

    public LiveData<PlantType> getPlantType(Long plantId){
        return plantTypeRepository.getPlantType(plantId);
    }

    public Call<Response<Message>> saveUserPlant(Long plantId, Long userId, String plantName, float plantSize , float potSize){
        return userPlantRepository.addUserPlant( plantId, userId, plantName , plantSize, potSize);
    }

    public Call<Response<Message>> saveUserPlantWithImage(Long plantId, Long userId, String plantName, float plantSize , float potSize, String imageFilePath){
        return userPlantRepository.addUserPlantWithImage( plantId, userId, plantName , plantSize, potSize,imageFilePath);
    }


    public Call<Response<Message>>  saveUserPlantImage(String imageFilePath,Long plantId, Long userId){
        return userPlantRepository.updateUserPlantImage(imageFilePath , userId, plantId);
    }


    public Long getUserId(){
        return secretPreferences.getLong(ApiConstants.USER_ID,-1);
    }
}
