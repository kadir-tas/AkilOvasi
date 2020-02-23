package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.UserPlant;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public interface UserPlantRepository {

    LiveData<Resource<List<UserPlant>>> getAllUserPlants(Long userId);
    Call<ResponseBody> addUserPlant(Long plantId, Long userId, String plantName , float plantSize, float potSize);
    Call<ResponseBody> updateUserPlantImage(String imageFilePath, Long userId, Long userPlantId);
}
