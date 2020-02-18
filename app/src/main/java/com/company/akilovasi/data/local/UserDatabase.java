package com.company.akilovasi.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.company.akilovasi.data.local.dao.UserDao;
import com.company.akilovasi.data.local.entities.User;

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}