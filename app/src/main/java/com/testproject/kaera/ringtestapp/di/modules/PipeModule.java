package com.testproject.kaera.ringtestapp.di.modules;

import com.testproject.kaera.ringtestapp.service.command.GetTopSubredditCommand;
import com.testproject.kaera.ringtestapp.service.command.SaveImageCommand;

import dagger.Module;
import dagger.Provides;
import io.techery.janet.ActionPipe;
import io.techery.janet.Janet;
import rx.schedulers.Schedulers;

@Module
public class PipeModule {

    @Provides ActionPipe<GetTopSubredditCommand> provideAuthenticateCommand(Janet janet) {
        return janet.createPipe(GetTopSubredditCommand.class, Schedulers.io());
    }

    @Provides ActionPipe<SaveImageCommand> provideSaveImageCommand(Janet janet) {
        return janet.createPipe(SaveImageCommand.class, Schedulers.io());
    }
}
