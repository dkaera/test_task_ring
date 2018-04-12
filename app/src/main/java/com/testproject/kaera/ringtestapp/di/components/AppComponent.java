package com.testproject.kaera.ringtestapp.di.components;

import com.testproject.kaera.ringtestapp.controllers.HomeController;
import com.testproject.kaera.ringtestapp.controllers.LoginController;
import com.testproject.kaera.ringtestapp.di.modules.AppModule;
import com.testproject.kaera.ringtestapp.service.command.AuthenticateCommand;
import com.testproject.kaera.ringtestapp.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent extends PipeComponent {

    void inject(MainActivity object);

    void inject(HomeController object);

    void inject(AuthenticateCommand authenticateCommand);

    void inject(LoginController loginController);
}