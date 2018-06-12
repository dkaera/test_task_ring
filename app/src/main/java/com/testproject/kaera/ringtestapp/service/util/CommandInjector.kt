package com.testproject.kaera.ringtestapp.service.util

import com.testproject.kaera.ringtestapp.di.components.PipeComponent
import io.techery.janet.Command
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*

/**
 * Created by Dmitriy Puzak on 6/12/18.
 */
class CommandInjector(private val commandsComponent: PipeComponent) {

    private val cache = HashMap<Class<out Command<*>>, Method>()

    fun inject(command: Command<*>) {
        val commandClass = command.javaClass
        try {
            val injectableMethod = findInjectableMethod(commandClass)
            injectableMethod!!.invoke(commandsComponent, command)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            handleThrowableExc(commandClass, e)
        } catch (e: NullPointerException) {
            handleThrowableExc(commandClass, e)
        }
    }

    private fun handleThrowableExc(commandClass: Class<Command<*>>, e: Exception) {
        val detailMessage = "No graph method found to inject " + commandClass.simpleName + ". Check " + PipeComponent::class.java.name + " component"
        val exception = NullPointerException(detailMessage)
        exception.stackTrace = e.stackTrace
        throw exception
    }

    @Throws(NoSuchMethodException::class)
    private fun findInjectableMethod(commandClass: Class<out Command<*>>): Method? {
        val cachedMethod = cache[commandClass]
        if (cachedMethod != null) {
            return commandsComponent.javaClass.getDeclaredMethod(cachedMethod.name, commandClass)
        }
        // Find proper injectable method of component to inject presenter instance
        for (m in commandsComponent.javaClass.declaredMethods) {
            for (pClass in m.parameterTypes) {
                if (m.returnType == Void.TYPE && pClass == commandClass) {
                    cache[commandClass] = m
                    return m
                }
            }
        }
        return null
    }
}