package com.company.akilovasi.data.remote.repositoriesImpl;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.dao.NotificationDao;
import com.company.akilovasi.data.local.entities.AnalysisResult;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.NetworkBoundResource;
import com.company.akilovasi.data.remote.api.NotificationService;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.requests.PlantAddRequest;
import com.company.akilovasi.data.remote.models.responses.PlantResponse;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.data.remote.repositories.NotificationRepository;
import com.company.akilovasi.di.SecretPrefs;
import com.company.akilovasi.util.AppConstants;
import com.google.android.gms.common.api.Api;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static com.company.akilovasi.util.AppConstants.EXISTING_SENSOR_TYPES;

public class NotificationsRepositoryImpl implements NotificationRepository {
    private static final String TAG = "NotificationsRepository";
    private NotificationDao notificationDao;
    private NotificationService notificationService;

    @Inject
    public NotificationsRepositoryImpl(Retrofit retrofit, NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
        this.notificationService = retrofit.create(NotificationService.class);
    }

    @Override
    public  MediatorLiveData<Resource<Response<Message>>> debugMock(Long userId) {
        final MediatorLiveData<Resource<Response<Message>>> resourceMutableLiveData = new MediatorLiveData<>();
        resourceMutableLiveData.setValue(Resource.loading(null));
        notificationService.debugMock( userId ).enqueue(new Callback<Response<Message>>() {
            @Override
            public void onResponse(Call<Response<Message>> call, retrofit2.Response<Response<Message>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getSuccess()){
                    resourceMutableLiveData.setValue(Resource.success(response.body()));
                }else {
                    resourceMutableLiveData.setValue(Resource.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Response<Message>> call, Throwable t) {
                resourceMutableLiveData.setValue(Resource.error(t.getMessage(), null));
            }
        });

        return resourceMutableLiveData;
    }

    @Override
    public  MediatorLiveData<Resource<Response<Message>>> updateFcmToken(Long userId,String token) {

        final MediatorLiveData<Resource<Response<Message>>> resourceMutableLiveData = new MediatorLiveData<>();
        resourceMutableLiveData.postValue(Resource.loading(null));
        notificationService.updateFcmToken( userId , token ).enqueue(new Callback<Response<Message>>() {
            @Override
            public void onResponse(Call<Response<Message>> call, retrofit2.Response<Response<Message>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getSuccess()){
                    resourceMutableLiveData.postValue(Resource.success(response.body()));
                }else {
                    resourceMutableLiveData.postValue(Resource.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Response<Message>> call, Throwable t) {
                resourceMutableLiveData.postValue(Resource.error(t.getMessage(), null));
            }
        });

        return resourceMutableLiveData;
    }

    @Override
    public MediatorLiveData<Resource<Response<Message>>> invalidateFcmToken(Long userId) {
        final MediatorLiveData<Resource<Response<Message>>> resourceMutableLiveData = new MediatorLiveData<>();
        resourceMutableLiveData.setValue(Resource.loading(null));
        notificationService.invalidateFcmToken( userId ).enqueue(new Callback<Response<Message>>() {
            @Override
            public void onResponse(Call<Response<Message>> call, retrofit2.Response<Response<Message>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getSuccess()){
                    resourceMutableLiveData.setValue(Resource.success(response.body()));
                }else {
                    resourceMutableLiveData.setValue(Resource.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Response<Message>> call, Throwable t) {
                resourceMutableLiveData.setValue(Resource.error(t.getMessage(), null));
            }
        });
        return resourceMutableLiveData;
    }

    @Override
    public MediatorLiveData<Resource<Response<List<Notification>>>> pollNotifications(Long userId) {
        final MediatorLiveData<Resource<Response<List<Notification>>>> resourceMutableLiveData = new MediatorLiveData<>();
        resourceMutableLiveData.postValue(Resource.loading(null));
        notificationService.pollNotifications( userId ).enqueue(new Callback<Response<List<Notification>>>() {
            @Override
            public void onResponse(Call<Response<List<Notification>>> call, retrofit2.Response<Response<List<Notification>>> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getSuccess()){
                    resourceMutableLiveData.postValue(Resource.success(response.body()));
                    addNotifications(response.body().getResults());
                }else {
                    resourceMutableLiveData.postValue(Resource.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Response<List<Notification>>> call, Throwable t) {
                resourceMutableLiveData.postValue(Resource.error(t.getMessage(), null));
            }
        });
        return resourceMutableLiveData;
    }

    @Override
    public void deleteNotification(Notification notification) {
        new deleteNotificationAsyncTask(notificationDao, notification).execute();
    }

    @Override
    public void addNotification(Notification notification) {
        parseExtraData(notification);
        new addNotificationAsyncTask(notificationDao, notification).execute();
    }

    @Override
    public void deleteAllNotifications() {
        new deleteAllNotificationsAsyncTask(notificationDao).execute();
    }

    @Override
    public LiveData<List<Notification>> getAllNotifications() {
        return notificationDao.getAllNotifications();
    }

    @Override
    public void addNotifications(List<Notification> notifications) {
        for(Notification n : notifications){
            parseExtraData(n);
        }
        new addNotificationsAsyncTask(notificationDao, notifications).execute();
    }

    @Override
    public LiveData<List<AnalysisResult>> getAllAnalysisResults(Long plantId) {
        return notificationDao.getAllAnalysisResults(plantId);
    }

    @Override
    public void deleteProblem(AnalysisResult analysisResult) {
        new deleteAnalysisResultAsyncTask(notificationDao,analysisResult).execute();
    }

    private static class deleteNotificationAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotificationDao notificationDao;
        private Notification notification;

        private deleteNotificationAsyncTask(NotificationDao notificationDao, Notification notification) {
            this.notification = notification;
            this.notificationDao = notificationDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notificationDao.deleteNotification(notification);
            return null;
        }
    }

    private static class addNotificationAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotificationDao notificationDao;
        private Notification notification;

        private addNotificationAsyncTask(NotificationDao notificationDao, Notification notification) {
            this.notificationDao = notificationDao;
            this.notification = notification;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notificationDao.addNotification(notification);
            return null;
        }
    }

    private static class addNotificationsAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotificationDao notificationDao;
        private List<Notification> notifications;

        private addNotificationsAsyncTask(NotificationDao notificationDao, List<Notification> notifications) {
            this.notificationDao = notificationDao;
            this.notifications = notifications;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notificationDao.addNotifications(notifications);
            return null;
        }
    }

    private static class deleteAllNotificationsAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotificationDao notificationDao;

        private deleteAllNotificationsAsyncTask(NotificationDao notificationDao) {
            this.notificationDao = notificationDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notificationDao.deleteAllNotifications();
            return null;
        }
    }

