package com.feerlaroc.mqasho;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Defines app-wide singletons
 */
@Module(addsTo = ApplicationModule.class, library = true)
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    public Activity providesActivity() {
        return activity;
    }

}
