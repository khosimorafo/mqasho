package com.feerlaroc.mqasho.schema;

import android.view.View;

import mortar.ViewPresenter;

/**
 * Created by root on 2016/03/29.
 */
public abstract class EntityViewScreen<V extends View> extends ViewPresenter<V> {

    protected abstract void updateViewValues();

}
