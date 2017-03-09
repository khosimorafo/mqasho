package com.feerlaroc.mqasho.common.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import com.feerlaroc.mqasho.common.dagger.ObjectGraphService;

import butterknife.ButterKnife;
import mortar.Presenter;

/**
 * Created by root on 2016/05/04.
 */
public abstract class CustomCoordinatorLayout<T extends Presenter> extends CoordinatorLayout {

    public CustomCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ObjectGraphService.inject(context, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getPresenter().takeView(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getPresenter().dropView(this);
    }

    public abstract T getPresenter();
}
