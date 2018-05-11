package com.testproject.kaera.ringtestapp.service.util;

import com.testproject.kaera.ringtestapp.di.components.PipeComponent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.techery.janet.Command;

/**
 * Created by Dmitriy Puzak on 5/11/18.
 */
public class CommandInjector {

    private static final Map<Class<? extends Command>, Method> cache = new HashMap<>();
    private PipeComponent commandsComponent;

    public CommandInjector(PipeComponent commandsComponent) {
        this.commandsComponent = commandsComponent;
    }

    public void inject(Command command) {
        Class<? extends Command> commandClass = command.getClass();
        try {
            Method injectableMethod = findInjectableMethod(commandClass);
            injectableMethod.invoke(commandsComponent, command);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException | NullPointerException e) {
            String detailMessage = "No graph method found to inject " + commandClass.getSimpleName() + ". Check " + PipeComponent.class.getName() + " component";
            NullPointerException exception = new NullPointerException(detailMessage);
            exception.setStackTrace(e.getStackTrace());
            throw exception;
        }
    }

    private Method findInjectableMethod(Class<? extends Command> commandClass) throws NoSuchMethodException {
        Method cachedMethod = cache.get(commandClass);
        if (cachedMethod != null) {
            return commandsComponent.getClass().getDeclaredMethod(cachedMethod.getName(), commandClass);
        }
        // Find proper injectable method of component to inject presenter instance
        for (Method m : commandsComponent.getClass().getDeclaredMethods()) {
            for (Class pClass : m.getParameterTypes()) {
                if (m.getReturnType().equals(Void.TYPE) && pClass.equals(commandClass)) {
                    cache.put(commandClass, m);
                    return m;
                }
            }
        }
        return null;
    }
}