package com.feerlaroc.mqasho.schema;

import android.view.View;

import mortar.ViewPresenter;

public abstract class EntityEditScreen<V extends View> extends ViewPresenter<V> {

    public abstract void onSaveItem();
    protected abstract void updateViewValues();

    public enum EditMode { NEW, UPDATE }

}