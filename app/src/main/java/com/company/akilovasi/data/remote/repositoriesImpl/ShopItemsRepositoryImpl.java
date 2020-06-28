package com.company.akilovasi.data.remote.repositoriesImpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.company.akilovasi.data.Resource;
import com.company.akilovasi.data.local.dao.ShopItemDao;
import com.company.akilovasi.data.local.entities.ShopItem;
import com.company.akilovasi.data.remote.NetworkBoundResource;
import com.company.akilovasi.data.remote.api.ShopItemService;
import com.company.akilovasi.data.remote.models.responses.Response;
import com.company.akilovasi.data.remote.repositories.ShopItemsRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ShopItemsRepositoryImpl implements ShopItemsRepository {

    private final ShopItemDao shopItemDao;
    private final ShopItemService shopItemService;
    private final Retrofit retrofit;

    public ShopItemsRepositoryImpl(Retrofit retrofit, ShopItemDao shopItemDao) {
        this.retrofit = retrofit;
        this.shopItemDao = shopItemDao;
        this.shopItemService = retrofit.create(ShopItemService.class);
    }

    @Override
    public LiveData<Resource<List<ShopItem>>> loadShopItems() {
        return new NetworkBoundResource<List<ShopItem>, Response<List<ShopItem>>>() {

            @Override
            protected void saveCallResult(@NonNull Response<List<ShopItem>> item) {
                if(item.getSuccess()){
                    shopItemDao.saveShopItems(item.getResults());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ShopItem> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData< List<ShopItem>>  loadFromDb() {
                return shopItemDao.loadShopItems();
            }

            @NonNull
            @Override
            protected Call< Response<List<ShopItem>> > createCall() {
                return shopItemService.getAllShopItems();
            }
        }.getAsLiveData();
    }

    @Override
    public LiveData<Resource<List<ShopItem>>> loadByCategory(String category) {
        return new NetworkBoundResource<List<ShopItem>, Response<List<ShopItem>>>() {

            @Override
            protected void saveCallResult(@NonNull Response<List<ShopItem>> item) {
                if(item.getSuccess()){
                    shopItemDao.saveShopItems(item.getResults());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ShopItem> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData< List<ShopItem>>  loadFromDb() {
                return shopItemDao.loadByCategory(category);
            }

            @NonNull
            @Override
            protected Call< Response<List<ShopItem>> > createCall() {
                return shopItemService.getAllShopItems();
            }
        }.getAsLiveData();
    }

    @Override
    public LiveData<Resource<List<ShopItem>>> loadShopItemsBySubCategories(String subCategory) {
        return new NetworkBoundResource<List<ShopItem>, Response<List<ShopItem>>>() {

            @Override
            protected void saveCallResult(@NonNull Response<List<ShopItem>> item) {
                if(item.getSuccess()){
                    shopItemDao.saveShopItems(item.getResults());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ShopItem> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData< List<ShopItem>>  loadFromDb() {
                return shopItemDao.loadPlantBySubCategories(subCategory);
            }

            @NonNull
            @Override
            protected Call< Response<List<ShopItem>> > createCall() {
                return shopItemService.getAllShopItems();
            }
        }.getAsLiveData();
    }

    @Override
    public LiveData<Resource<List<ShopItem>>> loadShopItemsByCategoryAndSubCategories(String category, String subCategory) {
        return new NetworkBoundResource<List<ShopItem>, Response<List<ShopItem>>>() {

            @Override
            protected void saveCallResult(@NonNull Response<List<ShopItem>> item) {
                if(item.getSuccess()){
                    shopItemDao.saveShopItems(item.getResults());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ShopItem> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData< List<ShopItem>>  loadFromDb() {
                return shopItemDao.loadPlantByCategoryAndSubCategories(category ,subCategory);
            }

            @NonNull
            @Override
            protected Call< Response<List<ShopItem>> > createCall() {
                return shopItemService.getAllShopItems();
            }
        }.getAsLiveData();
    }

    @Override
    public LiveData<List<String>> getListOfCategory() {
        return shopItemDao.getListOfCategory();
    }
}
