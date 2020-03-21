package com.company.akilovasi.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.company.akilovasi.di.ViewModelKey;
import com.company.akilovasi.ui.login.LoginViewModel;
import com.company.akilovasi.ui.main.MainViewModel;
import com.company.akilovasi.ui.main.fragments.history.PlantHistoryFragmentViewModel;
import com.company.akilovasi.ui.plant.PlantCategoryActivityViewModel;
import com.company.akilovasi.ui.plant.fragments.addplant.PlantAddFragmentViewModel;
import com.company.akilovasi.ui.plant.fragments.plantcategory.PlantCategoryFragmentViewModel;
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
    @ViewModelKey(PlantCategoryActivityViewModel.class)
    abstract ViewModel bindsPlantCategoryViewModel(PlantCategoryActivityViewModel plantCategoryActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PlantCategoryFragmentViewModel.class)
    abstract ViewModel bindPlantCategoryFragmentViewModel(PlantCategoryFragmentViewModel plantCategoryFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PlantAddFragmentViewModel.class)
    abstract ViewModel bindPlantAddFragmentViewModel(PlantAddFragmentViewModel plantAddFragmentViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(PlantHistoryFragmentViewModel.class)
    abstract ViewModel bindsPlantHistoryFragmentViewModel(PlantHistoryFragmentViewModel plantHistoryFragmentViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory userViewModelFactory);
}