package com.testproject.kaera.ringtestapp.di.components;

import com.testproject.kaera.ringtestapp.service.command.GetTopSubredditCommand;

import io.techery.janet.ActionPipe;

public interface PipeComponent {

    ActionPipe<GetTopSubredditCommand> provideAuthenticateCommand();

    void inject(GetTopSubredditCommand getTopSubredditCommand);
}