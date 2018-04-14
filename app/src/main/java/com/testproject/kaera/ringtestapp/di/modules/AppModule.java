package com.testproject.kaera.ringtestapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.service.cache.StaticCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class, JanetModule.class, PipeModule.class})
public class AppModule {

    private RingApplication application;

    public AppModule(@NonNull RingApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton RingApplication providesRingApplication() {
        return application;
    }

    @Provides
    @Singleton Context providesApplicationContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton StaticCache provideStaticCache() {
        return new StaticCache();
    }
}