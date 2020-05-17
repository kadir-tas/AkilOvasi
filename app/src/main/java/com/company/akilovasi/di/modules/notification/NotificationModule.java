package com.company.akilovasi.di.modules.notification;

import com.company.akilovasi.data.local.dao.NotificationDao;
import com.company.akilovasi.data.remote.repositories.NotificationRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.NotificationsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class NotificationModule {

    @Provides
    @Singleton
    NotificationRepository provideNotificationRepository(Retrofit retrofit, NotificationDao notificationDao){
        return new NotificationsRepositoryImpl(retrofit, notificationDao);
    }

}
