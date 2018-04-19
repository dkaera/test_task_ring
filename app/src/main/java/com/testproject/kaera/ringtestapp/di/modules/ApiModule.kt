package com.testproject.kaera.ringtestapp.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.testproject.kaera.ringtestapp.enteties.APIRedditItem
import com.testproject.kaera.ringtestapp.service.adapter.APIRedditItemAdapter
import com.testproject.kaera.ringtestapp.util.curl.CurlInterceptor
import dagger.Module
import dagger.Provides
import io.techery.janet.http.HttpClient
import io.techery.janet.okhttp3.OkClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ApiModule @Inject constructor() {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val apiRedditItemType = object : TypeToken<List<APIRedditItem>>() {

        }.type
        return GsonBuilder().serializeNulls()
                .registerTypeAdapter(apiRedditItemType, APIRedditItemAdapter())
                .disableHtmlEscaping().create()
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
                .connectTimeout(20L, TimeUnit.SECONDS)
                .readTimeout(20L, TimeUnit.SECONDS)
                .addInterceptor(CurlInterceptor())
                .addInterceptor(logInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(okHttpClient: OkHttpClient): HttpClient {
        return OkClient(okHttpClient)
    }
}