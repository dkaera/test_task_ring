package com.testproject.kaera.ringtestapp.controllers;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandlerCompat;
import com.testproject.kaera.ringtestapp.R;
import com.testproject.kaera.ringtestapp.changehandler.FabToDialogTransitionChangeHandler;
import com.testproject.kaera.ringtestapp.controllers.base.BaseController;

import butterknife.OnClick;

public class LoginController extends BaseController {

    public LoginController() {
        super();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_login, container, false);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
    }

    @OnClick(R.id.action_button)
    void onActionButtonClick() {
        getRouter()
                .pushController(RouterTransaction.with(new AuthDialogController())
                        .pushChangeHandler(new FadeChangeHandler(false))
                        .popChangeHandler(new FadeChangeHandler()));
    }
}
