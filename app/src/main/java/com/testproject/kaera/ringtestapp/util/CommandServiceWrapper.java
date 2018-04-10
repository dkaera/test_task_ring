package com.testproject.kaera.ringtestapp.util;

import android.content.Context;

import com.grasshopper.dialer.di.CommandsComponent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.techery.janet.ActionHolder;
import io.techery.janet.Command;
import io.techery.janet.CommandActionService;
import io.techery.janet.JanetException;
import io.techery.presenta.mortar.DaggerService;
import timber.log.Timber;

public class CommandServiceWrapper extends SimpleActionServiceWrapper {

    private final CommandInjector injector;

    public CommandServiceWrapper(CommandActionService service, Context context) {
        super(service);
        CommandsComponent component = DaggerService.getDaggerComponent(context);
        this.injector = new CommandInjector(component);
    }

    @Override protected <A> void onInterceptStart(ActionHolder<A> holder) {
        if (holder.action() instanceof CachedCommand) {
            ((CachedCommand) holder.action()).onRestore(null);
        }
    }

    @Override protected <A> boolean onInterceptSend(ActionHolder<A> holder) {
        try {
            injector.inject((Command) holder.action());
        } catch (Throwable throwable) {
//            Timber.e(throwable, "Can't inject to command %s, check if you have inject method declared in %s", holder.action(), CommandsComponent.class.getName());
        }
        return false;
    }

    @Override protected <A> boolean onInterceptFail(ActionHolder<A> holder, JanetException e) {
        if (e != null) {
//            Timber.e(e, "Janet exception");
        }
        return super.onInterceptFail(holder, e);
    }

    private static class CommandInjector {

        private static final Map<Class<? extends Command>, Method> cache = new HashMap<>();
        private Object commandsComponent;

        private CommandInjector(Object commandsComponent) {
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
                String detailMessage = "No graph method found to inject " + commandClass.getSimpleName() + ". Check " + CommandsComponent.class.getName() + " component";
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
                    if (pClass.equals(commandClass)) {
                        cache.put(commandClass, m);
                        return m;
                    }
                }
            }
            return null;
        }
    }
}
