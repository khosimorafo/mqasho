package com.feerlaroc.mqasho.placeholder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class EmptyDataMessage extends FrameLayout{

    private Context mContext;
    private TextView mMessage;

    public EmptyDataMessage(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EmptyDataMessage(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init(){

        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(com.feerlaroc.widgets.R.dimen.activity_horizontal_margin, com.feerlaroc.widgets.R.dimen.activity_horizontal_margin
                , com.feerlaroc.widgets.R.dimen.activity_horizontal_margin, com.feerlaroc.widgets.R.dimen.activity_horizontal_margin);
        params.gravity = Gravity.CENTER;

        setLayoutParams(params);

        mMessage = new TextView(mContext);

    }


    public void displayMessage(int message){

        mMessage.setText(message);

    }

    public void displayMessage(String message){

        mMessage.setText(message);

    }

    public void clearMessage(){

        mMessage.setText("");

    }

}
