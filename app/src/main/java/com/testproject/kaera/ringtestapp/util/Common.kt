package com.testproject.kaera.ringtestapp.util

import android.app.Activity
import android.content.Context
import android.os.Looper
import com.squareup.leakcanary.RefWatcher
import com.testproject.kaera.ringtestapp.RingApplication
import com.testproject.kaera.ringtestapp.di.components.AppComponent

/**
 * Created by Dmitriy Puzak on 5/11/18.
 */
fun Activity.isMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper()

fun Context.getComponent() : AppComponent {
    val ringApplication = this.applicationContext as RingApplication
    return ringApplication.component
}

fun Context.getRefWatcher() : RefWatcher {
    val ringApplication = this.applicationContext as RingApplication
    return ringApplication.refWatcher
}