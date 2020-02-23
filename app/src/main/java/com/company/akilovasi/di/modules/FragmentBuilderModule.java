package com.company.akilovasi.di.modules;

import com.company.akilovasi.ui.plant.fragments.addplant.PlantAddFragment;
import com.company.akilovasi.ui.plant.fragments.plantcategory.PlantCategoryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract PlantCategoryFragment plantCategoryFragment();

    @ContributesAndroidInjector
    abstract PlantAddFragment plantAddFragment();
}

