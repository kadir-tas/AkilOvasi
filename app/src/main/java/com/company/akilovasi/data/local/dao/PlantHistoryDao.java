package com.company.akilovasi.data.local.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.company.akilovasi.data.local.entities.PlantHistory;

import java.util.List;

@Dao
public interface PlantHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePlantHistory(List<PlantHistory> plantHistories);

    @Query("SELECT * FROM plant_history WHERE userPlantId=:userPlantId ORDER BY dateSubmited DESC")
    LiveData<List<PlantHistory>> getUserPlantHistory(Long userPlantId);

    @Query("SELECT * FROM plant_history WHERE userPlantId=:userPlantId AND pageId=:pageId ORDER BY dateSubmited DESC")
    LiveData<List<PlantHistory>> getUserPlantHistoryPaged(Long userPlantId, int pageId);

    @Query("DELETE FROM plant_history")
    void clearPlantsHistory();

}
