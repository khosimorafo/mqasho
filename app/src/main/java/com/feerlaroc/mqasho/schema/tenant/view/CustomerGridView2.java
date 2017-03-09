package com.feerlaroc.mqasho.schema.tenant.view;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.widget.CustomLinearLayout;
import com.feerlaroc.mqasho.schema.tenant.screen.CustomerGridScreen2;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

/**
 * Created by root on 2017/01/13.
 */

public class CustomerGridView2 extends CustomLinearLayout<CustomerGridScreen2.Presenter> {

    @Inject
    CustomerGridScreen2.Presenter mPresenter;

    RecyclerView mRecyclerView;

    FloatingActionButton mFabAddNewCustom;

    Toolbar mToolbar;

    SearchView mSearchView;
    MenuItem searchMenuItem;


    public CustomerGridView2(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_customer_list);
        mFabAddNewCustom = (FloatingActionButton) findViewById(R.id.fab_add_new_customer);

        RxView.clicks(mFabAddNewCustom)
                .subscribe(aVoid -> {

                    getPresenter().addNewCustomer();;
                });
    }

    @Override
    public CustomerGridScreen2.Presenter getPresenter() {

        return mPresenter;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public FloatingActionButton getFab(){

        return mFabAddNewCustom;
    }

}
