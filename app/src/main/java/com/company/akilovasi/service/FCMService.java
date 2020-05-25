package com.company.akilovasi.service;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleOwner;

import com.company.akilovasi.R;
import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.repositories.NotificationRepository;
import com.company.akilovasi.di.SecretPrefs;
import com.company.akilovasi.ui.plantanalysis.PlantAnalysisActivity;
import com.company.akilovasi.util.AppConstants;
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
                if(intent == null) return;

                switch (intent) {
                    case "notify":
                        String notfCount = data.get("notificationCount");
                        if (notfCount != null) {
                            int notficationCount = Integer.parseInt(notfCount);
                            if (notficationCount < 20) {
                                //Only poll notification here if the action will take less then 10 seconds I assumed that 20 notifications could take long but I am not sure ??????
                                notificationRepository.pollNotifications(secretPreferences.getLong(ApiConstants.USER_ID, -1));
                                sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                            } else {
                                //TODO: Its probably too large to poll here so, create some flag so that application will now recent notifications has not been polled
                            }
                        }
                        break;
                    case "message":
                        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                        break;
                    case "remind-analysis":
                        //TODO: Null Check
                        notificationRepository.pollNotifications(secretPreferences.getLong(ApiConstants.USER_ID, -1));
                        sendRemindAnalysisNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(),remoteMessage.getData().get("userPlantId"));
                        break;
                    default:
                        Log.e(TAG, "onMessageReceived: unidentified case for intent " + intent);
                        break;
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(AppConstants.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null)
                notificationManager.createNotificationChannel(channel);
            else
                Log.e(TAG, "createNotificationChannel: notificationManager null");
        }
    }

    private void sendRemindAnalysisNotification(String title, String content, String userPlantId){
        createNotificationChannel();
        Intent intent = new Intent(this, PlantAnalysisActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Log.d(TAG, "sendRemindAnalysisNotification: " + userPlantId);
        intent.putExtra( PlantAnalysisActivity.PARAM_USER_PLANT, userPlantId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), AppConstants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_info)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat.from(getBaseContext()).notify( 1, builder.build() );
    }

    private void sendNotification(String title, String content){
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), AppConstants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_info)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat.from(getBaseContext()).notify( 2, builder.build() );
    }
}
