package com.testproject.kaera.ringtestapp.di.modules;

import android.app.Application;
import android.content.Context;

import com.grasshopper.dialer.service.util.ActionDataProvider;
import com.grasshopper.dialer.service.util.CommandServiceWrapper;
import com.grasshopper.dialer.service.util.TimberServiceWrapper;

import java.util.Set;

import dagger.Module;
import dagger.Provides;
import io.techery.janet.ActionService;
import io.techery.janet.CommandActionService;
import io.techery.janet.Janet;
import io.techery.presenta.di.ApplicationScope;

@Module
public class JanetModule {

    @Provides
    public ActionService provideCommandService(Context context) {
        return new CommandServiceWrapper(new CommandActionService(), context);
    }

    @Provides
    Janet provideJanet(Set<ActionService> services, Application application) {
        Janet.Builder builder = new Janet.Builder();

        for (ActionService service : services) {
            builder.addService((new ActionDataProvider(new TimberServiceWrapper(service), application)));
        }
        return builder.build();
    }
}
