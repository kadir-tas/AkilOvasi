package com.company.akilovasi.data.remote.api;

import com.company.akilovasi.data.local.entities.Blog;
import com.company.akilovasi.data.local.entities.BlogPreview;
import com.company.akilovasi.data.local.entities.ShopItem;
import com.company.akilovasi.data.remote.models.responses.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShopItemService {

    @Headers("Cache-Control: public, max-stale=604800")
    @GET("shop/get/all")
    Call<Response<List<ShopItem>>> getAllShopItems();
}
