package com.testproject.kaera.ringtestapp.di.modules;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.techery.janet.ActionService;
import io.techery.janet.CommandActionService;
import io.techery.janet.HttpActionService;
import io.techery.janet.Janet;
import io.techery.janet.gson.GsonConverter;
import io.techery.janet.http.HttpClient;

@Module
public class JanetModule {

    @Provides
    @Singleton
    CommandActionService provideCommandService() {
        return new CommandActionService();
    }

    @Provides()
    @Singleton
    HttpActionService provideHttpService(HttpClient client, Gson gson) {
        //TODO:: Add base URL
        return new HttpActionService("base URL", client, new GsonConverter(gson));
    }

    @Provides
    @Singleton
    Janet provideJanet(ActionService service) {
        return new Janet.Builder().addService(service).build();
    }
}