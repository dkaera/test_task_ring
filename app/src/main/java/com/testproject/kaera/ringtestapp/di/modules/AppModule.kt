package com.testproject.kaera.ringtestapp.di.modules

import android.content.Context
import com.testproject.kaera.ringtestapp.service.cache.StaticCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApiModule::class, JanetModule::class, PipeModule::class])
class AppModule(private val applicationContext : Context) {

    @Singleton
    @Provides
    fun provideStaticCache() = StaticCache()

    @Singleton
    @Provides
    fun provideApplicatinContext() : Context {
        return applicationContext.applicationContext
    }
}