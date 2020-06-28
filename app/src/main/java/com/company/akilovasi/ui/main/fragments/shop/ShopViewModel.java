package com.company.akilovasi.ui.main.fragments.shop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.entities.ShopItem;
import com.company.akilovasi.data.remote.repositories.ShopItemsRepository;

import java.util.List;

import javax.inject.Inject;

public class ShopViewModel extends ViewModel {

    private final ShopItemsRepository shopItemsRepository;

    @Inject
    public ShopViewModel(ShopItemsRepository shopItemsRepository) {
        this.shopItemsRepository = shopItemsRepository;
    }

    public LiveData<Resource<List<ShopItem>>> getAllItems(){
        return shopItemsRepository.loadShopItems();
    }

    public LiveData<List<String>> getListOfCategory(){
        return shopItemsRepository.getListOfCategory();
    }

    public LiveData<Resource<List<ShopItem>>> loadByCategories(String category){
        return shopItemsRepository.loadByCategory(category);
    }

    public LiveData<Resource<List<ShopItem>>> loadPlantByCategoryAndSubCategories(String category, String subCategory){
        return shopItemsRepository.loadShopItemsByCategoryAndSubCategories(category, subCategory);
    }
}
