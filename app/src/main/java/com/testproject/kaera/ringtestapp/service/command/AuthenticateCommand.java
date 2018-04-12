package com.testproject.kaera.ringtestapp.service.command;

import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.service.api.AuthenticateAction;

import javax.inject.Inject;

import io.techery.janet.ActionPipe;
import io.techery.janet.Command;
import io.techery.janet.command.annotations.CommandAction;

@CommandAction
public class AuthenticateCommand extends Command<String> {

    @Inject
    ActionPipe<AuthenticateAction> authenticateActionPipe;

    public AuthenticateCommand() {
        RingApplication.getComponent().inject(this);
    }

    @Override
    protected void run(CommandCallback callback) throws Throwable {
        authenticateActionPipe.createObservableResult(new AuthenticateAction())
                .map(AuthenticateAction::getResponse)
                .subscribe(callback::onSuccess, callback::onFail);
    }
}
