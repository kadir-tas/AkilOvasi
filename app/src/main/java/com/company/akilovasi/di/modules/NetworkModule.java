package com.company.akilovasi.di.modules;

import android.app.Application;
import android.content.SharedPreferences;

import com.company.akilovasi.BuildConfig;
import com.company.akilovasi.data.remote.ApiConstants;
import com.company.akilovasi.data.remote.RequestInterceptor;
import com.company.akilovasi.data.remote.repositories.UserRepository;
import com.company.akilovasi.data.remote.TokenAuthenticator;
import com.company.akilovasi.di.SecretPrefs;
import com.google.gson.Gson;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Lazy;
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
    OkHttpClient provideOkHttpClient(Application application, @SecretPrefs SharedPreferences secretPreferences, Lazy<UserRepository> userRepositoryLazyWrapper) {
        final int cacheSize = 10 * 1024 * 1024;
       // Cache cache = new okhttp3.Cache();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .authenticator(new TokenAuthenticator(secretPreferences, userRepositoryLazyWrapper))
                .readTimeout(ApiConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS)
                .connectTimeout(ApiConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS)
                .addInterceptor(new RequestInterceptor(secretPreferences))
                .addInterceptor(loggingInterceptor)
              //  .cache( new Cache(application.getCacheDir() , Integer.MAX_VALUE) )
                .build();
    }

    @Provides
    @Singleton
    Picasso providePicasso(OkHttpClient client, Application application){
        return new Picasso.Builder(application).downloader(new OkHttp3Downloader(client)).loggingEnabled(true).indicatorsEnabled(true).build();

    }
}