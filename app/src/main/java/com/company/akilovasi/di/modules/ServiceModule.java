package com.company.akilovasi.di.modules;

import com.company.akilovasi.service.FCMService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class ServiceModule {
    @ContributesAndroidInjector
    abstract FCMService contributeMyFirebaseMessagingService();
}
