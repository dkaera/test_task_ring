package com.testproject.kaera.ringtestapp.di.modules;

import com.testproject.kaera.ringtestapp.service.command.AuthenticateCommand;

import dagger.Module;
import dagger.Provides;
import io.techery.janet.ActionPipe;
import io.techery.janet.Janet;
import rx.schedulers.Schedulers;

@Module
public class PipeModule {

    @Provides
    ActionPipe<AuthenticateCommand> provideAuthenticateCommand(Janet janet) {
        return janet.createPipe(AuthenticateCommand.class, Schedulers.io());
    }
}
