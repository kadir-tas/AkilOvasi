package com.company.akilovasi.data.remote.repositoriesImpl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.dao.PlantDao;
import com.company.akilovasi.data.local.dao.PlantHistoryDao;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.data.remote.NetworkBoundResource;
import com.company.akilovasi.data.remote.api.PlantHistoryService;
import com.company.akilovasi.data.remote.api.PlantService;
import com.company.akilovasi.data.remote.models.responses.PlantHistoryResponse;
import com.company.akilovasi.data.remote.models.responses.PlantResponse;
import com.company.akilovasi.data.remote.repositories.PlantHistoryRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PlantHistoryRepositoryImpl implements PlantHistoryRepository {
    private static final String TAG = "PlantHistoryRepositoryI";
    Retrofit retrofit;

    PlantHistoryService plantHistoryService;

    PlantHistoryDao plantHistoryDao;

    @Inject
    public PlantHistoryRepositoryImpl(Retrofit retrofit, PlantHistoryDao plantHistoryDao) {
        this.retrofit = retrofit;
        this.plantHistoryDao = plantHistoryDao;
        this.plantHistoryService = retrofit.create(PlantHistoryService.class);
    }

    @Override
    public LiveData<Resource<List<PlantHistory>>> getUserPlantHistory(Long userPlantId) {
        return new NetworkBoundResource<List<PlantHistory>, PlantHistoryResponse>() {
            @Override
            protected void saveCallResult(@NonNull PlantHistoryResponse item) {
                plantHistoryDao.savePlantHistory(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<PlantHistory> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<PlantHistory>> loadFromDb() {
                return plantHistoryDao.getUserPlantHistory(userPlantId);
            }

            @NonNull
            @Override
            protected Call<PlantHistoryResponse> createCall() {
                return plantHistoryService.getUserPlantHistory(userPlantId);
            }
        }.getAsLiveData();
    }

    @Override
    public LiveData<Resource<List<PlantHistory>>> getUserPlantHistoryPaged(Long userPlantId, int pageId) {
        return new NetworkBoundResource<List<PlantHistory>, PlantHistoryResponse>() {
            @Override
            protected void saveCallResult(@NonNull PlantHistoryResponse item) {
                if (item != null) {
                    plantHistoryDao.savePlantHistory(item.getResults());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<PlantHistory> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<PlantHistory>> loadFromDb() {
                return plantHistoryDao.getUserPlantHistoryPaged(userPlantId, pageId);
            }

            @NonNull
            @Override
            protected Call<PlantHistoryResponse> createCall() {
                return plantHistoryService.getUserPlantHistoryPaged(userPlantId,pageId);
            }
        }.getAsLiveData();
    }
}
