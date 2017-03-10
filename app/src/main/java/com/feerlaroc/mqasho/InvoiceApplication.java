package com.feerlaroc.mqasho;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.feerlaroc.mqasho.common.dagger.ObjectGraphService;

import dagger.ObjectGraph;
import mortar.MortarScope;
import timber.log.Timber;

public class InvoiceApplication extends Application {

    static InvoiceApplication singleton;

    private MortarScope rootScope;

    @Override public Object getSystemService(String name) {
        if (rootScope == null) {
            rootScope = MortarScope.buildRootScope()
                    .withService(
                            ObjectGraphService.SERVICE_NAME,
                            ObjectGraph.create(new ApplicationModule(this)))
                    .build("Root");
        }

        if (rootScope.hasService(name)) return rootScope.getService(name);

        return super.getSystemService(name);
    }

    public static InvoiceApplication getInstance(){

        return singleton;
    }

    @Override
    public void onCreate()
    {

        super.onCreate();

        singleton = this;

        if (BuildConfig.DEBUG)
        {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.i("Creating our Application");
    }

    public static boolean hasNetwork ()
    {
        return singleton.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public Context getBaseContext() {
        return super.getBaseContext();
    }

}

