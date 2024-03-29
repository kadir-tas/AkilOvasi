package com.company.akilovasi.di.modules;

import android.app.Application;

import androidx.room.Room;

import com.company.akilovasi.data.local.AppDatabase;
import com.company.akilovasi.data.local.dao.BannerDao;
import com.company.akilovasi.data.local.dao.BlogDao;
import com.company.akilovasi.data.local.dao.NotificationDao;
import com.company.akilovasi.data.local.dao.PlantDao;
import com.company.akilovasi.data.local.dao.PlantHistoryDao;
import com.company.akilovasi.data.local.dao.PlantTypeDao;
import com.company.akilovasi.data.local.dao.ShopItemDao;
import com.company.akilovasi.data.local.dao.SupportTicketDao;
import com.company.akilovasi.data.local.dao.UserDao;
import com.company.akilovasi.data.remote.ApiConstants;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class LocalModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "aa.db").build();
    }

    @Provides
    @Singleton
    PlantTypeDao providePlantTypeDao(AppDatabase appDatabase) {
        return appDatabase.plantTypeDao();
    }

    @Provides
    @Singleton
    BannerDao provideBannerDao(AppDatabase appDatabase) {
        return appDatabase.bannerDao();
    }

    @Provides
    @Singleton
    PlantDao providePlantDao(AppDatabase appDatabase) {
        return appDatabase.plantDao();
    }

    @Provides
    @Singleton
    PlantHistoryDao providePlantHistoryDao(AppDatabase appDatabase) {
        return appDatabase.plantHistoryDao();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }

    @Provides
    @Singleton
    NotificationDao provideNotificationDao(AppDatabase appDatabase){ return appDatabase.notificationDao(); }

    @Provides
    @Singleton
    SupportTicketDao provideSupportTicketDao(AppDatabase appDatabase){ return appDatabase.supportTicketDao(); }

    @Provides
    @Singleton
    BlogDao provideBlogDao(AppDatabase appDatabase){ return appDatabase.blogDao();}

    @Provides
    @Singleton
    ShopItemDao provideShopItemDao(AppDatabase appDatabase){ return  appDatabase.shopItemDao(); }
}
