package com.testproject.kaera.ringtestapp.di.components;

import com.testproject.kaera.ringtestapp.service.command.GetTopSubredditCommand;
import com.testproject.kaera.ringtestapp.service.command.SaveImageCommand;

import io.techery.janet.ActionPipe;

public interface PipeComponent {

    ActionPipe<GetTopSubredditCommand> provideAuthenticateCommand();

    ActionPipe<SaveImageCommand> provideSaveImageCommand();

    void inject(GetTopSubredditCommand getTopSubredditCommand);

    void inject(SaveImageCommand saveImageCommand);
}