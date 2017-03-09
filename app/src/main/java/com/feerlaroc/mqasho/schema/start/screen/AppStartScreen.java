package com.feerlaroc.mqasho.schema.start.screen;

import android.os.Bundle;

import com.feerlaroc.mqasho.ActivityModule;
import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.flow.Layout;
import com.feerlaroc.mqasho.common.mortarscreen.WithModule;
import com.feerlaroc.mqasho.schema.start.DataInitialization;
import com.feerlaroc.mqasho.schema.start.view.AppStartView;
import com.feerlaroc.mqasho.schema.tenant.screen.CustomerGridScreen2;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Flow;
import flow.path.Path;
import mortar.ViewPresenter;

@Layout(R.layout.layout_start_screen)
@WithModule(AppStartScreen.Module.class)
public class AppStartScreen extends Path {

    private static final String TAG = "AppStartScreen";

    @dagger.Module(injects = AppStartView.class, addsTo = ActivityModule.class)
    public class Module {
    }

    @Singleton
    public static class Presenter extends ViewPresenter<AppStartView>
        implements DataInitialization.AppDataInitializationListener {

        @Inject
        public Presenter() {
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {

            super.onLoad(savedInstanceState);

            navigate();
        }


        @Override
        public void onDataInitializationComplete() {
            //SUCCESS
            getView().getNewtonCradleLoading().stop();
            navigate();
        }

        public void navigate(){ Flow.get(getView()).set(new CustomerGridScreen2()); }

    }
}
