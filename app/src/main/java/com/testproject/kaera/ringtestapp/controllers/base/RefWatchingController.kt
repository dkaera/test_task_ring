package com.testproject.kaera.ringtestapp.controllers.base

import android.os.Bundle
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.testproject.kaera.ringtestapp.util.getRefWatcher

abstract class RefWatchingController(bundle: Bundle?) : Controller(bundle) {

    var hasExited: Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        if (hasExited) applicationContext?.getRefWatcher()?.watch(this)
    }

    override fun onChangeEnded(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeEnded(changeHandler, changeType)
        hasExited = !changeType.isEnter
        if (isDestroyed) applicationContext?.getRefWatcher()?.watch(this)
    }
}