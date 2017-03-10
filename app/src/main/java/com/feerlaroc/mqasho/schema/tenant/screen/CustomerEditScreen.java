package com.feerlaroc.mqasho.schema.tenant.screen;

import android.graphics.Color;
import android.os.Bundle;

import com.feerlaroc.mqasho.ActivityModule;
import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.flow.Layout;
import com.feerlaroc.mqasho.common.mortarscreen.WithModule;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.mqasho.schema.EntityEditScreen;
import com.feerlaroc.mqasho.schema.tenant.CustomerEditHandler;
import com.feerlaroc.mqasho.schema.tenant.CustomerObservable;
import com.feerlaroc.mqasho.schema.tenant.view.CustomerEditView;
import com.feerlaroc.zoho.utils.DialogHelper;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Flow;
import flow.path.Path;
import rx.Observable;
import rx.subjects.PublishSubject;

@Layout(R.layout.layout_customer_edit)
@WithModule(CustomerEditScreen.Module.class)
public class CustomerEditScreen extends Path{

    private static final String TAG = "CustomerEditScreen";

    static EntityEditScreen.EditMode mEditMode;


    @dagger.Module(injects = CustomerEditView.class, addsTo = ActivityModule.class)
    public class Module {
    }

    public CustomerEditScreen(){

        mEditMode = EntityEditScreen.EditMode.NEW;
    }

    public CustomerEditScreen(EntityEditScreen.EditMode mode){

        mEditMode = mode;
    }

    @Singleton
    public static class Presenter extends EntityEditScreen<CustomerEditView> {

        static PublishSubject<Boolean> mCustomerCreatedResult = PublishSubject.create();

        CustomerEditHandler mEditHandler;

        @Inject
        public Presenter() {}

        @Override
        protected void onLoad(Bundle savedInstanceState) {

            super.onLoad(savedInstanceState);

            mEditHandler = new CustomerEditHandler(getView());

            getView().getSaveButton().setEnabled(false);
            getView().getSaveButton().setTextColor(Color.LTGRAY);

            RxView.clicks(getView().getSaveButton())
                    .subscribe(aVoid -> {

                        onSaveItem();
                    });

            navigateFromView(mEditHandler.getResultSubject());

            if (mEditMode == EditMode.UPDATE) {

                updateViewValues();
            }
        }

        @Override
        public void onSaveItem() {

            DialogHelper.showProgressDlg(getView().getContext(), "Saving Tenant...");
            mEditHandler.create();
        }

        private void navigateFromView(Observable<Boolean> observable){

            observable.subscribe(aBoolean -> {

                if(aBoolean) {

                    Flow.get(getView()).set(new CustomerDisplayScreen());
                }
                else{
                    Flow.get(getView()).set(new CustomerGridScreen());}
            });
        }

        @Override
        protected void updateViewValues() {

            getView().getTenantName().setKey(Constants.CUSTOMERSCHEMA.NAME)
                    .subscribeToString(CustomerObservable.getCustomerNameSubject());

            getView().getTenantZAID().setKey(Constants.CUSTOMERSCHEMA.ZAID)
                    .subscribeToString(CustomerObservable.getCustomerZAIDSubject());

            getView().getTenantMobile().setKey(Constants.CUSTOMERSCHEMA.TELEPHONE)
                    .subscribeToString(CustomerObservable.getCustomerMobileSubject());

            getView().getTenantTelephone().setKey(Constants.CUSTOMERSCHEMA.MOBILE)
                    .subscribeToString(CustomerObservable.getCustomerTelephoneSubject());
        }
    }
}
