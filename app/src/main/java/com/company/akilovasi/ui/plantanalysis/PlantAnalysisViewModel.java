package com.company.akilovasi.ui.plantanalysis;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.PlantValueUpdateRequest;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.data.remote.repositories.PlantRepository;
import com.company.akilovasi.data.remote.repositories.PlantTypeRepository;
import com.company.akilovasi.di.SecretPrefs;

import javax.inject.Inject;

import retrofit2.Call;


public class PlantAnalysisViewModel extends ViewModel {

    private PlantTypeRepository plantRepository;
    private PlantRepository userPlantRepository;

    @Inject
    @SecretPrefs
    SharedPreferences secretPreferences;

    @Inject
    PlantAnalysisViewModel(PlantTypeRepository plantRepository, PlantRepository userPlantRepository) {
        this.plantRepository = plantRepository;
        this.userPlantRepository = userPlantRepository;
    }

    public LiveData<PlantType> getPlantType(Long plantTypeId){
        return plantRepository.getPlantType(plantTypeId);
    }

    public LiveData<Plant> getUserPlantLocal(Long userPlantId){
        return userPlantRepository.getUserPlantLocal(userPlantId);
    }

    public MutableLiveData<Resource<Response<Message>>> updatePlantSensValue(PlantValueUpdateRequest plantValueUpdateRequest){
        return userPlantRepository.updateSensorValue(plantValueUpdateRequest);
    }

    public Long getUserId(){
        return secretPreferences.getLong(ApiConstants.USER_ID,-1);
    }
}
