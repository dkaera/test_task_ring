package com.testproject.kaera.ringtestapp.di.components

import com.testproject.kaera.ringtestapp.controllers.GalleryController
import com.testproject.kaera.ringtestapp.controllers.TopListController
import com.testproject.kaera.ringtestapp.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent : PipeComponent {

    fun inject(activity: MainActivity)

    fun inject(controller: TopListController)

    fun inject(controller: GalleryController)
}