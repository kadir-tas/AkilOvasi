package com.company.akilovasi.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.company.akilovasi.di.DefaultPrefs;
import com.company.akilovasi.di.SecretPrefs;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.company.akilovasi.util.AppConstants.SECRET_PREFERENCE_FILE_NAME;

@Module
public class AppPreference {

    @Provides
    @Singleton
    @DefaultPrefs
    SharedPreferences provideDefaultSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    @SecretPrefs
    public SharedPreferences provideSecretSharedPreferences(Application application) {
        return application.getSharedPreferences(SECRET_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

}
