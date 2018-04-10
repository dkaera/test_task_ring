package com.testproject.kaera.ringtestapp.controllers;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.controllers.base.BaseController;

public abstract class InjectorController extends BaseController {

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        RingApplication.getComponent().inject(this);
    }
}
