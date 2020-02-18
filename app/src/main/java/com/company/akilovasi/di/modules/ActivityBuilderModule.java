package com.company.akilovasi.di.modules;

import com.company.akilovasi.ui.login.LoginActivity;
import com.company.akilovasi.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();
}

