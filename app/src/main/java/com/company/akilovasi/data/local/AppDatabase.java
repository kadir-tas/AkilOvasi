package com.company.akilovasi.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.company.akilovasi.data.local.dao.BannerDao;
import com.company.akilovasi.data.local.dao.NotificationDao;
import com.company.akilovasi.data.local.dao.PlantDao;
import com.company.akilovasi.data.local.dao.PlantHistoryDao;
import com.company.akilovasi.data.local.dao.PlantTypeDao;
import com.company.akilovasi.data.local.dao.UserDao;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.local.entities.Login;
import com.company.akilovasi.data.local.entities.Notification;
import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.local.entities.User;
import com.company.akilovasi.util.Converters;

@Database(entities = {User.class,
        PlantType.class,
        Banner.class,
        Plant.class,
        PlantHistory.class,
        Notification.class,
        Login.class},
        version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlantTypeDao plantTypeDao();

    public abstract UserDao userDao();

    public abstract BannerDao bannerDao();

    public abstract PlantDao plantDao();

    public abstract PlantHistoryDao plantHistoryDao();

    public abstract NotificationDao notificationDao();
}