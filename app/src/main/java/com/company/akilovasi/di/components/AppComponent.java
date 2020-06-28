package com.company.akilovasi.di.components;

import android.app.Application;

import com.company.akilovasi.AkilOvasiApp;
import com.company.akilovasi.di.modules.ActivityBuilderModule;
import com.company.akilovasi.di.modules.AppModule;
import com.company.akilovasi.di.modules.AppPreference;
import com.company.akilovasi.di.modules.FragmentBuilderModule;
import com.company.akilovasi.di.modules.LocalModule;
import com.company.akilovasi.di.modules.NetworkModule;
import com.company.akilovasi.di.modules.ServiceModule;
import com.company.akilovasi.di.modules.ViewModelModule;
import com.company.akilovasi.di.modules.banner.BannerModule;
import com.company.akilovasi.di.modules.blog.BlogModule;
import com.company.akilovasi.di.modules.notification.NotificationModule;
import com.company.akilovasi.di.modules.plant.PlantHistoryModule;
import com.company.akilovasi.di.modules.plant.PlantModule;
import com.company.akilovasi.di.modules.plantType.PlantTypeModule;
import com.company.akilovasi.di.modules.supportTicket.SupportTicketModule;
import com.company.akilovasi.di.modules.user.UserModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AppModule.class,
        AppPreference.class,
        AndroidSupportInjectionModule.class,
        ActivityBuilderModule.class,
        FragmentBuilderModule.class,
        NetworkModule.class,
        UserModule.class,
        BannerModule.class,
        PlantTypeModule.class,
        ViewModelModule.class,
        LocalModule.class,
        PlantModule.class,
        PlantHistoryModule.class,
        NotificationModule.class,
        SupportTicketModule.class,
        BlogModule.class,
        ServiceModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(AkilOvasiApp aaApp);
}
