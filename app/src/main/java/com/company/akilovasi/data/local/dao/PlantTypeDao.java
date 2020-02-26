package com.company.akilovasi.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.remote.models.responses.Response;

import java.util.List;

@Dao
public interface PlantTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePlantTypes(List<PlantType> plantTypes);

    @Query("SELECT * FROM plantType")
    LiveData< List<PlantType>> loadPlantTypes();

    @Query("SELECT * FROM plantType WHERE plantCategory = :plantCategory")
    LiveData<List<PlantType>> loadPlantByCategories( String plantCategory );

    @Query("SELECT DISTINCT plantCategory FROM plantType")
    LiveData<List<String>> loadListOfPlantCategories();

    @Query("SELECT * FROM plantType WHERE plantId=:plantId")
    LiveData<PlantType> getPlantType(Long plantId);
}
