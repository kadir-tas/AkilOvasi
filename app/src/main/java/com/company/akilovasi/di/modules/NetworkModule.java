package com.company.akilovasi.di.modules;

import android.app.Application;

import com.company.akilovasi.BuildConfig;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.RequestInterceptor;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitClient(OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Application application) {
        final int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .readTimeout(ApiConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS)
                .connectTimeout(ApiConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS)
                .addInterceptor(new RequestInterceptor())
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }
}