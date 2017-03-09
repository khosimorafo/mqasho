package com.feerlaroc.mqasho.schema.invoice;


import com.feerlaroc.mqasho.http.ServiceFactory;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.zoho.transformer.DefaultTransformer;

import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by root on 2016/12/21.
 */

public class InvoiceObservable {

    static PublishSubject<String> mInvoiceIDSubject = PublishSubject.create();
    static PublishSubject<String> mInvoiceNumberSubject = PublishSubject.create();
    static PublishSubject<Double> mAmountOustandingSubject = PublishSubject.create();

    private static final InvoiceObservable holder = new InvoiceObservable();
    public static InvoiceObservable getInstance() { return holder;}

    String mInvoiceID = "";

    public void setInvoiceID(String invoiceID){

        mInvoiceID = invoiceID;
        mInvoiceIDSubject.onNext(invoiceID);

        ServiceFactory
                .invoiceApi()
                .getSingleInvoice(invoiceID, Constants.ZOHO.TOKEN, Constants.ZOHO.ORG_ID)
                .compose(new DefaultTransformer<>())
                .subscribe(new Action1<Map<String, Object>>() {
                    @Override
                    public void call(Map<String, Object> map) {

                        mInvoiceIDSubject
                                .onNext(map.get(Constants.ZOHOINVOICESCHEMA.INVOICE_ID).toString());
                        mInvoiceNumberSubject
                                .onNext(map.get(Constants.ZOHOINVOICESCHEMA.INVOICE_NUMBER).toString());
                        Double _outstanding =
                                Double.valueOf(map.get(Constants.ZOHOINVOICESCHEMA.OUTSTANDING_AMOUNT).toString());
                        mAmountOustandingSubject
                                .onNext(_outstanding);
                    }
                });
    }

    public Observable<String> getInvoiceIDObservable() {

        return Observable.just(mInvoiceID);
    }

    public static PublishSubject<String> getInvoiceNumberSubject() {

        return mInvoiceNumberSubject;
    }

    public static PublishSubject<Double> getAmountOustandingSubject() {
        return mAmountOustandingSubject;
    }

}
