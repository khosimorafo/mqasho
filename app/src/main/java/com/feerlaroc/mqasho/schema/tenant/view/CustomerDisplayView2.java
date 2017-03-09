package com.feerlaroc.mqasho.schema.tenant.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.widget.CustomLinearLayout;
import com.feerlaroc.mqasho.schema.tenant.screen.CustomerDisplayScreen2;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;

public class CustomerDisplayView2 extends CustomLinearLayout<CustomerDisplayScreen2.Presenter> {

    private static final String TAG = "CustomerDisplayView";

    @Inject
    CustomerDisplayScreen2.Presenter mPresenter;

    CustomerHeaderView2 mCustomerHeaderView;
    CustomerPinView2 mCustomerPinView;
    CustomerInvoiceTotalsView2 mCustomerInvoiceTotalsView;

    @InjectView(R.id.top)
    LinearLayout mLayoutTop;
    @InjectView(R.id.second_row)
    LinearLayout mLayoutSecondRow;
    @InjectView(R.id.third_row)
    LinearLayout mLayoutThirdRow;

    @InjectView(R.id.image_edit_customer_header)
    ImageView mEditCustomerImageView;


    public CustomerDisplayView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        ButterKnife.inject(this);

        setupCustomerHeaderView();
        setupCustomerPinView();
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

        mCustomerHeaderView = (CustomerHeaderView2) View
                .inflate(getContext(), R.layout.layout_customer_header2, null);

        mCustomerHeaderView.setBackgroundColor(Color.TRANSPARENT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        mCustomerHeaderView.setLayoutParams(params);
        mCustomerHeaderView.setDrawBorderBottom(true);
        mLayoutTop.addView(mCustomerHeaderView);

    }

    private void setupCustomerPinView(){

        mCustomerPinView = (CustomerPinView2) View
                .inflate(getContext(), R.layout.layout_customer_pin2, null);

        mCustomerPinView.setBackgroundColor(Color.TRANSPARENT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mCustomerPinView.setLayoutParams(params);
        mLayoutSecondRow.addView(mCustomerPinView);

    }

    private void setupInvoiceTotalsView() {

        mCustomerInvoiceTotalsView = (CustomerInvoiceTotalsView2) View
                .inflate(getContext(), R.layout.layout_customer_invoice_total2, null);

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
    public CustomerDisplayScreen2.Presenter getPresenter() {

        return mPresenter;
    }

    public void getResetView() {

        mCustomerPinView = null;
        setupCustomerPinView();
    }
}
