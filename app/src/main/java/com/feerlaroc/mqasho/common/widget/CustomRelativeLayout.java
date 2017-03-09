package com.feerlaroc.mqasho.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.feerlaroc.mqasho.common.dagger.ObjectGraphService;

import butterknife.ButterKnife;
import mortar.Presenter;

/**
 * Custom {@link RelativeLayout} that has support for { mortar.Mortar} and
 * {@link ButterKnife}.
 */
public abstract class CustomRelativeLayout<T extends Presenter> extends RelativeLayout {

    private boolean mDrawBorderTop = false;
    private boolean mDrawBorderBottom = false;

    public CustomRelativeLayout(Context context, AttributeSet attrs) {

      super(context, attrs);
      ObjectGraphService.inject(context, this);
    }

    @Override
    protected void onFinishInflate() {

      super.onFinishInflate();
      ButterKnife.inject(this);
    }

    @Override
    protected void onAttachedToWindow() {

      super.onAttachedToWindow();
      getPresenter().takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {

      super.onDetachedFromWindow();
      getPresenter().dropView(this);
    }

    public abstract T getPresenter();

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if(mDrawBorderBottom)
            drawBorderBottom(canvas);

        if(mDrawBorderTop)
            drawBorderTop(canvas);

    }

    private void drawBorderTop(Canvas canvas){

        float x = getX() + getWidth();
        float y = getY();

        Log.i("CustomRelativeLayout : ", "x is " + x + "y is " + y );

        Paint p = new Paint();
        p.setColor(Color.LTGRAY);
        canvas.drawLine(0, y + 1, x, y + 1, p);
    }

    private void drawBorderBottom(Canvas canvas){

        float x = getX() + getWidth();
        float y = getY() + getHeight();

        Log.i("CustomRelativeLayout : ", "x is " + x + "y is " + y );

        Paint p = new Paint();
        p.setColor(Color.LTGRAY);
        canvas.drawLine(0, y - 1, x, y - 1, p);
    }

    public void setDrawBorderTop(boolean drawBorderTop){

        mDrawBorderTop = drawBorderTop;
    }

    public void setDrawBorderBottom(boolean drawBorderBottom){

        mDrawBorderBottom = drawBorderBottom;
    }
}
