package com.company.akilovasi.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.company.akilovasi.data.local.entities.PlantType;
import com.company.akilovasi.data.local.entities.ShopItem;

import java.util.List;

@Dao
public interface ShopItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveShopItems(List<ShopItem> shopItems);

    @Query("SELECT * FROM shopItems")
    LiveData< List<ShopItem>> loadShopItems();

    @Query("SELECT * FROM shopItems WHERE category=:category")
    LiveData<List<ShopItem>> loadByCategory( String category );

    @Query("SELECT * FROM shopItems WHERE subCategory=:subCategory")
    LiveData<List<ShopItem>> loadPlantBySubCategories( String subCategory );

    @Query("SELECT * FROM shopItems WHERE category=:category AND subCategory=:subCategory")
    LiveData<List<ShopItem>> loadPlantByCategoryAndSubCategories(String category , String subCategory );

    @Query("SELECT DISTINCT category FROM shopItems  ORDER BY category ASC")
    LiveData<List<String>> getListOfCategory();
}
