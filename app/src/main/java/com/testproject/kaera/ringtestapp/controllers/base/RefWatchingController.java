package com.testproject.kaera.ringtestapp.controllers.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;

public abstract class RefWatchingController extends ButterKnifeController {

    private boolean hasExited;

    public RefWatchingController() {
        super();
    }

    public RefWatchingController(Bundle args) {
        super(args);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hasExited) RingApplication.refWatcher.watch(this);
    }

    @Override
    protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        super.onChangeEnded(changeHandler, changeType);
        hasExited = !changeType.isEnter;
        if (isDestroyed()) RingApplication.refWatcher.watch(this);
    }
}