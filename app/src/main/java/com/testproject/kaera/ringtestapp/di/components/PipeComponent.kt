package com.testproject.kaera.ringtestapp.di.components

import com.testproject.kaera.ringtestapp.service.command.GetTopSubredditCommand
import com.testproject.kaera.ringtestapp.service.command.SaveImageCommand
import io.techery.janet.ActionPipe

interface PipeComponent {

     fun provideAuthenticateCommand(): ActionPipe<GetTopSubredditCommand>

     fun provideSaveImageCommand(): ActionPipe<SaveImageCommand>

     fun inject(command: GetTopSubredditCommand)

     fun inject(command: SaveImageCommand)
}