    private static class addAnalysisResultAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotificationDao notificationDao;
        private AnalysisResult analysisResult;

        private addAnalysisResultAsyncTask(NotificationDao notificationDao, AnalysisResult analysisResult) {
            this.notificationDao = notificationDao;
            this.analysisResult = analysisResult;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notificationDao.addAnalysisResult(analysisResult);
            return null;
        }
    }

    private static class deleteAnalysisResultAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotificationDao notificationDao;
        private AnalysisResult analysisResult;

        private deleteAnalysisResultAsyncTask(NotificationDao notificationDao, AnalysisResult analysisResult) {
            this.analysisResult = analysisResult;
            this.notificationDao = notificationDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notificationDao.deleteAnalysisResult(analysisResult);
            return null;
        }
    }

    private void parseExtraData(Notification notification) {
        switch (notification.getVersion()) {
            case 1:
                /**
                 * The characters represent, soilTemperature, soilHum, airTemperature, airHumidity, airPressure, environmentalLight respectively.
                 */
                if (notification.getExtra() != null && !notification.getExtra().isEmpty()) {
                    char[] array = notification.getExtra().toCharArray();
                    for(int i = 0; i < array.length; i++){
                        char c = array[i];
                        if(c == 'F'){
                            AnalysisResult analysisResult = new AnalysisResult();
                            analysisResult.setUserPlantId(notification.getUserPlantId());
                            analysisResult.setResultId(notification.getId());
                            analysisResult.setVersion(notification.getVersion());
                            analysisResult.setSensorType(EXISTING_SENSOR_TYPES[i]);
                            analysisResult.setMessage(EXISTING_SENSOR_TYPES[i].getSensorString() + " message");
                            new addAnalysisResultAsyncTask(notificationDao, analysisResult).execute();
                        }
                    }
                }
                break;
        }
    }
}
