package com.testproject.kaera.ringtestapp.service.util

import android.content.Context
import com.testproject.kaera.ringtestapp.util.getComponent
import io.techery.janet.*

/**
 * Created by Dmitriy Puzak on 5/11/18.
 */
class InjectableCommandsActionService(var context: Context, actionService: ActionService) : ActionServiceWrapper(actionService) {

    val injector: CommandInjector

    init {
        injector = CommandInjector(context.getComponent())
    }

    override fun <A : Any?> onInterceptProgress(holder: ActionHolder<A>?, progress: Int) {
    }

    override fun <A : Any?> onInterceptStart(holder: ActionHolder<A>?) {
    }

    override fun <A : Any?> onInterceptSend(holder: ActionHolder<A>?): Boolean {
        injector.inject(holder?.action() as Command<A>)
        return false
    }

    override fun <A : Any?> onInterceptCancel(holder: ActionHolder<A>?) {
    }

    override fun <A : Any?> onInterceptSuccess(holder: ActionHolder<A>?) {
    }

    override fun <A : Any?> onInterceptFail(holder: ActionHolder<A>?, e: JanetException?) = false
}