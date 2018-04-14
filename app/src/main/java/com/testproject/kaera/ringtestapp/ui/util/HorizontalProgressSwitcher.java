package com.testproject.kaera.ringtestapp.ui.util;

import com.testproject.kaera.ringtestapp.controllers.base.BaseController;

import io.techery.janet.ActionState;
import rx.functions.Action1;

import static io.techery.janet.ActionState.Status.FAIL;
import static io.techery.janet.ActionState.Status.START;
import static io.techery.janet.ActionState.Status.SUCCESS;

public class HorizontalProgressSwitcher<A> implements Action1<ActionState<A>> {
    private final BaseController controller;

    public HorizontalProgressSwitcher(BaseController controller) {
        this.controller = controller;
    }

    @Override public void call(ActionState<A> state) {
        if (state.status == FAIL || state.status == SUCCESS) {
            controller.hideProgressBar();
        }
        if (state.status == START){
            controller.showProgressBar();
        }
    }
}
