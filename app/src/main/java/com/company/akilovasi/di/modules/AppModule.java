package com.company.akilovasi.di.modules;

import android.content.Context;

import com.company.akilovasi.AkilOvasiApp;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {

    @Binds
    abstract Context provideContext(AkilOvasiApp app);

}















































//    @Singleton
//    @Provides
//    Gson provideGson() {
//        return new Gson();
//    }
//
//    @Provides
//    @Singleton
//    Retrofit provideRetrofitClient(OkHttpClient okHttpClient) {
//        return new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
//    }
//
//
//
//    @Provides
//    @Singleton
//    OkHttpClient provideOkHttpClient(Application application) {
//        final int cacheSize = 10 * 1024 * 1024;
//        Cache cache = new Cache(application.getCacheDir(), cacheSize);
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        return new OkHttpClient.Builder()
//                .readTimeout(ApiConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS)
//                .connectTimeout(ApiConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS)
//                .addInterceptor(new RequestInterceptor())
//                .addInterceptor(loggingInterceptor)
//                .cache(cache)
//                .build();
//    }
//
////
////    @Provides
////    @Singleton
////    OkHttpClient provideOkHttpClient() {
////        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
////        okHttpClient.connectTimeout(ApiConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS);
////        okHttpClient.readTimeout(ApiConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS);
////        okHttpClient.addInterceptor(new RequestInterceptor());
////        return okHttpClient.build();
////    }
//
//    @Provides
//    @Singleton
//    UserService provideRetrofit(OkHttpClient okHttpClient) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BuildConfig.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        return retrofit.create(UserService.class);
//    }
//
//    @Provides
//    @Singleton
//    AppDatabase provideMovieDatabase(Application application) {
//        return Room.databaseBuilder(application, AppDatabase.class, "aa.db").build();
//    }
//
//    @Provides
//    @Singleton
//    UserDao provideMovieDao(AppDatabase userDatabase) {
//        return userDatabase.userDao();
//    }
//}
//
//
//
//









//
//    @Provides
//    @Singleton
//    AppDatabase provideUserDatabase(Application application) {
//        return Room.databaseBuilder(application, AppDatabase.class, "aa.db").build();
//    }
//
//
//    @Provides
//    @Singleton
//    UserService provideUserService(Retrofit retrofit) {
//        return retrofit.create(UserService.class);
//    }

//    @Provides
//    @Singleton
//    LoginService provideLoginService(Retrofit retrofit) {
//        return retrofit.create(LoginService.class);
//    }


//    @Provides
//    @Singleton
//    UserDao provideUserDao(AppDatabase userDatabase) {
//        return userDatabase.userDao();
//    }
//


//    @Provides
//    @Singleton
//    UserRepository provideUserRepository(UserDao userDao, UserService userService){
//        return new UserRepository(userDao,userService);
//    }



//}