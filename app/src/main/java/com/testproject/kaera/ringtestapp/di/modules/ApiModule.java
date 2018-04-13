package com.testproject.kaera.ringtestapp.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.testproject.kaera.ringtestapp.enteties.APIRedditItem;
import com.testproject.kaera.ringtestapp.service.adapter.APIRedditItemAdapter;
import com.testproject.kaera.ringtestapp.util.curl.CurlInterceptor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.techery.janet.http.HttpClient;
import io.techery.janet.okhttp3.OkClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class ApiModule {

    @Provides
    @Singleton
    Gson provideGson() {
        Type apiRedditItemType = new TypeToken<List<APIRedditItem>>() {}.getType();
        return new GsonBuilder().serializeNulls()
                .registerTypeAdapter(apiRedditItemType, new APIRedditItemAdapter())
                .disableHtmlEscaping().create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttp() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20L, TimeUnit.SECONDS)
                .readTimeout(20L, TimeUnit.SECONDS)
                .addInterceptor(new CurlInterceptor())
                .addInterceptor(logInterceptor);
        return builder.build();
    }

    @Provides
    @Singleton
    HttpClient provideHttpClient(OkHttpClient okHttpClient) {
        return new OkClient(okHttpClient);
    }
}