package com.testproject.kaera.ringtestapp.di.modules;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.techery.janet.CommandActionService;
import io.techery.janet.HttpActionService;
import io.techery.janet.Janet;
import io.techery.janet.gson.GsonConverter;
import io.techery.janet.http.HttpClient;

@Module
public class JanetModule {

    @Provides
    @Singleton Janet provideJanet(HttpClient client, Gson gson) {
        return new Janet.Builder()
                .addService(new HttpActionService("https://www.reddit.com/", client, new GsonConverter(gson)))
                .addService(new CommandActionService())
                .build();
    }
}