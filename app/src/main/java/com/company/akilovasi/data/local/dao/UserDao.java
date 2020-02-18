package com.company.akilovasi.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.company.akilovasi.data.local.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveLogin(List<User> users);

    @Query("SELECT * FROM users")
    LiveData<List<User>> loadLogin();
}
