package com.company.akilovasi.di.modules;

import android.app.Application;

import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.company.akilovasi.data.local.AppDatabase;
import com.company.akilovasi.data.local.dao.BannerDao;
import com.company.akilovasi.data.local.dao.PlantDao;
import com.company.akilovasi.data.local.dao.PlantHistoryDao;
import com.company.akilovasi.data.local.dao.PlantTypeDao;
import com.company.akilovasi.data.local.dao.UserPlantDao;
import com.company.akilovasi.ui.BaseActivity;
import com.company.akilovasi.ui.plant.PlantCategoryActivity;
import com.company.akilovasi.ui.plant.PlantCategoryPagerAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "aa.db").build();
    }

    @Provides
    @Singleton
    PlantTypeDao providePlantTypeDao(AppDatabase appDatabase){
        return appDatabase.plantTypeDao();
    }

    @Provides
    @Singleton
    BannerDao provideBannerDao(AppDatabase appDatabase) {
        return appDatabase.bannerDao();
    }

    @Provides
    @Singleton
    UserPlantDao provideUserPlantDao(AppDatabase appDatabase){ return appDatabase.userPlantDao(); }

    @Provides
    @Singleton
    PlantDao providePlantDao(AppDatabase appDatabase) { return appDatabase.plantDao();
    }

    @Provides
    @Singleton
    PlantHistoryDao providePlantHistoryDao(AppDatabase appDatabase) { return appDatabase.plantHistoryDao();
    }
}
