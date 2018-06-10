package com.testproject.kaera.ringtestapp.controllers.base

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.View
import com.testproject.kaera.ringtestapp.service.util.FinallyActionStateSubscriber
import com.testproject.kaera.ringtestapp.ui.ActionBarProvider
import com.trello.rxlifecycle.android.RxLifecycleAndroid
import io.techery.janet.ActionState
import io.techery.janet.ReadActionPipe
import rx.Observable
import rx.android.schedulers.AndroidSchedulers

abstract class BaseController(bundle: Bundle?) : RefWatchingController(bundle) {

    // Note: This is just a quick demo of how an ActionBar *can* be accessed, not necessarily how it *should*
    // be accessed. In a production app, this would use Dagger instead.
    private fun getActionBar(): ActionBar? {
        val actionBarProvider = activity as ActionBarProvider?
        return actionBarProvider?.getSupportActionBar()
    }

    override fun onAttach(view: View) {
        setTitle()
        super.onAttach(view)
    }

    private fun setTitle() {
        var parentController = parentController
        while (parentController != null) {
            if (parentController is BaseController && parentController.getTitle() != null) {
                return
            }
            parentController = parentController.parentController
        }

        val title = getTitle()
        val actionBar = getActionBar()
        if (title != null && actionBar != null) {
            actionBar.title = title
        }
    }

    private fun getTitle(): String? {
        return null
    }

    fun <T> bind(source: Observable<T>): Observable<T> {
        return source
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleAndroid.bindView(view!!))
    }

    fun <A> bindPipe(pipe: ReadActionPipe<A>): FinallyActionStateSubscriber<A> {
        val subscriber = FinallyActionStateSubscriber<A>()
        bind<ActionState<A>>(pipe.observe())
                .subscribe(subscriber)
        return subscriber
    }
}