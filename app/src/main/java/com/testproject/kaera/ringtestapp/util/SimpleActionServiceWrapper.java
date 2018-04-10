package com.testproject.kaera.ringtestapp.util;

import io.techery.janet.ActionHolder;
import io.techery.janet.ActionService;
import io.techery.janet.ActionServiceWrapper;
import io.techery.janet.JanetException;

class SimpleActionServiceWrapper extends ActionServiceWrapper {
    public SimpleActionServiceWrapper(ActionService actionService) {
        super(actionService);
    }

    @Override protected <A> boolean onInterceptSend(ActionHolder<A> holder) throws JanetException {
        return false;
    }

    @Override protected <A> void onInterceptCancel(ActionHolder<A> holder) {

    }

    @Override protected <A> void onInterceptStart(ActionHolder<A> holder) {

    }

    @Override protected <A> void onInterceptProgress(ActionHolder<A> holder, int progress) {

    }

    @Override protected <A> void onInterceptSuccess(ActionHolder<A> holder) {

    }

    @Override protected <A> boolean onInterceptFail(ActionHolder<A> holder, JanetException e) {
        return false;
    }
}
