package com.company.akilovasi.di.modules;

import com.company.akilovasi.ui.common.fullscreen.PlantFullImageFragment;
import com.company.akilovasi.ui.login.fragments.RegisterFragment;
import com.company.akilovasi.ui.main.fragments.history.PlantHistoryFragment;

import com.company.akilovasi.ui.main.fragments.profile.ProfileFragment;
import com.company.akilovasi.ui.notification.NotificationFragment;
import com.company.akilovasi.ui.plant.fragments.addplant.PlantAddFragment;
import com.company.akilovasi.ui.plant.fragments.plantcategory.PlantCategoryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract PlantCategoryFragment plantCategoryFragment();

    @ContributesAndroidInjector
    abstract PlantHistoryFragment plantHistoryFragment();

    @ContributesAndroidInjector
    abstract PlantAddFragment plantAddFragment();

    @ContributesAndroidInjector
    abstract RegisterFragment registerFragment();

    @ContributesAndroidInjector
    abstract PlantFullImageFragment plantFullImageFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment profileFragment();

    @ContributesAndroidInjector
    abstract NotificationFragment notificationFragment();
}

