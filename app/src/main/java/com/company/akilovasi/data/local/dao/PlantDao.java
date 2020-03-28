package com.company.akilovasi.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.company.akilovasi.data.local.entities.Plant;
import com.company.akilovasi.data.local.entities.PlantHistory;
import com.company.akilovasi.data.local.entities.UserPlant;

import java.util.List;

@Dao
public interface PlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePlants(List<Plant> plants);

    @Query("SELECT * FROM plants")
    LiveData<List<Plant>> loadPlants();

    @Query("SELECT * FROM plants WHERE userId=:userId")
    LiveData<List<Plant>> getUserPlants(Long userId);

    @Query("SELECT * FROM plants WHERE userPlantId =:userPlantId")
    LiveData<Plant> loadUserPlant(Long userPlantId);
}
