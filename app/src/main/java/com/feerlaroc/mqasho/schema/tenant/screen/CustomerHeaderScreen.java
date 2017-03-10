package com.feerlaroc.mqasho.schema.tenant.screen;

import android.os.Bundle;

import com.feerlaroc.mqasho.ActivityModule;
import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.flow.Layout;
import com.feerlaroc.mqasho.common.mortarscreen.WithModule;
import com.feerlaroc.mqasho.schema.EntityViewScreen;
import com.feerlaroc.mqasho.schema.tenant.CustomerObservable;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerHeaderView;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.path.Path;

@Layout(R.layout.layout_customer_header)
@WithModule(CustomerHeaderScreen.Module.class)
public class CustomerHeaderScreen extends Path {

    private static final String TAG = "CustomerHeaderScreen";

    @dagger.Module(injects = CustomerHeaderView.class, addsTo = ActivityModule.class)
    public class Module {}

    @Singleton
    public static class Presenter extends EntityViewScreen<CustomerHeaderView> {

        @Inject
        public Presenter() {}

        @Override
        protected void onLoad(Bundle savedInstanceState) {

            super.onLoad(savedInstanceState);

            updateViewValues();
        }

        @Override
        protected void updateViewValues() {

            getView().getCustomerName()
                    .subscribeTo(CustomerObservable.getCustomerNameSubject());
        }
    }
}
