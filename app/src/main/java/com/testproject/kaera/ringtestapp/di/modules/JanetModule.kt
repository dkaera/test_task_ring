package com.testproject.kaera.ringtestapp.di.modules

import android.content.Context
import com.google.gson.Gson
import com.testproject.kaera.ringtestapp.service.util.InjectableCommandsActionService
import dagger.Module
import dagger.Provides
import io.techery.janet.CommandActionService
import io.techery.janet.HttpActionService
import io.techery.janet.Janet
import io.techery.janet.gson.GsonConverter
import io.techery.janet.http.HttpClient
import javax.inject.Singleton

@Module
class JanetModule {

    @Provides
    @Singleton
    fun provideJanet(context : Context, client: HttpClient, gson: Gson): Janet {
        return Janet.Builder()
                .addService(HttpActionService("https://www.reddit.com/", client, GsonConverter(gson)))
                .addService(InjectableCommandsActionService(context, CommandActionService()))
                .build()
    }
}