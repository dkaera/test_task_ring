package com.testproject.kaera.ringtestapp.di.modules;

import dagger.Module;
import dagger.Provides;
import io.techery.janet.ActionService;
import io.techery.janet.CommandActionService;
import io.techery.janet.Janet;

@Module
public class JanetModule {

    @Provides
    public ActionService provideCommandService() {
        return new CommandActionService();
    }

    @Provides
    Janet provideJanet() {
        return new Janet.Builder().build();
    }
}
