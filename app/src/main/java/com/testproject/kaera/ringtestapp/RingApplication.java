package com.testproject.kaera.ringtestapp;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.testproject.kaera.ringtestapp.di.components.AppComponent;
import com.testproject.kaera.ringtestapp.di.components.DaggerAppComponent;
import com.testproject.kaera.ringtestapp.di.modules.AppModule;


public class RingApplication extends Application {

    public static RefWatcher refWatcher;

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
