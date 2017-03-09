package com.feerlaroc.mqasho.schema.tenant;

import android.content.Context;

import com.feerlaroc.mqasho.http.ServiceFactory;
import com.feerlaroc.mqasho.schema.BaseEntityFactory;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.mqasho.schema.items.ItemObservableFactory;
import com.feerlaroc.mqasho.schema.items.LineItemCreator;
import com.feerlaroc.utils.datetime.utils.FeerlarocDateUtils;
import com.feerlaroc.zoho.entity.HttpResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class TenantRecurringInvoiceHandler<T> {

    private RecurringInvoiceFactory mRecurringInvoiceFactory = new RecurringInvoiceFactory().initialize();

    private static BehaviorSubject<List<Map<String, Object>>> mLineItemSubject = BehaviorSubject.create();
    private static BehaviorSubject<String> mRecurringStartDateSubject  = BehaviorSubject.create();
    private static BehaviorSubject<String> mRecurringFrequencySubject  = BehaviorSubject.create("months");

    private Context mContext;

    public TenantRecurringInvoiceHandler(){

        configureObservables();
        setItem();
        createRecurringStartDate();
    }

    public void setItem(){

        ItemObservableFactory.getItemByName("Monthly Rental")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .forEach(itemSubscribe());
    }


    private Action1<HttpResult<List<Map<String, Object>>>> itemSubscribe() {

        return result -> {

            if(result.getCode() == 0){

                List<Map<String,Object>> list = new ArrayList<>();
                list.add(LineItemCreator.get(result.getData().get(0), 1d));
                mLineItemSubject
                        .onNext(list);
            }
        };
    }

    public Observable<HttpResult<Map<String, Object>>> saveInvoice(){

        return mRecurringInvoiceFactory.post();
    }

    private void configureObservables(){

        mRecurringInvoiceFactory.captureCustomerID(CustomerObservable.getCustomerIDSubject());
        mRecurringInvoiceFactory.captureRecurringName(CustomerObservable.getCustomerNameSubject());
        mRecurringInvoiceFactory.captureRecurringFrequency(mRecurringFrequencySubject);
        mRecurringInvoiceFactory.captureRecurringStartDate(mRecurringStartDateSubject);
        mRecurringInvoiceFactory.captureLineItem(mLineItemSubject);
    }

    private void createRecurringStartDate(){

        mRecurringStartDateSubject
                .onNext(FeerlarocDateUtils.getSimpleDate(Calendar.getInstance().getTimeInMillis()));
    }

    class RecurringInvoiceFactory extends BaseEntityFactory<RecurringInvoiceFactory> {

        @Override
        public Observable<HttpResult<Map<String, Object>>> post() {

            JSONObject _json = new JSONObject(mData);
            return ServiceFactory.invoiceApi()
                    .createRecurringInvoice(Constants.ZOHO.TOKEN, Constants.ZOHO.ORG_ID, _json);
        }

        @Override
        public RecurringInvoiceFactory initialize() {
            return this;
        }

        @Override
        public void clearData() {}

        public void captureCustomerID(Observable<String> observable){

            observable.subscribe(s -> {
                mData.put(Constants.INVOICESCHEMA.CUSTOMER_ID, s);
            });
        }

        public void captureRecurringName(Observable<String> observable){

            observable.subscribe(s -> {
                mData.put(Constants.INVOICESCHEMA.RECURRING_NAME, "Rec Invoice for " + s);
            });
        }

        public void captureRecurringStartDate(Observable<String> observable){

            observable.subscribe(s -> {
                mData.put(Constants.INVOICESCHEMA.RECURRING_START_DATE, s);
            });
        }

        public void captureRecurringFrequency(Observable<String> observable){

            observable.subscribe(s -> {
                mData.put(Constants.INVOICESCHEMA.RECURRING_FREQUENCY, s);
            });
        }

        public void captureLineItem(Observable<List<Map<String,Object>>> observable){

            observable.subscribe(list -> {
                mData.put(Constants.INVOICESCHEMA.LINEITEMS, list);
            });
        }
    }
}
