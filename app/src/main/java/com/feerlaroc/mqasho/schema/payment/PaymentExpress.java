package com.feerlaroc.mqasho.schema.payment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.widgets.ReactiveActualNumberPicker;

public class PaymentExpress extends LinearLayout {

    ReactiveActualNumberPicker mPaymentAmountActualNumberPicker;

    public PaymentExpress(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(getContext(), R.layout.layout_payment_express, this);
        mPaymentAmountActualNumberPicker = (ReactiveActualNumberPicker)
                findViewById(R.id.actual_picker_layout_payment_express);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
    }

    public ReactiveActualNumberPicker getPaymentAmountActualNumberPicker(){

        return mPaymentAmountActualNumberPicker;
    }
}
