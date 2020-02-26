package com.company.akilovasi.di.modules;

import com.company.akilovasi.ui.main.fragments.history.PlantHistoryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract PlantHistoryFragment plantHistoryFragment();

}

