package com.company.akilovasi.di.components;

import android.app.Application;

import com.company.akilovasi.AkilOvasiApp;
import com.company.akilovasi.di.modules.ActivityBuilderModule;
import com.company.akilovasi.di.modules.AppModule;
import com.company.akilovasi.di.modules.NetworkModule;
import com.company.akilovasi.di.modules.ViewModelModule;
import com.company.akilovasi.di.modules.user.LoginModule;
import com.company.akilovasi.di.modules.user.UserModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AppModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBuilderModule.class,
        NetworkModule.class,
        UserModule.class,
        LoginModule.class,
        ViewModelModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(AkilOvasiApp aaApp);
}
