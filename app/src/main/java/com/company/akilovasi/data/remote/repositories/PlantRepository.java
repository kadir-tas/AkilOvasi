package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.data.local.entities.UserPlant;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.PlantValueUpdateRequest;
import com.company.akilovasi.data.remote.models.responses.Response;

import java.util.List;

import retrofit2.Call;

public interface PlantRepository {

    public LiveData<Resource<List<Plant>>> getUserAllPlants(Long userId);
    Call<Response<Message>> addUserPlant(Long plantId, Long userId, String plantName , float plantSize, float potSize);
    Call<Response<Message>> addUserPlantWithImage(Long plantId, Long userId, String plantName , float plantSize, float potSize, String imageFilePath);
    Call<Response<Message>> updateUserPlantImage(String imageFilePath, Long userId, Long userPlantId);
    Call<Response<Message>> updateSensorValue(PlantValueUpdateRequest plantValueUpdateRequest);
    LiveData<Plant> getUserPlantLocal(Long userPlantId);

}
