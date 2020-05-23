package com.company.akilovasi.di.modules;

import com.company.akilovasi.service.FCMService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class ServiceModule {
    // for my case, the service class which needs injection is MyFirebaseMessagingService
    @ContributesAndroidInjector
    abstract FCMService contributeMyFirebaseMessagingService();
}
