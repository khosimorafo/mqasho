package com.feerlaroc.mqasho.schema.tenant.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.widget.CustomRelativeLayout;
import com.feerlaroc.mqasho.schema.tenant.screen.CustomerInvoiceTotalsScreen;
import com.feerlaroc.widgets.ReactiveCurrencyBite;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by root on 2016/09/05.
 */
public class CustomerInvoiceTotalsView extends CustomRelativeLayout<CustomerInvoiceTotalsScreen.Presenter> {

    private static final String TAG = "CustomerInvoiceTotalsView";

    @Inject
    CustomerInvoiceTotalsScreen.Presenter mPresenter;

    @InjectView(R.id.layout_customer_textbite)
    @Optional LinearLayout mLayoutCustomerBite;

    //@InjectView(R.id.textbite_outstanding_amount)
    ReactiveCurrencyBite mOutstandingAmount;

    public CustomerInvoiceTotalsView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        setupBiteLayout();
    }

    private void setupBiteLayout(){

        mOutstandingAmount = (ReactiveCurrencyBite) findViewById(R.id.textbite_outstanding_amount);
        mOutstandingAmount.setLabelText("Outstanding");
        mOutstandingAmount.setValueText("R 0");

    }

    public ReactiveCurrencyBite getOutstandingAmount(){

        return mOutstandingAmount;
    }

    @Override
    public CustomerInvoiceTotalsScreen.Presenter getPresenter() {

        return mPresenter;
    }
}
