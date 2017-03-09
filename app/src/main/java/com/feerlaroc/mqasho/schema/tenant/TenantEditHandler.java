package com.feerlaroc.mqasho.schema.tenant;

import android.graphics.Color;
import android.widget.Toast;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.rx.RxHelper;
import com.feerlaroc.mqasho.rx.formvalidation.ValidationResult;
import com.feerlaroc.mqasho.rx.formvalidation.Validations;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.mqasho.schema.tenant.view.TenantEditView;
import com.feerlaroc.zoho.entity.HttpResult;
import com.feerlaroc.zoho.retrofit.exception.ApiException;
import com.feerlaroc.zoho.subscribers.CommonSubscriber;
import com.feerlaroc.zoho.utils.DialogHelper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func3;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import timber.log.Timber;

public class TenantEditHandler {

    private CustomerFactory mCustomerFactory = new CustomerFactory().initialize();
    private Subscription mSubscription;

    private static PublishSubject<String> mCustomerNameSubject = PublishSubject.create();
    private static PublishSubject<String> mCustomerZAIDSubject = PublishSubject.create();
    private static PublishSubject<String> mCustomerMobileSubject = PublishSubject.create();
    private static PublishSubject<String> mCustomerTelephoneSubject = PublishSubject.create();

    private PublishSubject<Boolean> mResultSubject = PublishSubject.create();

    private TenantEditView mView;

    private TenantRecurringInvoiceHandler<HttpResult<List<Map<String, Object>>>> mRecurringInvoiceHandler;

    public TenantEditHandler(TenantEditView view){

        mView = view;
        configureObservables();
        mRecurringInvoiceHandler = new TenantRecurringInvoiceHandler();
    }

    public void create(){

        mCustomerFactory.post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(saveCustomerSubscriber());
    }

    private void createRecurringInvoice(){

        mRecurringInvoiceHandler.saveInvoice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(saveInvoiceSubscriber());
    }

    private void configureObservables(){

        mCustomerFactory.captureCustomerName(mCustomerNameSubject);
        mCustomerFactory.captureCustomerZAID(mCustomerZAIDSubject);
        mCustomerFactory.captureCustomerMobile(mCustomerMobileSubject);
        mCustomerFactory.captureCustomerTelephone(mCustomerTelephoneSubject);

        Observable<Boolean> nameObservable = RxHelper.getTextWatcherObservable(mView.getTenantName())
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> {

                    ValidationResult result = Validations.validatePerson(s);
                    mView.getTenantName().setError(result.getReason());

                    if(result.isValid()) {
                        mCustomerNameSubject.onNext(s);
                        CustomerObservable.getCustomerNameSubject()
                                .onNext(s);
                    }

                    return result.isValid();
                });

        Observable<Boolean> zaidObservable = RxHelper.getTextWatcherObservable(mView.getTenantZAID())
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> {

                    ValidationResult result = Validations.validateZAID(s);
                    mView.getTenantZAID().setError(result.getReason());

                    if(result.isValid()) { mCustomerZAIDSubject.onNext(s);}

                    return result.isValid();
                });

        Observable<Boolean> mobileObservable = RxHelper.getTextWatcherObservable(mView.getTenantMobile())
                .debounce(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> {

                    ValidationResult result = Validations.validatePhone(s);
                    mView.getTenantMobile().setError(result.getReason());

                    if(result.isValid()) { mCustomerMobileSubject.onNext(s);}

                    return result.isValid();
                });


        Observable
                .combineLatest(nameObservable, mobileObservable, zaidObservable, new Func3<Boolean, Boolean, Boolean, Boolean>() {

            @Override
            public Boolean call(Boolean name, Boolean mobile, Boolean zaid) {

                return name && mobile && zaid;
            }
        }).subscribe(aBoolean -> {

            if(!aBoolean){

                mView.getSaveButton().setTextColor(Color.LTGRAY);
            }else {

                mView.getSaveButton().setTextColor(Color.WHITE);
            }
            mView.getSaveButton().setEnabled(aBoolean);
        });


    }

    public PublishSubject<Boolean> getResultSubject(){

        return mResultSubject;
    }

    private CommonSubscriber<HttpResult<Map<String, Object>>> saveCustomerSubscriber(){

        return new CommonSubscriber<HttpResult<Map<String, Object>>>(mView.getContext()) {

            @Override
            public void onStart() {

                super.onStart();
                DialogHelper.showProgressDlg(mView.getContext(), "Kancane nje!");
            }

            @Override
            public void onCompleted() {

                DialogHelper.stopProgressDlg();
            }

            @Override
            public void onError(Throwable e) {

                Timber.e("Failed to save tenant");
                Toast.makeText(mView.getContext(), R.string.failed_to_save, Toast.LENGTH_LONG).show();
                mResultSubject.onNext(false);
            }

            @Override
            public void onNext(HttpResult<Map<String, Object>> response) {

                Timber.i(String.valueOf(response.getMessage()));

                if(response.getCode() == 0){

                    CustomerObservable.getInstance()
                            .set(mView.getContext(), String.valueOf(response.getData().get(Constants.ZOHOCONTACTSCHEMA.CUSTOMER_ID)));
                    createRecurringInvoice();
                }
            }
        };
    }

    private CommonSubscriber<HttpResult<Map<String, Object>>> saveInvoiceSubscriber(){

        return new CommonSubscriber<HttpResult<Map<String, Object>>>(mView.getContext()) {

            @Override
            public void onStart() {

                super.onStart();
                DialogHelper.showProgressDlg(mView.getContext(), "Saving Recurring Invoice...");
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            protected void onError(ApiException ex) {
                super.onError(ex);
                mResultSubject.onNext(false);
            }

            @Override
            public void onNext(HttpResult<Map<String, Object>> result) {

                if(result.getCode() == 0) {

                    mResultSubject.onNext(true);
                }
                else {
                    mResultSubject.onNext(false);
                }
            }
        };
    }
}
