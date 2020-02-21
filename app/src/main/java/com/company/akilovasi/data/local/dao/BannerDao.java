package com.company.akilovasi.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.remote.models.responses.BannerResponse;

import java.util.List;

@Dao
public interface BannerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveBanners(List<Banner> banners);

    @Query("SELECT * FROM banners")
    LiveData<List<Banner>> loadBanners();

    @Query("SELECT * FROM banners WHERE bannerId=:bannerId")
    LiveData<Banner> getBanner(Long bannerId);

}
