package com.testproject.kaera.ringtestapp.controllers.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.bluelinelabs.conductor.Controller;
import com.testproject.kaera.ringtestapp.service.util.FinallyActionStateSubscriber;
import com.testproject.kaera.ringtestapp.ui.ActionBarProvider;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import io.techery.janet.ReadActionPipe;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


public abstract class BaseController extends RefWatchingController {

    public BaseController() {
        super();
    }

    public BaseController(Bundle args) {
        super(args);
    }

    // Note: This is just a quick demo of how an ActionBar *can* be accessed, not necessarily how it *should*
    // be accessed. In a production app, this would use Dagger instead.
    protected ActionBar getActionBar() {
        ActionBarProvider actionBarProvider = ((ActionBarProvider) getActivity());
        return actionBarProvider != null ? actionBarProvider.getSupportActionBar() : null;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        setTitle();
        super.onAttach(view);
    }

    protected void setTitle() {
        Controller parentController = getParentController();
        while (parentController != null) {
            if (parentController instanceof BaseController && ((BaseController) parentController).getTitle() != null) {
                return;
            }
            parentController = parentController.getParentController();
        }

        String title = getTitle();
        ActionBar actionBar = getActionBar();
        if (title != null && actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected String getTitle() {
        return null;
    }

    public <T> Observable<T> bind(Observable<T> source) {
        return source
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleAndroid.bindView(getView()));
    }

    public <A> FinallyActionStateSubscriber<A> bindPipe(ReadActionPipe<A> pipe) {
        FinallyActionStateSubscriber<A> subscriber = new FinallyActionStateSubscriber<>();
        bind(pipe.observe())
                .subscribe(subscriber);
        return subscriber;
    }

    public void showProgressBar() {
        ActionBarProvider actionBarProvider = ((ActionBarProvider) getActivity());
        if (actionBarProvider != null) actionBarProvider.showProgress();
    }
    public void hideProgressBar() {
        ActionBarProvider actionBarProvider = ((ActionBarProvider) getActivity());
        if (actionBarProvider != null) actionBarProvider.hideProgress();
    }
}
