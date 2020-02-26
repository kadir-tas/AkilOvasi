package com.company.akilovasi.data.remote.repositoriesImpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.dao.PlantDao;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.remote.NetworkBoundResource;
import com.company.akilovasi.data.remote.api.PlantService;
import com.company.akilovasi.data.remote.models.responses.PlantResponse;
import com.company.akilovasi.data.remote.repositories.PlantRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PlantRepositoryImpl implements PlantRepository {

    Retrofit retrofit;

    PlantService plantService;

    PlantDao plantDao;

    @Inject
    public PlantRepositoryImpl(Retrofit retrofit, PlantDao plantDao) {
        this.retrofit = retrofit;
        this.plantDao = plantDao;
        this.plantService = retrofit.create(PlantService.class);
    }


    @Override
    public LiveData<Resource<List<Plant>>> getUserAllPlants(Long userId) {
        return new NetworkBoundResource<List<Plant>, PlantResponse>() {
            @Override
            protected void saveCallResult(@NonNull PlantResponse item) {
//                if(item.equals(null))
                    plantDao.savePlants(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Plant> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Plant>> loadFromDb() {
                return plantDao.getUserPlants(userId);
            }

            @NonNull
            @Override
            protected Call<PlantResponse> createCall() {
                return plantService.getUserPlants(userId);
            }
        }.getAsLiveData();
    }

}
