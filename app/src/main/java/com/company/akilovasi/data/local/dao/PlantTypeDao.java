package com.company.akilovasi.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.company.akilovasi.data.local.entities.PlantType;

import java.util.List;

@Dao
public interface PlantTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePlantTypes(List<PlantType> plantTypes);

    @Query("SELECT * FROM plantType")
    LiveData<List<PlantType>> loadPlantTypes();

    @Query("SELECT * FROM plantType WHERE plantId=:plantId")
    LiveData<PlantType> getPlantType(Long plantId);

}
