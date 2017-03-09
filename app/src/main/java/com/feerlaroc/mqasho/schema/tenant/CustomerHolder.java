package com.feerlaroc.mqasho.schema.tenant;

import android.view.View;
import android.widget.TextView;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.zoho.rx.Holder;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by root on 2017/01/14.
 */

public class CustomerHolder extends Holder {

    @InjectView(R.id.text_customer_name)TextView textCustomerName;
    @InjectView(R.id.text_outstanding_amount) TextView textOutstandingAmount;

    public CustomerHolder(View view) {

        super(view);
        ButterKnife.inject(this, view);
        view.setOnClickListener(this);
    }
}
