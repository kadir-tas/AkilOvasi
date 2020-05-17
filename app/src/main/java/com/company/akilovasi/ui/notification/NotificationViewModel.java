package com.company.akilovasi.ui.notification;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.company.akilovasi.data.local.dao.NotificationsDao;
import com.company.akilovasi.data.local.entities.Notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

import io.reactivex.Completable;

public class NotificationViewModel extends ViewModel {
    private static final String TAG = "NotificationViewModel";

    private NotificationsDao notificationsDao;

    @Inject
    public NotificationViewModel(NotificationsDao notificationsDao){
        this.notificationsDao = notificationsDao;
    }

    public void deleteNotification(Notification notification){
        notificationsDao.deleteNotification(notification);
    }

    public void addNotifications(Notification notification){
        notificationsDao.addNotification(notification);
    }

    public void testRemoveAll(){
        notificationsDao.deleteAllNotifications();
    }

    public LiveData<List<Notification>> getNotifications(){
        return notificationsDao.getAllNotifications();
    }

    public void produceDummyData(){
        List<Notification> notifications = new ArrayList<>();

        int color = 0;
        for (int i = 0; i < 10; i++) {
            Notification notification = new Notification();
            notification.setDate(new Date());
            notification.setMessage("This is the " + i + " th notification");
            notification.setType(Notification.Type.Default);

            if(i % 2 == 0){
                notification.setType(Notification.Type.Default);
            }else if(i % 3 == 0){
                notification.setType(Notification.Type.GeneralNotification);
            }else if(i % 5 == 0){
                notification.setType(Notification.Type.RemindAnalysis);
            }else if(i % 7 == 0){
                notification.setType(Notification.Type.RemindCare);
            }

            notifications.add(notification);
        }
        notificationsDao.addNotifications(notifications);
    }

}
