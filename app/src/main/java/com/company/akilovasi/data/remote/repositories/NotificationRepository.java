package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.responses.Response;

import java.util.List;

import retrofit2.Call;

public interface NotificationRepository {

    MediatorLiveData<Resource<Response<Message>>> debugMock(Long userId);

    MediatorLiveData<Resource<Response<Message>>> updateFcmToken(Long userId,String token);

    MediatorLiveData<Resource<Response<Message>>> invalidateFcmToken(Long userId);

    MediatorLiveData<Resource<Response<List<Notification>>>> pollNotifications(Long userId);

    void deleteNotification(Notification notification);

    void addNotification(Notification notification);

    void deleteAllNotifications();

    LiveData<List<Notification>> getAllNotifications();

    void addNotifications(List<Notification> notifications);
}
