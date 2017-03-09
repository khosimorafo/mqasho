package com.feerlaroc.mqasho.schema.start.view;

import android.content.Context;
import android.util.AttributeSet;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.widget.CustomRelativeLayout;
import com.feerlaroc.mqasho.schema.start.screen.AppStartScreen;
import com.feerlaroc.widgets.loading.newton.NewtonCradleLoading;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by root on 2016/06/09.
 */
public class AppStartView extends CustomRelativeLayout<AppStartScreen.Presenter> {

    @Inject
    AppStartScreen.Presenter mPresenter;


    @InjectView(R.id.newton_cradle_appstart)
    NewtonCradleLoading mNewtonCradleLoading;

    public AppStartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    public AppStartScreen.Presenter getPresenter() {
        return mPresenter;
    }

    public NewtonCradleLoading getNewtonCradleLoading() {
        return mNewtonCradleLoading;
    }
}
