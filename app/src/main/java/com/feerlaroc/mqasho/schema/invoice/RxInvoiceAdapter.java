package com.feerlaroc.mqasho.schema.invoice;

import android.content.Context;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.http.ServiceFactory;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.mqasho.schema.tenant.CustomerObservable;
import com.feerlaroc.utils.Helper;
import com.feerlaroc.zoho.retrofit.exception.ApiException;
import com.feerlaroc.zoho.rx.Holder;
import com.feerlaroc.zoho.rx.RxZohoRecyclerAdapter;
import com.feerlaroc.zoho.subscribers.RxSubscriber;
import com.feerlaroc.zoho.transformer.DefaultTransformer;

import java.util.List;
import java.util.Map;

import rx.functions.Action1;
import timber.log.Timber;

public class RxInvoiceAdapter extends RxZohoRecyclerAdapter<InvoiceHolder> {

    public RxInvoiceAdapter(int layout, Holder.SelectedItemListener listener, Context context) {

        super(layout, InvoiceHolder.class, listener);

        CustomerObservable.getCustomerIDSubject().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

                ServiceFactory.invoiceApi()
                        .getInvoiceByCustomerID(Constants.ZOHO.TOKEN, Constants.ZOHO.ORG_ID, s)
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
        });

    }

    @Override
    protected void populateViewHolder(InvoiceHolder viewHolder, Map model, int position) {

        viewHolder.textInvoiceNumber
                .setText(Helper.returnString(model.get(Constants.ZOHOINVOICESCHEMA.INVOICE_NUMBER)));
        viewHolder.textOutstandingAmount
                .setText(Helper.returnString(model.get(Constants.ZOHOINVOICESCHEMA.BALANCE)));

        String _status = String.valueOf(model.get(Constants.ZOHOINVOICESCHEMA.STATUS));

        switch(_status) {

            case Constants.STATUS.PAID:
                viewHolder.imageInvoiceStatus.setImageResource(R.drawable.ic_money_paid_24);
                break;
            case Constants.STATUS.UNPAID:
                viewHolder.imageInvoiceStatus.setImageResource(R.drawable.ic_unpaid_24);
                break;
            case Constants.STATUS.OVERDUE:
                viewHolder.imageInvoiceStatus.setImageResource(R.drawable.ic_time_span_24);
                break;
            case Constants.STATUS.DRAFT:
            default:
                viewHolder.imageInvoiceStatus.setImageResource(R.drawable.ic_unpaid_24);
                break;
        }

        viewHolder.setListener(mListener);

    }

    @Override
    public String getReference() {  return Constants.FEERLAROC.INVOICES;    }
}