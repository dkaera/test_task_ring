package com.testproject.kaera.ringtestapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.service.cache.StaticCache;
import com.testproject.kaera.ringtestapp.service.util.RxPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;
import static com.testproject.kaera.ringtestapp.service.util.RxPreferences.RING_PREFERENCES;

@Module(includes = {ApiModule.class, JanetModule.class, PipeModule.class})
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

    @Provides
    @Singleton
    RxPreferences providePreferences(Context context, Gson gson) {
        return new RxPreferences(context.getSharedPreferences(RING_PREFERENCES, MODE_PRIVATE), gson);
    }

    @Provides
    @Singleton
    StaticCache provideStaticCache() {
        return new StaticCache();
    }
}