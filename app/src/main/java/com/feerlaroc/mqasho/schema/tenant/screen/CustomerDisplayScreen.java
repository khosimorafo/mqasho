package com.feerlaroc.mqasho.schema.tenant.screen;

import android.os.Bundle;

import com.feerlaroc.mqasho.ActivityModule;
import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.flow.Layout;
import com.feerlaroc.mqasho.common.mortarscreen.WithModule;
import com.feerlaroc.mqasho.schema.EntityEditScreen;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerDisplayView;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerHeaderView;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerInvoiceTotalsView;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerPinView;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Flow;
import flow.path.Path;
import mortar.ViewPresenter;

@Layout(R.layout.layout_customer_display)
@WithModule(CustomerDisplayScreen.Module.class)
public class CustomerDisplayScreen extends Path {

    private static final String TAG = "CustomerDisplayScreen";

    @dagger.Module(injects = {CustomerDisplayView.class, CustomerPinView.class,
            CustomerInvoiceTotalsView.class, CustomerHeaderView.class}
            , addsTo = ActivityModule.class)
    public class Module {}

    @Singleton
    public static class Presenter extends ViewPresenter<CustomerDisplayView> {

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
