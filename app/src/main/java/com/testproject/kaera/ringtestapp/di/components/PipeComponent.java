package com.testproject.kaera.ringtestapp.di.components;

import com.testproject.kaera.ringtestapp.service.command.AuthenticateCommand;

import io.techery.janet.ActionPipe;

public interface PipeComponent {

    ActionPipe<AuthenticateCommand> provideAuthenticateCommand();
}