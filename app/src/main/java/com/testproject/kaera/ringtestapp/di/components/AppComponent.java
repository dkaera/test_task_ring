package com.testproject.kaera.ringtestapp.di.components;

import com.testproject.kaera.ringtestapp.controllers.HomeController;
import com.testproject.kaera.ringtestapp.controllers.InjectorController;
import com.testproject.kaera.ringtestapp.di.modules.AppModule;
import com.testproject.kaera.ringtestapp.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(MainActivity object);

    void inject(HomeController object);

    void inject(InjectorController controller);
}