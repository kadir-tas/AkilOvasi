package com.company.akilovasi.data.remote.api;

import com.company.akilovasi.data.local.entities.Banner;
import com.company.akilovasi.data.remote.models.responses.BannerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface BannerService {

    @Headers("Cache-Control: public, max-stale=604800")
    @GET("banner/get/all")
    Call<List<Banner>> loadBanners();

}
