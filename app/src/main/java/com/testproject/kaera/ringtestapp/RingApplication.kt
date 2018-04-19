package com.testproject.kaera.ringtestapp

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

class RingApplication : Application() {

    lateinit var refWatcher: RefWatcher

    override fun onCreate() {
        super.onCreate()
        refWatcher = LeakCanary.install(this)
    }
}