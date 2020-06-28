package com.company.akilovasi.data.remote.repositories;

import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.ShopItem;

import java.util.List;

public interface ShopItemsRepository {
    LiveData<Resource<List<ShopItem>>> loadShopItems();
    LiveData<Resource<List<ShopItem>>> loadByCategory( String category );
    LiveData<Resource<List<ShopItem>>> loadShopItemsBySubCategories(String subCategory );
    LiveData<Resource<List<ShopItem>>> loadShopItemsByCategoryAndSubCategories(String category , String subCategory );
    LiveData<List<String>> getListOfCategory();
}
