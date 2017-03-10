package com.feerlaroc.mqasho.schema.tenant.screen;

import android.graphics.Color;
import android.os.Bundle;

import com.feerlaroc.mqasho.ActivityModule;
import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.common.flow.Layout;
import com.feerlaroc.mqasho.common.mortarscreen.WithModule;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.mqasho.schema.EntityEditScreen;
import com.feerlaroc.mqasho.schema.tenant.CustomerObservable;
import com.feerlaroc.mqasho.schema.tenant.TenantEditHandler;
import com.feerlaroc.mqasho.schema.tenant.view.TenantEditView;
import com.feerlaroc.zoho.utils.DialogHelper;
import com.jakewharton.rxbinding.view.RxView;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Flow;
import flow.path.Path;
import rx.Observable;
import rx.subjects.PublishSubject;

@Layout(R.layout.layout_tenant_edit)
@WithModule(TenantEditScreen.Module.class)
public class TenantEditScreen extends Path{

    private static final String TAG = "TenantEditScreen";

    static EntityEditScreen.EditMode mEditMode;


    @dagger.Module(injects = TenantEditView.class, addsTo = ActivityModule.class)
    public class Module {
    }

    public TenantEditScreen(){

        mEditMode = EntityEditScreen.EditMode.NEW;
    }

    public TenantEditScreen(EntityEditScreen.EditMode mode){

        mEditMode = mode;
    }

    @Singleton
    public static class Presenter extends EntityEditScreen<TenantEditView> {

        static PublishSubject<Boolean> mCustomerCreatedResult = PublishSubject.create();

        TenantEditHandler mEditHandler;

        @Inject
        public Presenter() {}

        @Override
        protected void onLoad(Bundle savedInstanceState) {

            super.onLoad(savedInstanceState);

            mEditHandler = new TenantEditHandler(getView());

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
