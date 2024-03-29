package com.company.akilovasi.di.modules.shopItem;

import com.company.akilovasi.data.local.dao.ShopItemDao;
import com.company.akilovasi.data.remote.api.ShopItemService;
import com.company.akilovasi.data.remote.repositories.ShopItemsRepository;
import com.company.akilovasi.data.remote.repositoriesImpl.ShopItemsRepositoryImpl;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ShopItemModule {
    @Provides
    @Singleton
    ShopItemsRepository provideBlogRepository(Retrofit retrofit, ShopItemDao shopItemDao){
        return new ShopItemsRepositoryImpl(retrofit , shopItemDao);
    }
}
