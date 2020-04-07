package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.PlantValueUpdateRequest;
import com.company.akilovasi.data.remote.models.responses.Response;

import java.util.List;

import retrofit2.Call;

public interface PlantRepository {

    public LiveData<Resource<List<Plant>>> getUserAllPlants(Long userId);
    MediatorLiveData<Resource<Response<Message>>> addUserPlant(Long plantId, Long userId, String plantName , float plantSize, float potSize);
    MediatorLiveData<Resource<Response<Message>>> addUserPlantWithImage(Long plantId, Long userId, String plantName , float plantSize, float potSize, String imageFilePath);
    MediatorLiveData<Resource<Response<Message>>> updateUserPlantImage(String imageFilePath, Long userId, Long userPlantId);
    MediatorLiveData<Resource<Response<Message>>> updateSensorValue(PlantValueUpdateRequest plantValueUpdateRequest);
    LiveData<Plant> getUserPlantLocal(Long userPlantId);

}
