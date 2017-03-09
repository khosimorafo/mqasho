package com.feerlaroc.mqasho.schema.tenant.view;

import android.content.Context;
import android.util.AttributeSet;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.widget.CustomFrameLayout;
import com.feerlaroc.mqasho.schema.invoice.view.InvoiceQuickPayView;
import com.feerlaroc.mqasho.schema.tenant.screen.CustomerPinScreen2;
import com.feerlaroc.widgets.view.DataPinView;

import javax.inject.Inject;


public class CustomerPinView2 extends CustomFrameLayout<CustomerPinScreen2.Presenter> {

    @Inject
    CustomerPinScreen2.Presenter mPresenter;

    InvoiceQuickPayView mInvoiceQuickPayView = new InvoiceQuickPayView(getContext(), null);

    DataPinView mDataPinView;

    public CustomerPinView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDataPinView = (DataPinView) findViewById(R.id.data_pin_customer2);
    }

    @Override
    public CustomerPinScreen2.Presenter getPresenter() {
        return mPresenter;
    }

    public DataPinView getDataPinView(){

        return mDataPinView;
    }

    public InvoiceQuickPayView getInvoiceQuickPayView() {

        return mInvoiceQuickPayView;
    }

    public void getResetView() {

        mInvoiceQuickPayView =  new InvoiceQuickPayView(getContext(), null);
    }
}
