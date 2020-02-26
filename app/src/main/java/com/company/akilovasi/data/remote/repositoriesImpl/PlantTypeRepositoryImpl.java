package com.company.akilovasi.data.remote.repositoriesImpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.dao.PlantTypeDao;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.remote.NetworkBoundResource;
import com.company.akilovasi.data.remote.api.PlantTypeService;
import com.company.akilovasi.data.remote.models.responses.PlantTypeResponse;
import com.company.akilovasi.data.remote.repositories.PlantTypeRepository;

import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Retrofit;

public class PlantTypeRepositoryImpl implements PlantTypeRepository {

    private Retrofit retrofit;

    private PlantTypeDao plantTypeDao;

    private PlantTypeService plantTypeService;

    @Inject
    public PlantTypeRepositoryImpl(Retrofit retrofit, PlantTypeDao bannerDao){
        this.retrofit = retrofit;
        this.plantTypeDao = bannerDao;
        this.plantTypeService = retrofit.create(PlantTypeService.class);
    }

    @Override
    public LiveData<Resource<List<PlantType>>> getAllPlantTypes() {
        return new NetworkBoundResource<List<PlantType>, PlantTypeResponse>() {

            @Override
            protected void saveCallResult(@NonNull PlantTypeResponse item) {
                plantTypeDao.savePlantTypes(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<PlantType> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<PlantType>> loadFromDb() {
                return plantTypeDao.loadPlantTypes();
            }

            @NonNull
            @Override
            protected Call<PlantTypeResponse> createCall() {
                return plantTypeService.loadAllPlantTypes();
            }
        }.getAsLiveData();
    }
}
