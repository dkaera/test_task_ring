package com.testproject.kaera.ringtestapp.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.techery.janet.CommandActionService
import io.techery.janet.HttpActionService
import io.techery.janet.Janet
import io.techery.janet.gson.GsonConverter
import io.techery.janet.http.HttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Module
class JanetModule @Inject constructor() {

    @Provides
    @Singleton
    fun provideJanet(client: HttpClient, gson: Gson): Janet {
        return Janet.Builder()
                .addService(HttpActionService("https://www.reddit.com/", client, GsonConverter(gson)))
                .addService(CommandActionService())
                .build()
    }
}