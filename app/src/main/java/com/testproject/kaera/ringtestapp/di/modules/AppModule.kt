package com.testproject.kaera.ringtestapp.di.modules

import com.testproject.kaera.ringtestapp.service.cache.StaticCache
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class AppModule @Inject constructor() {

    @Singleton
    @Provides
    fun provideStaticCache() = StaticCache()
}