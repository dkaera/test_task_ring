package com.testproject.kaera.ringtestapp.di.modules

import com.testproject.kaera.ringtestapp.service.cache.StaticCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApiModule::class, JanetModule::class, PipeModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideStaticCache() = StaticCache()
}