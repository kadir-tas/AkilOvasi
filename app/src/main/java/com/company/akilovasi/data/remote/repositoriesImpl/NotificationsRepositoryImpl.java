package com.company.akilovasi.data.remote.repositoriesImpl;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.local.dao.NotificationDao;
import com.company.akilovasi.data.local.entities.AnalysisResult;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.data.remote.repositories.NotificationRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

import static com.company.akilovasi.util.AppConstants.EXISTING_SENSOR_TYPES;

public class NotificationsRepositoryImpl implements NotificationRepository {

    Retrofit retrofit;
    NotificationDao notificationDao;

    @Inject
    public NotificationsRepositoryImpl(Retrofit retrofit, NotificationDao notificationDao) {
        this.retrofit = retrofit;
        this.notificationDao = notificationDao;
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
                            analysisResult.setMessage(EXISTING_SENSOR_TYPES[i].getSensorType() + " message");
                            new addAnalysisResultAsyncTask(notificationDao, analysisResult).execute();
                        }
                    }
                }
                break;
        }
    }
}
