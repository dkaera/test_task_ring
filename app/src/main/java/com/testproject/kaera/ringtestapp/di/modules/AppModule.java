package com.testproject.kaera.ringtestapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.testproject.kaera.ringtestapp.RingApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private RingApplication application;

    public AppModule(@NonNull RingApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    RingApplication providesRingApplication() {
        return application;
    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return application.getApplicationContext();
    }
}