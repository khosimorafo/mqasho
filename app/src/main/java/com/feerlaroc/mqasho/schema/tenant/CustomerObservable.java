package com.feerlaroc.mqasho.schema.tenant;

import android.content.Context;

import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.zoho.entity.HttpResult;
import com.feerlaroc.zoho.subscribers.CommonSubscriber;

import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import timber.log.Timber;


public class CustomerObservable {

    static String TAG = "CustomerObservable";

    static BehaviorSubject<String> mCustomerIDSubject = BehaviorSubject.create();
    static BehaviorSubject<String> mCustomerNameSubject = BehaviorSubject.create();
    static BehaviorSubject<String> mCustomerZAID = BehaviorSubject.create();
    static BehaviorSubject<String> mCustomerMobile = BehaviorSubject.create();
    static BehaviorSubject<String> mCustomerTelephone = BehaviorSubject.create();

    static PublishSubject<Double> mAmountOustandingSubject = PublishSubject.create();
    static PublishSubject<List<Map<String, Object>>> mCustomerInvoicesSubject = PublishSubject.create();
    static PublishSubject<List<Map<String, Object>>> mCustomerPaymentsSubject = PublishSubject.create();

    private static final CustomerObservable holder = new CustomerObservable();

    public static CustomerObservable getInstance() { return holder;}

    private CustomerObservable(){

        mCustomerIDSubject.subscribe(s -> {

            CustomerObservableFactory.getCustomerInvoices(s)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {

                        if(result.getCode()==0){

                            mCustomerInvoicesSubject.onNext(result.getData());
                        }else {

                            Timber.i("Failed to query customer invoice data");
                        }
                    });

            CustomerObservableFactory.getCustomerPayments(s)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {

                        if(result.getCode()==0){

                            mCustomerPaymentsSubject.onNext(result.getData());
                        }else {

                            Timber.i("Failed to query customer payments data");
                        }
                    });
        });
    }

    public void set(Context context, String id){

        mCustomerIDSubject
                .onNext(id);

        CustomerObservableFactory.getCustomer(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<HttpResult<Map<String,Object>>>(context) {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Timber.i("Failed to query customer data");
                        super.onError(e);
                    }

                    @Override
                    public void onNext(HttpResult<Map<String, Object>> result) {

                        if(result.getCode() == 0){

                            Map<String, Object> map = result.getData();

                            setCustomerData(map);
                        }
                    }
                });
    }

    void setCustomerData(Map<String, Object> map){

        mCustomerNameSubject
                .onNext(map.get(Constants.ZOHOCONTACTSCHEMA.CUSTOMER_NAME).toString());

        mAmountOustandingSubject
                .onNext(Double.valueOf(map.get(Constants.ZOHOCONTACTSCHEMA.OUTSTANDING_AMOUNT)
                        .toString()));

        //Customer Contact
        List<Map<String, Object>> contact_list =
                (List<Map<String, Object>>) map.get(Constants.ZOHOCONTACTSCHEMA.CONTACT_PERSONS);
        Map<String, Object> contact_map = contact_list.get(0);

        mCustomerMobile
                .onNext(String.valueOf(contact_map.get(Constants.ZOHOCONTACTSCHEMA.MOBILE)));

        mCustomerTelephone
                .onNext(String.valueOf(contact_map.get(Constants.ZOHOCONTACTSCHEMA.TELEPHONE)));

        //Customer custom fields
        List<Map<String, Object>> custom_list =
                (List<Map<String, Object>>) map.get(Constants.ZOHOCONTACTSCHEMA.CUSTOM_FIELDS);

        for (Map<String, Object> _map : custom_list){

            String placeholder = String.valueOf(_map.get(Constants.ZOHO.PLACEHOLDER).toString());
            if(placeholder.equals(Constants.ZOHOCONTACTSCHEMA.ZARIDNO_PLACE_HOLDER)){

                mCustomerZAID
                        .onNext(String.valueOf(_map.get(Constants.ZOHO.VALUE)));
            }
        }
    }

    public static BehaviorSubject<String> getCustomerIDSubject() {
        return mCustomerIDSubject;
    }

    public static BehaviorSubject<String> getCustomerNameSubject() {
        return mCustomerNameSubject;
    }

    public static PublishSubject<Double> getAmountOustandingSubject() {
        return mAmountOustandingSubject;
    }

    public static BehaviorSubject<String> getCustomerZAIDSubject() {
        return mCustomerZAID;
    }

    public static BehaviorSubject<String> getCustomerMobileSubject() {
        return mCustomerMobile;
    }

    public static BehaviorSubject<String> getCustomerTelephoneSubject() {
        return mCustomerTelephone;
    }

    public static PublishSubject<List<Map<String, Object>>> getCustomerInvoicesSubject() {
        return mCustomerInvoicesSubject;
    }

    public static PublishSubject<List<Map<String, Object>>> getCustomerPaymentsSubject() {
        return mCustomerPaymentsSubject;
    }

}
