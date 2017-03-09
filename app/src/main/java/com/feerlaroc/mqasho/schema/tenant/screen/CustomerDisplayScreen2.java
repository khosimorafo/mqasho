package com.feerlaroc.mqasho.schema.tenant.screen;

import android.os.Bundle;

import com.feerlaroc.mqasho.ActivityModule;
import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.flow.Layout;
import com.feerlaroc.mqasho.common.mortarscreen.WithModule;
import com.feerlaroc.mqasho.schema.EntityEditScreen;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerDisplayView2;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerHeaderView2;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerInvoiceTotalsView2;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerPinView2;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Flow;
import flow.path.Path;
import mortar.ViewPresenter;

@Layout(R.layout.layout_customer_display2)
@WithModule(CustomerDisplayScreen2.Module.class)
public class CustomerDisplayScreen2 extends Path {

    private static final String TAG = "CustomerDisplayScreen2";

    @dagger.Module(injects = {CustomerDisplayView2.class, CustomerPinView2.class,
            CustomerInvoiceTotalsView2.class, CustomerHeaderView2.class}
            , addsTo = ActivityModule.class)
    public class Module {}

    @Singleton
    public static class Presenter extends ViewPresenter<CustomerDisplayView2> {

        @Inject
        public Presenter() {}

        @Override
        protected void onLoad(Bundle savedInstanceState) {

            super.onLoad(savedInstanceState);
        }

        public void editButtonClicked(){

            Flow.get(getView()).set(new TenantEditScreen(EntityEditScreen.EditMode.UPDATE));
        }
    }
}
