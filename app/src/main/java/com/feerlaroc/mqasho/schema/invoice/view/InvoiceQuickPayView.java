package com.feerlaroc.mqasho.schema.invoice.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.widgets.ReactiveActualNumberPicker;


/**
 * Created by root on 2017/01/15.
 */

public class InvoiceQuickPayView extends LinearLayout {

    ReactiveActualNumberPicker mPaymentAmountActualNumberPicker;

    public InvoiceQuickPayView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(getContext(), R.layout.layout_invoice_quick_pay, this);
        mPaymentAmountActualNumberPicker = (ReactiveActualNumberPicker)
                findViewById(R.id.actual_picker_layout_invoice_quick_payment);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
    }

    public ReactiveActualNumberPicker getPaymentAmountActualNumberPicker(){

        return mPaymentAmountActualNumberPicker;
    }
}
