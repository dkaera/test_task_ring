package com.testproject.kaera.ringtestapp.service.command;

import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.di.modules.JanetModule;
import com.testproject.kaera.ringtestapp.enteties.APIToken;
import com.testproject.kaera.ringtestapp.service.api.AuthenticateAction;
import com.testproject.kaera.ringtestapp.service.util.RxPreferences;
import com.testproject.kaera.ringtestapp.util.Constants;

import javax.inject.Inject;

import io.techery.janet.ActionPipe;
import io.techery.janet.Command;
import io.techery.janet.Janet;
import io.techery.janet.command.annotations.CommandAction;
import rx.schedulers.Schedulers;

@CommandAction
public class AuthenticateCommand extends Command<APIToken> {

    @Inject Janet janet;
    @Inject RxPreferences preferences;

    public AuthenticateCommand() {
        RingApplication.getComponent().inject(this);
    }

    @Override protected void run(CommandCallback callback) throws Throwable {
        String authCode = preferences.getString(Constants.KEY_AUTH_CODE).get();
        janet.createPipe(AuthenticateAction.class, Schedulers.immediate())
                .createObservableResult(new AuthenticateAction(authCode))
                .map(AuthenticateAction::getResponse)
                .subscribe(callback::onSuccess, callback::onFail);
    }
}
