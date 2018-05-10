package com.testproject.kaera.ringtestapp.di.modules

import com.testproject.kaera.ringtestapp.service.command.GetTopSubredditCommand
import com.testproject.kaera.ringtestapp.service.command.SaveImageCommand
import dagger.Module
import dagger.Provides
import io.techery.janet.ActionPipe
import io.techery.janet.Janet
import rx.schedulers.Schedulers

@Module
class PipeModule {

    @Provides fun provideAuthenticateCommand(janet: Janet): ActionPipe<GetTopSubredditCommand> {
        return janet.createPipe(GetTopSubredditCommand::class.java, Schedulers.io())
    }

    @Provides fun provideSaveImageCommand(janet: Janet): ActionPipe<SaveImageCommand> {
        return janet.createPipe(SaveImageCommand::class.java, Schedulers.io())
    }
}