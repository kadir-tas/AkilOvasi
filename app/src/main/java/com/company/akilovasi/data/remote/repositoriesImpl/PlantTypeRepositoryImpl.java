package com.company.akilovasi.data.remote.repositoriesImpl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.dao.PlantTypeDao;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.remote.NetworkBoundResource;
import com.company.akilovasi.data.remote.api.PlantTypeService;
import com.company.akilovasi.data.remote.repositories.PlantTypeRepository;

import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Retrofit;

public class PlantTypeRepositoryImpl implements PlantTypeRepository {
    private static final String TAG = "PlantTypeRepositoryImpl";
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
        return new NetworkBoundResource<List<PlantType>, List<PlantType>>() {

            @Override
            protected void saveCallResult(@NonNull List<PlantType> item) {
                Log.d(TAG, "saveCallResult: of plant types of item " + item.size());
                plantTypeDao.savePlantTypes(item);
            }

            @NonNull
            @Override
            protected LiveData<List<PlantType>> loadFromDb() {
                Log.d(TAG, "loadFromDb: of plant types");
                return plantTypeDao.loadPlantTypes();
            }

            @NonNull
            @Override
            protected Call<List<PlantType>> createCall() {
                Log.d(TAG, "createCall: of plant types");
                return plantTypeService.loadAllPlantTypes();
            }
        }.getAsLiveData();
    }

    /*Access from local data base rather then sending new request since bellow data is not changed regularly*/
    @Override
    public LiveData<List<PlantType>> getPlantByCategories(String plantCategory) {
        Log.d(TAG, "getPlantByCategories: of type " + plantCategory);
        return plantTypeDao.loadPlantByCategories(plantCategory);
    }

    @Override
    public LiveData<List<String>> getListOfPlantCategories() {
        Log.d(TAG, "getListOfPlantCategories: called");
        return plantTypeDao.loadListOfPlantCategories();
    }

    @Override
    public LiveData<PlantType> getPlantType(Long plantId) {
        return plantTypeDao.getPlantType(plantId);
    }

}
