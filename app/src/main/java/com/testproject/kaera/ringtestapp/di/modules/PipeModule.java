package com.testproject.kaera.ringtestapp.di.modules;

import com.testproject.kaera.ringtestapp.service.api.AuthenticateAction;
import com.testproject.kaera.ringtestapp.service.command.AuthenticateCommand;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.techery.janet.ActionPipe;
import io.techery.janet.Janet;
import rx.schedulers.Schedulers;

@Module
public class PipeModule {

    @Provides
    ActionPipe<AuthenticateAction> provideAuthenticateAction(Janet janet) {
        return createPipe(AuthenticateAction.class, janet);
    }

    @Provides
    ActionPipe<AuthenticateCommand> provideAuthenticateCommand(Janet janet) {
        return createPipe(AuthenticateCommand.class, janet);
    }

    private <T> ActionPipe<T> createPipe(Class<T> clazz, Janet janet) {
        ActionPipe pipe = janet.createPipe(clazz, Schedulers.io());
        return pipe;
    }
}
