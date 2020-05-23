package com.company.akilovasi.di.modules;

import com.company.akilovasi.ui.analysisresult.AnalysisResultActivity;
import com.company.akilovasi.ui.camera.CameraActivity;
import com.company.akilovasi.ui.login.LoginActivity;
import com.company.akilovasi.ui.main.MainActivity;
import com.company.akilovasi.ui.plant.PlantCategoryActivity;
import com.company.akilovasi.ui.plantanalysis.PlantAnalysisActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ContributesAndroidInjector
    abstract PlantCategoryActivity plantCategoryActivity();

    @ContributesAndroidInjector
    abstract CameraActivity cameraActivity();

    @ContributesAndroidInjector
    abstract PlantAnalysisActivity plantAnalysisActivity();

    @ContributesAndroidInjector
    abstract AnalysisResultActivity analysisResultActivity();
}

