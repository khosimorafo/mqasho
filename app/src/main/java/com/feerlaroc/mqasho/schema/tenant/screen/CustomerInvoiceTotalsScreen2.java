package com.feerlaroc.mqasho.schema.tenant.screen;

import android.os.Bundle;

import com.feerlaroc.mqasho.ActivityModule;
import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.flow.Layout;
import com.feerlaroc.mqasho.common.mortarscreen.WithModule;
import com.feerlaroc.mqasho.schema.EntityViewScreen;
import com.feerlaroc.mqasho.schema.tenant.CustomerObservable;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerInvoiceTotalsView2;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.path.Path;

/**
 * Created by root on 2016/09/05.
 */

@Layout(R.layout.layout_customer_invoice_total2)
@WithModule(CustomerInvoiceTotalsScreen2.Module.class)
public class CustomerInvoiceTotalsScreen2 extends Path {

    private static final String TAG = "CustomerInvoiceTotalsScreen2";

    @dagger.Module(injects = CustomerInvoiceTotalsView2.class, addsTo = ActivityModule.class)
    public class Module {}

    @Singleton
    public static class Presenter extends EntityViewScreen<CustomerInvoiceTotalsView2> {

        @Inject
        public Presenter() {}

        @Override
        protected void onLoad(Bundle savedInstanceState) {

            super.onLoad(savedInstanceState);
            updateViewValues();
        }

        @Override
        protected void updateViewValues() {

            getView().getOutstandingAmount()
                    .subscribeTo(CustomerObservable.getAmountOustandingSubject());
        }
    }
}
