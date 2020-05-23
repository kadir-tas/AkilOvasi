package com.company.akilovasi.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.company.akilovasi.di.ViewModelKey;
import com.company.akilovasi.ui.analysisresult.AnalysisResultViewModel;
import com.company.akilovasi.ui.common.fullscreen.PlantFullImageViewModel;
import com.company.akilovasi.ui.login.LoginViewModel;
import com.company.akilovasi.ui.login.fragments.RegisterViewModel;
import com.company.akilovasi.ui.main.MainViewModel;
import com.company.akilovasi.ui.main.fragments.history.PlantHistoryFragmentViewModel;
import com.company.akilovasi.ui.main.fragments.profile.ProfileFragmentViewModel;
import com.company.akilovasi.ui.notification.NotificationViewModel;
import com.company.akilovasi.ui.plant.PlantCategoryActivityViewModel;
import com.company.akilovasi.ui.plant.fragments.addplant.PlantAddFragmentViewModel;
import com.company.akilovasi.ui.plant.fragments.plantcategory.PlantCategoryFragmentViewModel;
import com.company.akilovasi.ui.plantanalysis.PlantAnalysisActivity;
import com.company.akilovasi.ui.plantanalysis.PlantAnalysisViewModel;
import com.company.akilovasi.viewmodel.ViewModelFactory;

import javax.inject.Inject;

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
    @IntoMap
    @ViewModelKey(PlantAnalysisViewModel.class)
    abstract ViewModel bindsPlantAnalysisViewModel(PlantAnalysisViewModel plantAnalysisViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    abstract ViewModel bindRegisterViewModel(RegisterViewModel registerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PlantFullImageViewModel.class)
    abstract ViewModel bindPlantFullImageViewModel(PlantFullImageViewModel plantFullImageViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileFragmentViewModel.class)
    abstract ViewModel bindProfileFragmentViewModel(ProfileFragmentViewModel profileFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel.class)
    abstract ViewModel bindNotificationViewModel(NotificationViewModel notificationViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AnalysisResultViewModel.class)
    abstract ViewModel bindAnalysisResultViewModel(AnalysisResultViewModel analysisResultViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory userViewModelFactory);
}