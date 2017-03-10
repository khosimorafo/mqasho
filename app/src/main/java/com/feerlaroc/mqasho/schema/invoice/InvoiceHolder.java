package com.feerlaroc.mqasho.schema.invoice;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.zoho.rx.Holder;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by root on 2017/01/14.
 */

public class InvoiceHolder extends Holder {

    @InjectView(R.id.text_invoice_number)       TextView textInvoiceNumber;
    @InjectView(R.id.text_amount_outstanding)   TextView textOutstandingAmount;
    @InjectView(R.id.text_invoice_detail)       TextView textInvoiceDetail;

    @InjectView(R.id.image_invoice_status)      ImageView imageInvoiceStatus;

    public InvoiceHolder(View view) {

        super(view);
        ButterKnife.inject(this, view);
        view.setOnClickListener(this);
    }
}
