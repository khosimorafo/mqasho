package com.feerlaroc.mqasho.schema.tenant.view;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.widget.Button;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.widget.CustomRelativeLayout;
import com.feerlaroc.mqasho.schema.tenant.screen.CustomerEditScreen;
import com.feerlaroc.widgets.ReactiveEditText;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;

import butterknife.InjectView;

public class CustomerEditView extends CustomRelativeLayout<CustomerEditScreen.Presenter> {

    @Inject
    CustomerEditScreen.Presenter mPresenter;

    @InjectView(R.id.input_layout_customer_zaid) TextInputLayout mInputLayoutZAID;

    @InjectView(R.id.input_tenant_name)         ReactiveEditText mEditTextTenantName;
    @InjectView(R.id.input_tenant_zaid)         ReactiveEditText mEditTextTenantZAID;
    @InjectView(R.id.input_tenant_mobile)       ReactiveEditText mEditTextTenantMobile;
    @InjectView(R.id.input_tenant_telephone)    ReactiveEditText mEditTextTenantTelephone;

    @InjectView(R.id.toggle_customer_gender)    GenderToggleButton mGenderToggleButton;
    @InjectView(R.id.toggle_customer_property)  SiteToggleButton mSiteToggleButton;

    Button mSaveTenantButton;

    public CustomerEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();

        mSaveTenantButton = (Button) findViewById(R.id.layout_tenant_btn_save);

        RxView.clicks(mSaveTenantButton).subscribe(aVoid -> {
                    getPresenter().onSaveItem();
                });

        mGenderToggleButton.setOnValueChangedListener(value ->
                mGenderToggleButton.getSelectedSubject().onNext(value));

        mSiteToggleButton.setOnValueChangedListener(value ->
                mGenderToggleButton.getSelectedSubject().onNext(value));
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

    public GenderToggleButton getGenderToggleButton() {
        return mGenderToggleButton;
    }

    public SiteToggleButton getSiteToggleButton() {
        return mSiteToggleButton;
    }

    public Button getSaveButton(){

        return mSaveTenantButton;
    }

}
