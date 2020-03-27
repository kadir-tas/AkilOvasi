package com.company.akilovasi.data.remote.repositoriesImpl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.dao.UserPlantDao;
import com.company.akilovasi.data.local.entities.UserPlant;
import com.company.akilovasi.data.remote.NetworkBoundResource;
import com.company.akilovasi.data.remote.api.UserPlantService;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.PlantAddRequest;
import com.company.akilovasi.data.remote.models.requests.PlantValueUpdateRequest;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.data.remote.repositories.UserPlantRepository;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class UserPlantRepositoryImpl implements UserPlantRepository {
    private static final String TAG = "UserPlantRepositoryImpl";

    Retrofit retrofit;

    UserPlantDao userPlantDao;

    UserPlantService userPlantService;

    @Inject
    public UserPlantRepositoryImpl(Retrofit retrofit, UserPlantDao userPlantDao){
        this.retrofit = retrofit;
        this.userPlantDao = userPlantDao;
        userPlantService = this.retrofit.create(UserPlantService.class);
        Log.d(TAG, "UserPlantRepositoryImpl: is constructed");
    }

    @Override
    public LiveData<Resource<List<UserPlant>>> getAllUserPlants(Long userId) {
        return new NetworkBoundResource<List<UserPlant>, Response<List<UserPlant>>>() {

            @Override
            protected void saveCallResult(@NonNull Response<List<UserPlant>> item) {
                if(item.getSuccess()){
                    Log.d(TAG, "saveCallResult: of plant types of item " + item.getCount() + " bind to " + userId);
                    userPlantDao.saveUserPlant(item.getResults());
                }else {
                    Log.e(TAG, "saveCallResult: Something went wrong");
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<UserPlant> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData< List<UserPlant>> loadFromDb() {
                Log.d(TAG, "loadFromDb: of user plants bind to user id " + userId);
                return userPlantDao.loadUserPlants();
            }

            @NonNull
            @Override
            protected Call<Response<List<UserPlant>>> createCall() {
                Log.d(TAG, "createCall: of plant types");
                return userPlantService.getUserPlants(userId);
            }
        }.getAsLiveData();
    }

    @Override
    public Call<Response<Message>> addUserPlant(Long plantId, Long userId, String plantName, float plantSize, float potSize) {
        return userPlantService.addUserPlant(new PlantAddRequest( plantId , userId , plantName, plantSize, potSize ));
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
        return userPlantService.addUserPlantWithImage(plantAddRequest, body);
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

        return userPlantService.uploadImage(userIdResponseBody, userPlantIdResponseBody, body);
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

        return userPlantService.updatePlantSensorValue(userId,userPlantId,body,sensPh,sensTemp, sensHumidity,sensLight,plantSize,pantPotSize);
    }
}
