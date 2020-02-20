package com.company.akilovasi.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.company.akilovasi.di.ViewModelKey;
import com.company.akilovasi.ui.login.LoginViewModel;
import com.company.akilovasi.ui.main.MainViewModel;
import com.company.akilovasi.ui.plant.PlantCategoryActivity;
import com.company.akilovasi.ui.plant.PlantCategoryViewModel;
import com.company.akilovasi.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindsloginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindsMainViewModel(MainViewModel mainViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(PlantCategoryViewModel.class)
    abstract ViewModel bindsPlantCategoryViewModel(PlantCategoryViewModel plantCategoryViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory userViewModelFactory);
}