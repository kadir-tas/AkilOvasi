package com.company.akilovasi.data.remote.repositoriesImpl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.dao.PlantDao;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.local.entities.UserPlant;
import com.company.akilovasi.data.remote.NetworkBoundResource;
import com.company.akilovasi.data.remote.api.PlantService;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.PlantAddRequest;
import com.company.akilovasi.data.remote.models.requests.PlantValueUpdateRequest;
import com.company.akilovasi.data.remote.models.responses.PlantResponse;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.data.remote.repositories.PlantRepository;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class PlantRepositoryImpl implements PlantRepository {
    private static final String TAG = "PlantRepositoryImpl";
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
//                if(item != null)
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

    @Override
    public Call<Response<Message>> addUserPlant(Long plantId, Long userId, String plantName, float plantSize, float potSize) {
        return plantService.addUserPlant(new PlantAddRequest( plantId , userId , plantName, plantSize, potSize ));
    }

    @Override
    public Call<Response<Message>> addUserPlantWithImage(Long plantId, Long userId, String plantName, float plantSize, float potSize, String imageFilePath) {
        File file = new File(imageFilePath);
        RequestBody requestFile =
                RequestBody.create(file, MediaType.parse("multipart/form-data"));
        if(file.exists())
            Log.d(TAG, "addUserPlantWithImage: File exist");
        else
            Log.d(TAG, "addUserPlantWithImage: File does not ecist");
// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        PlantAddRequest plantAddRequest = new PlantAddRequest( plantId , userId , plantName, plantSize, potSize );
        return plantService.addUserPlantWithImage(plantAddRequest, body);
    }

    @Override
    public Call<Response<Message>> updateUserPlantImage(String imageFilePath, Long userId, Long userPlantId) {
        File file = new File(imageFilePath);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

// add another part within the multipart request
        RequestBody userPlantIdResponseBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), userPlantId + "" );

        RequestBody userIdResponseBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), userId + "" );

        return plantService.uploadImage(userIdResponseBody, userPlantIdResponseBody, body);
    }

    @Override
    public Call<Response<Message>> updateSensorValue(PlantValueUpdateRequest plantValueUpdateRequest) {
        File imageFile = new File(plantValueUpdateRequest.getImagePath());
        MultipartBody.Part body = null;
        if(imageFile.exists()){
            RequestBody requestFile = RequestBody.create(imageFile, MediaType.parse("multipart/form-data"));
            body = MultipartBody.Part.createFormData("image" ,imageFile.getName(), requestFile);
        }else{
            body = MultipartBody.Part.createFormData("image", "");
        }
        RequestBody userPlantId = RequestBody.create( plantValueUpdateRequest.getUserPlantId() + "" , MediaType.parse("multipart/form-data"));
        RequestBody userId = RequestBody.create( plantValueUpdateRequest.getUserId() + "" , MediaType.parse("multipart/form-data"));
        RequestBody sensHumidity = RequestBody.create( plantValueUpdateRequest.getSensHumidity() + "" , MediaType.parse("multipart/form-data"));
        RequestBody sensLight = RequestBody.create( plantValueUpdateRequest.getSensLight() + "" , MediaType.parse("multipart/form-data"));
        RequestBody sensPh = RequestBody.create( plantValueUpdateRequest.getSensPh() + "" , MediaType.parse("multipart/form-data"));
        RequestBody sensTemp = RequestBody.create( plantValueUpdateRequest.getSensTemp() + "" , MediaType.parse("multipart/form-data"));
        RequestBody pantPotSize = RequestBody.create( plantValueUpdateRequest.getPlantPotSize() + "" , MediaType.parse("multipart/form-data"));
        RequestBody plantSize = RequestBody.create( plantValueUpdateRequest.getPlantSize() + "" , MediaType.parse("multipart/form-data"));

        return plantService.updatePlantSensorValue(userId,userPlantId,body,sensPh,sensTemp, sensHumidity,sensLight,plantSize,pantPotSize);
    }

    @Override
    public LiveData<Plant> getUserPlantLocal(Long userPlantId) {
        return plantDao.loadUserPlant(userPlantId);
    }
}
