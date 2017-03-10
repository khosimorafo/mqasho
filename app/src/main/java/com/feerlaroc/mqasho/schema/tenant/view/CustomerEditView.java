package com.feerlaroc.mqasho.schema.tenant.view;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.widget.Button;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.widget.CustomRelativeLayout;
import com.feerlaroc.mqasho.schema.tenant.screen.CustomerEditScreen;
import com.feerlaroc.widgets.ReactiveEditText;
import com.feerlaroc.widgets.ReactiveToggleButton;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by root on 2016/03/23.
 */
public class CustomerEditView extends CustomRelativeLayout<CustomerEditScreen.Presenter> {

    @Inject
    CustomerEditScreen.Presenter mPresenter;

    @InjectView(R.id.input_layout_customer_zaid)
    TextInputLayout mInputLayoutZAID;

    @InjectView(R.id.input_tenant_name)
    ReactiveEditText mEditTextTenantName;
    @InjectView(R.id.input_tenant_zaid)
    ReactiveEditText mEditTextTenantZAID;
    @InjectView(R.id.input_tenant_mobile)
    ReactiveEditText mEditTextTenantMobile;
    @InjectView(R.id.input_tenant_telephone)
    ReactiveEditText mEditTextTenantTelephone;

    @InjectView(R.id.toggle_customer_gender)
    ReactiveToggleButton mToggleButton;

    Button mSaveTenantButton;


    public CustomerEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

        mSaveTenantButton = (Button) findViewById(R.id.layout_tenant_btn_save);

        /*
        RxView.clicks(mSaveTenantButton)
                .subscribe(aVoid -> {
                    getPresenter().onSaveItem();
                });
                */
    }

    @Override
    public CustomerEditScreen.Presenter getPresenter() {
        return mPresenter;
    }

    public ReactiveEditText getTenantName() {

        return mEditTextTenantName;
    }

    public ReactiveEditText getTenantZAID() {

        return mEditTextTenantZAID;
    }

    public ReactiveEditText getTenantTelephone() {

        return mEditTextTenantTelephone;
    }

    public ReactiveEditText getTenantMobile() {

        return mEditTextTenantMobile;
    }

    public TextInputLayout getInputLayoutZAID(){

        return mInputLayoutZAID;
    }

    public Button getSaveButton(){

        return mSaveTenantButton;
    }

}
