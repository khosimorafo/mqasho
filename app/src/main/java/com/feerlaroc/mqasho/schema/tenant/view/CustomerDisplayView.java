package com.feerlaroc.mqasho.schema.tenant.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.widget.CustomLinearLayout;
import com.feerlaroc.mqasho.schema.invoice.view.InvoiceListView;
import com.feerlaroc.mqasho.schema.tenant.screen.CustomerDisplayScreen;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;

public class CustomerDisplayView extends CustomLinearLayout<CustomerDisplayScreen.Presenter> {

    private static final String TAG = "CustomerDisplayView";

    @Inject
    CustomerDisplayScreen.Presenter mPresenter;

    CustomerHeaderView mCustomerHeaderView;
    CustomerPinView mCustomerPinView;
    CustomerInvoiceTotalsView mCustomerInvoiceTotalsView;
    InvoiceListView mOutstandingInvoiceList;

    @InjectView(R.id.top)
    LinearLayout mLayoutTop;
    @InjectView(R.id.second_row)
    LinearLayout mLayoutSecondRow;
    @InjectView(R.id.third_row)
    LinearLayout mLayoutThirdRow;

    @InjectView(R.id.image_edit_customer_header)
    ImageView mEditCustomerImageView;


    public CustomerDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        ButterKnife.inject(this);

        setupCustomerHeaderView();
        //setupCustomerPinView();
        setupCustomerInvoiceListView();
        setupInvoiceTotalsView();

        setupEditButton();
    }

    private void setupEditButton() {

        Observable<Void> editCustomerObservable = RxView.clicks(mEditCustomerImageView).share();
        editCustomerObservable.subscribe(aVoid -> {

            getPresenter().editButtonClicked();
        });
    }

    private void setupCustomerHeaderView(){

        mCustomerHeaderView = (CustomerHeaderView) View
                .inflate(getContext(), R.layout.layout_customer_header, null);

        mCustomerHeaderView.setBackgroundColor(Color.TRANSPARENT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        mCustomerHeaderView.setLayoutParams(params);
        mCustomerHeaderView.setDrawBorderBottom(true);
        mLayoutTop.addView(mCustomerHeaderView);

    }

    private void setupCustomerPinView(){

        mCustomerPinView = (CustomerPinView) View
                .inflate(getContext(), R.layout.layout_customer_pin, null);

        mCustomerPinView.setBackgroundColor(Color.TRANSPARENT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mCustomerPinView.setLayoutParams(params);
        mLayoutSecondRow.addView(mCustomerPinView);

    }

    private void setupCustomerInvoiceListView(){

        mOutstandingInvoiceList = (InvoiceListView) View
                .inflate(getContext(), R.layout.layout_invoice_list, null);

        mOutstandingInvoiceList.setBackgroundColor(Color.TRANSPARENT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mOutstandingInvoiceList.setLayoutParams(params);
        mLayoutSecondRow.addView(mOutstandingInvoiceList);

    }

    private void setupInvoiceTotalsView() {

        mCustomerInvoiceTotalsView = (CustomerInvoiceTotalsView) View
                .inflate(getContext(), R.layout.layout_customer_invoice_total, null);

        mCustomerInvoiceTotalsView.setBackgroundColor(Color.TRANSPARENT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        mCustomerInvoiceTotalsView.setLayoutParams(params);
        mCustomerInvoiceTotalsView.setPadding(10,10,10,10);
        mCustomerInvoiceTotalsView.setDrawBorderBottom(true);
        mCustomerInvoiceTotalsView.setDrawBorderTop(true);

        mLayoutThirdRow.addView(mCustomerInvoiceTotalsView);
    }

    @Override
    public CustomerDisplayScreen.Presenter getPresenter() {

        return mPresenter;
    }

    public void getResetView() {

        mCustomerPinView = null;
        setupCustomerPinView();
    }
}
