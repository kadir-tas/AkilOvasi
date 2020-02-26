package com.company.akilovasi.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.company.akilovasi.data.local.entities.UserPlant;
import com.company.akilovasi.data.remote.models.responses.Response;

import java.util.List;

@Dao
public interface UserPlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveUserPlant(List<UserPlant> userPlantList);

    @Query("SELECT * FROM userPlants")
    LiveData<List<UserPlant>> loadUserPlants();

    @Query("SELECT * FROM userPlants WHERE userPlantId =:userPlantId")
    LiveData<UserPlant> loadUserPlant(Long userPlantId);

}
