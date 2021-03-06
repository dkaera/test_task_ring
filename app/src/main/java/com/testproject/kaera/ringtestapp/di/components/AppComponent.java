package com.testproject.kaera.ringtestapp.di.components;

import com.testproject.kaera.ringtestapp.controllers.GalleryController;
import com.testproject.kaera.ringtestapp.controllers.TopListController;
import com.testproject.kaera.ringtestapp.di.modules.AppModule;
import com.testproject.kaera.ringtestapp.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent extends PipeComponent {

    void inject(MainActivity object);

    void inject(TopListController topListController);

    void inject(GalleryController galleryController);
}