package com.feerlaroc.mqasho.schema.invoice;

import android.content.Context;

import com.feerlaroc.mqasho.http.ServiceFactory;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.utils.Helper;
import com.feerlaroc.zoho.retrofit.exception.ApiException;
import com.feerlaroc.zoho.rx.Holder;
import com.feerlaroc.zoho.rx.RxZohoRecyclerAdapter;
import com.feerlaroc.zoho.subscribers.RxSubscriber;
import com.feerlaroc.zoho.transformer.DefaultTransformer;

import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class RxInvoiceAdapter extends RxZohoRecyclerAdapter<InvoiceHolder> {

    public RxInvoiceAdapter(int layout, Holder.SelectedItemListener listener, Context context) {

        super(layout, InvoiceHolder.class, listener);

        ServiceFactory.invoiceApi()
                .get(Constants.ZOHO.TOKEN, Constants.ZOHO.ORG_ID)
                .compose(new DefaultTransformer<>())
                .subscribe(new RxSubscriber<List<Map<String, Object>>>(context) {

                    @Override
                    public void onNext(List<Map<String, Object>> data) {

                        updateDataset(data);
                        Timber.i( "Customer data was loaded from API." );
                    }

                    @Override
                    protected void onError(ApiException ex) {

                        super.onError(ex);
                        Timber.e( ex, "Unable to load the customer data from API." );
                    }

                    @Override
                    public void onCompleted() {

                        super.onCompleted();
                    }
                });
    }

    @Override
    protected void populateViewHolder(InvoiceHolder viewHolder, Map model, int position) {

        viewHolder.textCustomerName
                .setText(Helper.returnString(model.get(Constants.ZOHOCONTACTSCHEMA.CUSTOMER_NAME)));
        viewHolder.textOutstandingAmount
                .setText(Helper.returnString(model.get(Constants.ZOHOCONTACTSCHEMA.OUTSTANDING_AMOUNT)));

        viewHolder.setListener(mListener);

    }

    @Override
    public String getReference() {  return Constants.FEERLAROC.CUSTOMERS;    }
}