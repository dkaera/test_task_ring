package com.testproject.kaera.ringtestapp.service.util

import io.techery.janet.ActionState
import io.techery.janet.ActionState.Status.*
import rx.Subscriber
import rx.exceptions.OnErrorNotImplementedException
import rx.functions.Action0
import rx.functions.Action1
import rx.functions.Action2

/**
 * Created by Dmitriy Puzak on 6/13/18.
 */
class FinallyActionStateSubscriber<A> : Subscriber<ActionState<A>>() {

    private lateinit var onFinish: Action0
    private lateinit var onSuccess: Action1<A>
    private lateinit var onFail: Action2<A, Throwable>
    private lateinit var onStart: Action1<A>
    private lateinit var onProgress: Action2<A, Int>
    private lateinit var beforeEach: Action1<ActionState<A>>
    private lateinit var afterEach: Action1<ActionState<A>>

    fun onSuccess(onSuccess: Action1<A>): FinallyActionStateSubscriber<A> {
        this.onSuccess = onSuccess
        return this
    }

    fun onFail(onError: Action2<A, Throwable>): FinallyActionStateSubscriber<A> {
        this.onFail = onError
        return this
    }

    fun onStart(onStart: Action1<A>): FinallyActionStateSubscriber<A> {
        this.onStart = onStart
        return this
    }

    fun onProgress(onProgress: Action2<A, Int>): FinallyActionStateSubscriber<A> {
        this.onProgress = onProgress
        return this
    }

    fun beforeEach(onEach: Action1<ActionState<A>>): FinallyActionStateSubscriber<A> {
        this.beforeEach = onEach
        return this
    }

    fun afterEach(afterEach: Action1<ActionState<A>>): FinallyActionStateSubscriber<A> {
        this.afterEach = afterEach
        return this
    }

    fun onFinish(onFinish: Action0): FinallyActionStateSubscriber<A> {
        this.onFinish = onFinish
        return this
    }

    override fun onNext(state: ActionState<A>) {
        beforeEach?.call(state)
        when (state.status) {
            START -> onStart?.call(state.action)
            PROGRESS -> onProgress?.call(state.action, state.progress)
            SUCCESS -> onSuccess?.call(state.action)
            FAIL -> onFail?.call(state.action, state.exception)
        }
        if (state.status == SUCCESS || state.status == FAIL) {
            onFinish?.call()
        }
        afterEach?.call(state)
    }

    override fun onCompleted() {}

    override fun onError(e: Throwable?) {
        if (e != null)
            e.printStackTrace()
        else
            throw OnErrorNotImplementedException(e)
    }
}