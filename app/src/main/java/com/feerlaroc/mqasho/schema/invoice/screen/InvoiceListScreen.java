package com.feerlaroc.mqasho.schema.invoice.screen;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.feerlaroc.mqasho.ActivityModule;
import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.flow.Layout;
import com.feerlaroc.mqasho.common.mortarscreen.WithModule;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.mqasho.schema.invoice.InvoiceObservable;
import com.feerlaroc.mqasho.schema.invoice.RxInvoiceAdapter;
import com.feerlaroc.mqasho.schema.invoice.view.InvoiceListView;
import com.feerlaroc.mqasho.schema.payment.PaymentHandler;
import com.feerlaroc.zoho.rx.Holder;
import com.feerlaroc.zoho.rx.RxZohoDataSource;
import com.feerlaroc.zoho.rx.RxZohoRecyclerAdapter;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import mortar.ViewPresenter;

@Layout(R.layout.layout_invoice_list)
@WithModule(InvoiceListScreen.Module.class)
public class InvoiceListScreen {

    private static final String TAG = "InvoiceListScreen";

    @dagger.Module(injects = InvoiceListView.class, addsTo = ActivityModule.class)
    public class Module {}

    @Singleton
    public static class Presenter extends ViewPresenter<InvoiceListView>
            implements Holder.SelectedItemListener {

        RxZohoRecyclerAdapter mAdapter;
        RxZohoDataSource mDatasource;

        private Map<String, Object> mSelectedItem = new HashMap<>();


        @Inject
        public Presenter() {}

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            if(getView() == null) return;

            getView().getRecyclerView().setLayoutManager(layoutManager);

            mAdapter = new RxInvoiceAdapter(R.layout.row_invoice_card, this, getView().getContext());

            mDatasource = new RxZohoDataSource(mAdapter);
            mDatasource.bindRecycleView(getView().getRecyclerView());
        }

        @Override
        public void onItemClick(int position) {

            AlertDialog.Builder alert = new AlertDialog.Builder(getView().getContext());

            alert.setView(getView().getPayment());

            mSelectedItem = (Map<String, Object>) mAdapter.getItem(position);
            String _invoice_id = String.valueOf(mSelectedItem.get(Constants.ZOHOINVOICESCHEMA.INVOICE_ID));

            InvoiceObservable.getInvoiceNumberSubject()
                    .subscribe(alert::setTitle);

            getView().getPayment().getPaymentAmountActualNumberPicker()
                    .setKey(Constants.ZOHOINVOICESCHEMA.OUTSTANDING_AMOUNT)
                    .subscribeTo(InvoiceObservable.getAmountOustandingSubject());

            InvoiceObservable.getInvoiceNumberSubject()
                    .onNext(String.valueOf(mSelectedItem.get(Constants.ZOHOINVOICESCHEMA.INVOICE_NUMBER)));

            InvoiceObservable.getAmountOustandingSubject()
                    .onNext(Double.valueOf(String.valueOf(mSelectedItem.get(Constants.ZOHOINVOICESCHEMA.OUTSTANDING_AMOUNT))));

            alert.setNegativeButton("Cancel", (dialog, which) -> {

                dialog.cancel();
            });

            alert.setPositiveButton("Pay", (dialog, which) -> {

                int i = getView().getPayment().getPaymentAmountActualNumberPicker().getValue();
                Double _applied_amt = (double) i;

                new PaymentHandler(getView().getContext(), _applied_amt, _invoice_id).create();
            });

            alert.show();

        }
    }
}
