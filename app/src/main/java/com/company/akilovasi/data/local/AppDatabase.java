package com.company.akilovasi.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.company.akilovasi.data.local.dao.BannerDao;
import com.company.akilovasi.data.local.dao.PlantTypeDao;
import com.company.akilovasi.data.local.dao.UserDao;
import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.local.entities.User;

@Database(entities = {User.class,
        PlantType.class,
        Banner.class},
        version = 2/*, exportSchema = false*/)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlantTypeDao plantTypeDao();

    public abstract UserDao userDao();

    public abstract BannerDao bannerDao();
}