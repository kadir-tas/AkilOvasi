package com.company.akilovasi.data.local.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.company.akilovasi.data.local.entities.PlantHistory;

import java.util.List;

@Dao
public interface PlantHistoryDao {

    @Query("SELECT * FROM plant_history WHERE id=:userPlantId")
    LiveData<List<PlantHistory>> getUserPlantHistory(Long userPlantId);

}
