package com.feerlaroc.mqasho.schema.invoice.view;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.widget.CustomLinearLayout;
import com.feerlaroc.mqasho.schema.invoice.screen.InvoiceListScreen;
import com.feerlaroc.mqasho.schema.payment.PaymentExpress;

import javax.inject.Inject;

public class InvoiceListView extends CustomLinearLayout<InvoiceListScreen.Presenter> {

    @Inject
    InvoiceListScreen.Presenter mPresenter;

    RecyclerView mRecyclerView;

    PaymentExpress mPaymentExpress = new PaymentExpress(getContext(), null);


    public InvoiceListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_invoice_list);

    }

    public PaymentExpress getPayment() {

        return mPaymentExpress;
    }


    @Override
    public InvoiceListScreen.Presenter getPresenter() {
        return mPresenter;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
