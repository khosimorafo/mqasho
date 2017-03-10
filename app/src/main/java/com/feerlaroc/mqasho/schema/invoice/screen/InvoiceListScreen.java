package com.feerlaroc.mqasho.schema.invoice.screen;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.feerlaroc.mqasho.ActivityModule;
import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.flow.Layout;
import com.feerlaroc.mqasho.common.mortarscreen.WithModule;
import com.feerlaroc.mqasho.schema.invoice.view.InvoiceListView;
import com.feerlaroc.mqasho.schema.tenant.RxCustomerAdapter;
import com.feerlaroc.zoho.rx.Holder;
import com.feerlaroc.zoho.rx.RxZohoDataSource;
import com.feerlaroc.zoho.rx.RxZohoRecyclerAdapter;

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

        @Inject
        public Presenter() {}

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getView().getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            if(getView() == null) return;

            getView().getRecyclerView().setLayoutManager(layoutManager);

            mAdapter = new RxCustomerAdapter(R.layout.row_invoice_card, this, getView().getContext());

            mDatasource = new RxZohoDataSource(mAdapter);
            mDatasource.bindRecycleView(getView().getRecyclerView());
        }

        @Override
        public void onItemClick(int position) {

        }
    }
}
