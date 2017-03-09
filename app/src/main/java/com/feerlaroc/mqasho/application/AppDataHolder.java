package com.feerlaroc.mqasho.application;


import android.app.FragmentManager;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class AppDataHolder<T> {

    Context mActivityContext;
    WindowManager mWindowManager;
    FragmentManager mFragmentManager;


    private static final AppDataHolder holder = new AppDataHolder();
    public static AppDataHolder getInstance() {return holder;}

    public int getScreenWidth(){

        // Use Display metrics to get Screen Dimensions
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics.widthPixels;

    }

    public Context getActivityContext() {
        return mActivityContext;
    }

    public void setActivityContext(Context mActivityContext) {
        this.mActivityContext = mActivityContext;
    }

    public void setFragmentManager(FragmentManager manager){

        mFragmentManager = manager;
    }

    public FragmentManager getFragmentManager(){

        return mFragmentManager;
    }

    public void setWindowManager(WindowManager windowManager) {
        mWindowManager = windowManager;
    }

    public WindowManager getWindowManager(){
        return mWindowManager;
    }

}
