package com.company.akilovasi.service;


import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.repositories.NotificationRepository;
import com.company.akilovasi.di.SecretPrefs;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class FCMService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FCMService";

    @Inject
    @SecretPrefs
    SharedPreferences secretPreferences;

    @Inject
    NotificationRepository notificationRepository;

    public FCMService() {
        Log.d(TAG, "FCMService: Started");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    /**
     * if token is changed on the run notify the backend server
     * @param s
     */
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "onNewToken: " + s);
        if(secretPreferences == null)
            Log.d(TAG, "onNewToken: secretPreferences is null");
        notificationRepository.updateFcmToken( secretPreferences.getLong(ApiConstants.USER_ID,-1) , s);
    }

    /**
     * This callback will only run if the app is currently running on the foreground, meaning if user currently is inside the application and doing some stuff also normal android notification bar will not show
     * so it needs to be created manually
     * @param remoteMessage
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            Map<String,String> data = remoteMessage.getData();
            if ( data != null && data.containsKey("intent")) {
                String intent = data.get("intent");
                if(intent != null && intent.equals("notify")){
                    String notfCount = data.get("notificationCount");
                    if(notfCount != null){
                        int notficationCount = Integer.parseInt(notfCount);
                        if( notficationCount < 20){
                            //Only poll notification here if the action will take less then 10 seconds I assumed that 20 notifications could take long but I am not sure ??????
                            notificationRepository.pollNotifications(secretPreferences.getLong(ApiConstants.USER_ID,-1));
                            //TODO: sendNotification() does not work  so user wont see any notification while in app
                            //sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                        }else{
                            //TODO: Its probably too large to poll here so, create some flag so that application will now recent notifications has not been polled
                        }
                    }
                }else if(intent.equals("general")){
                    //TODO: This is a general push notification no need to poll but do other stuff if needed here
                }
            } else{
                //TODO: If data does not contains 'intent' meaning this push notification is not sent from the backend server so if need some handling do handling
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    //TODO: Create notification upon receiving in app https://developer.android.com/training/notify-user/channels
    private void sendNotification(String title, String content){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "test_channel")
                .setSmallIcon(R.drawable.analysis)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
}
