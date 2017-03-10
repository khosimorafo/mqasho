package com.feerlaroc.mqasho.schema.tenant.screen;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.feerlaroc.mqasho.ActivityModule;
import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.flow.Layout;
import com.feerlaroc.mqasho.common.mortarscreen.WithModule;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.mqasho.schema.tenant.CustomerObservable;
import com.feerlaroc.mqasho.schema.tenant.RxCustomerAdapter;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerGridView;
import com.feerlaroc.zoho.rx.Holder;
import com.feerlaroc.zoho.rx.RxZohoDataSource;
import com.feerlaroc.zoho.rx.RxZohoRecyclerAdapter;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Flow;
import flow.path.Path;
import mortar.ViewPresenter;

@Layout(R.layout.layout_customer_list)
@WithModule(CustomerGridScreen.Module.class)
public class CustomerGridScreen extends Path {

    private static final String TAG = "CustomerGridScreen";

    @dagger.Module(injects = CustomerGridView.class, addsTo = ActivityModule.class)
    public class Module {}

    @Singleton
    public static class Presenter extends ViewPresenter<CustomerGridView>
            implements Holder.SelectedItemListener {

        RxZohoRecyclerAdapter mAdapter;
        RxZohoDataSource mDatasource;

        @Inject
        public Presenter() {}

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            if(getView() == null) return;

            getView().getRecyclerView().setLayoutManager(layoutManager);

            mAdapter = new RxCustomerAdapter(R.layout.row_customer_card, this, getView().getContext());

            mDatasource = new RxZohoDataSource(mAdapter);
            mDatasource.bindRecycleView(getView().getRecyclerView());

            configureViewObservables();
        }

        private void configureViewObservables() {

            RxView.clicks(getView().getFab())
                    .subscribe(aVoid -> {
                        addNewCustomer();
                    });
        }

        public void addNewCustomer(){

            Flow.get(getView()).set(new CustomerEditScreen());
        }

        @Override
        public void onItemClick(int position) {

            String _id = String.valueOf(mAdapter.getItem(position).get(Constants.ZOHOCONTACTSCHEMA.CUSTOMER_ID));

            CustomerObservable.getInstance().set(getView().getContext(), _id);
            Flow.get(getView()).set(new CustomerDisplayScreen());
        }
    }
}
