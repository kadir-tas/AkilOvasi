package com.company.akilovasi.data.remote.repositoriesImpl;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.local.dao.NotificationDao;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.data.remote.repositories.NotificationRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class NotificationsRepositoryImpl implements NotificationRepository {

    Retrofit retrofit;
    NotificationDao notificationDao;

    private LiveData<List<Notification>> allNotifications;
    @Inject
    public NotificationsRepositoryImpl(Retrofit retrofit, NotificationDao notificationDao) {
        this.retrofit = retrofit;
        this.notificationDao = notificationDao;
        allNotifications = notificationDao.getAllNotifications();
    }


    @Override
    public void deleteNotification(Notification notification) {
        new deleteNotificationAsyncTask(notificationDao,notification).execute();
    }

    @Override
    public void addNotification(Notification notification) {
        new addNotificationAsyncTask(notificationDao,notification).execute();
    }

    @Override
    public void deleteAllNotifications() {
        new deleteAllNotificationsAsyncTask(notificationDao).execute();
    }

    @Override
    public LiveData<List<Notification>> getAllNotifications() {
        return allNotifications;
    }

    @Override
    public void addNotifications(List<Notification> notifications) {
        new addNotificationsAsyncTask(notificationDao,notifications).execute();
    }


    private static class deleteNotificationAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotificationDao notificationDao;
        private Notification notification;

        private deleteNotificationAsyncTask(NotificationDao notificationDao, Notification notification) {
            this.notification =  notification;
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
}
