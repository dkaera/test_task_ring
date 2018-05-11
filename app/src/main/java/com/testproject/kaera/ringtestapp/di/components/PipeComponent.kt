package com.testproject.kaera.ringtestapp.di.components

import com.testproject.kaera.ringtestapp.service.command.GetTopSubredditCommand
import com.testproject.kaera.ringtestapp.service.command.SaveImageCommand
import io.techery.janet.ActionPipe

interface PipeComponent {

     fun provideGetTopSubredditPipe(): ActionPipe<GetTopSubredditCommand>

     fun provideSaveImagePipe(): ActionPipe<SaveImageCommand>

     fun inject(command: GetTopSubredditCommand)

     fun inject(command: SaveImageCommand)
}