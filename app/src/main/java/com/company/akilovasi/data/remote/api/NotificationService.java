package com.company.akilovasi.data.remote.api;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.data.remote.models.other.Message;
import com.company.akilovasi.data.remote.models.responses.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NotificationService {

    @Headers("Cache-Control: public, max-stale=604800")
    @POST("notification/poll")
    Call< Response<List<Notification>> > pollNotifications(@Query("userId") Long userId);

    @Headers("Cache-Control: public, max-stale=604800")
    @POST("notification/fcm/token/update")
    Call< Response<Message> > updateFcmToken(@Query("userId") Long userId, @Query("fcmToken") String fcmToken);

    @Headers("Cache-Control: public, max-stale=604800")
    @POST("notification/fcm/token/invalidate")
    Call< Response<Message> > invalidateFcmToken(@Query("userId") Long userId);

    @Headers("Cache-Control: public, max-stale=604800")
    @POST("notification/debug/mock")
    Call< Response<Message> > debugMock(@Query("userId") Long userId);
}
