package com.testproject.kaera.ringtestapp

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.testproject.kaera.ringtestapp.di.components.AppComponent
import com.testproject.kaera.ringtestapp.di.components.DaggerAppComponent
import com.testproject.kaera.ringtestapp.di.modules.AppModule

class RingApplication : Application() {

    val refWatcher: RefWatcher by lazy { LeakCanary.install(this) }

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}