package com.feerlaroc.mqasho.schema.tenant.screen;

import android.os.Bundle;

import com.feerlaroc.mqasho.ActivityModule;
import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.flow.Layout;
import com.feerlaroc.mqasho.common.mortarscreen.WithModule;
import com.feerlaroc.mqasho.schema.invoice.view.InvoiceQuickPayView;
import com.feerlaroc.mqasho.schema.tenant.RxCustomerInvoiceListAdapter;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerPinView;
import com.feerlaroc.zoho.rx.RxZohoDataSource;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.path.Path;
import mortar.ViewPresenter;

@Layout(R.layout.layout_customer_pin)
@WithModule(CustomerPinScreen.Module.class)
public class CustomerPinScreen extends Path {

    @dagger.Module(injects = {CustomerPinView.class, InvoiceQuickPayView.class}, addsTo = ActivityModule.class)
    public class Module {}

    @Singleton
    public static class Presenter extends ViewPresenter<CustomerPinView> {

        private RxCustomerInvoiceListAdapter mAdapter;
        private RxZohoDataSource mDatasource;
        private Map<String, Object> mSelectedItem = new HashMap<>();

        @Inject
        public Presenter() {}

        @Override
        protected void onLoad(Bundle savedInstanceState) {

            super.onLoad(savedInstanceState);

            mAdapter = new RxCustomerInvoiceListAdapter(getView().getContext(), 0);
            //mDatasource = new RxZohoDataSource(getView().getContext(), mAdapter);
            //mDatasource.bindPinView(getView().getDataPinView());
            //mDatasource.subscribeTo(CustomerObservable.getCustomerInvoicesSubject());

            /*
            getView().getDataPinView()
                    .setOnItemClickListener((parent, view, position, id) -> {

                        AlertDialog.Builder alert = new AlertDialog.Builder(getView().getContext());

                        if(!mAdapter.isEmpty()) {

                            alert.setView(getView().getInvoiceQuickPayView());

                            mSelectedItem = (Map<String, Object>) mAdapter.getItem(position);
                            String _invoice_id = String.valueOf(mSelectedItem.get(Constants.ZOHOINVOICESCHEMA.INVOICE_ID));

                            InvoiceObservable.getInvoiceNumberSubject()
                                    .subscribe(alert::setTitle);

                            getView().getInvoiceQuickPayView().getPaymentAmountActualNumberPicker()
                                    .setKey(Constants.ZOHOINVOICESCHEMA.OUTSTANDING_AMOUNT)
                                    .subscribeTo(InvoiceObservable.getAmountOustandingSubject());

                            InvoiceObservable.getInvoiceNumberSubject()
                                    .onNext(String.valueOf(mSelectedItem.get(Constants.ZOHOINVOICESCHEMA.INVOICE_NUMBER)));

                            InvoiceObservable.getAmountOustandingSubject()
                                    .onNext(Double.valueOf(String.valueOf(mSelectedItem.get(Constants.ZOHOINVOICESCHEMA.OUTSTANDING_AMOUNT))));

                            alert.setNegativeButton("Cancel", (dialog, which) -> {

                                dialog.cancel();
                                getView().getResetView();
                            });

                            alert.setPositiveButton("Pay", (dialog, which) -> {

                                int i = getView().getInvoiceQuickPayView().getPaymentAmountActualNumberPicker().getValue();
                                Double _applied_amt = (double) i;

                                new PaymentHandler(getView().getContext(), _applied_amt, _invoice_id).create();

                                //In the moron Donald J. Trump's voice, "Terrible programming. Just, terrible!"
                                CustomerDisplayView2 displayView2 = (CustomerDisplayView2) getView()
                                        .getParent().getParent().getParent().getParent().getParent();

                                displayView2.getResetView();
                            });

                            alert.show();
                        }
                    });
                    */
        }
    }
}
