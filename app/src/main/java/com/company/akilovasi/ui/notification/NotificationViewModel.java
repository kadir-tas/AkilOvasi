package com.company.akilovasi.ui.notification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.company.akilovasi.data.local.dao.NotificationDao;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.data.remote.repositories.NotificationRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class NotificationViewModel extends ViewModel {
    private static final String TAG = "NotificationViewModel";

    private NotificationRepository notificationRepository;

    private LiveData<List<Notification>> allNotifications;

    @Inject
    public NotificationViewModel(NotificationRepository  notificationRepository){
        this.notificationRepository = notificationRepository;
        allNotifications = notificationRepository.getAllNotifications();

    }

    public void deleteNotification(Notification notification){
        notificationRepository.deleteNotification(notification);
    }

    public void addNotifications(Notification notification){
        notificationRepository.addNotification(notification);
    }

    public void testRemoveAll(){
        notificationRepository.deleteAllNotifications();
    }

    public LiveData<List<Notification>> getNotifications(){
        return allNotifications;
    }

    public void produceDummyData(){
        List<Notification> notifications = new ArrayList<>();

        int color = 0;
        for (int i = 0; i < 10; i++) {
            Notification notification = new Notification();
            notification.setDate(new Date());
            notification.setMessage("This is the " + i + " th notification");
            notification.setType(Notification.Type.Default);
            notification.setVersion(1);
            notification.setExtra("TTFFFT");

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
        notificationRepository.addNotifications(notifications);
    }

}
