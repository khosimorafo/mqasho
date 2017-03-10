package com.feerlaroc.mqasho.schema.receipt;


import com.feerlaroc.mqasho.schema.Constants;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public class ReceiptFactory{

    public static Map<String, Object> mData = new HashMap<>();

    public ReceiptFactory initialize() {

        mData = new HashMap<>();
        return this;
    }

    public Observable<PrinterResult<String>> print(){

        return PrinterInjector.receiptApi(null).print(mData);
    }

    public void captureCustomerName(Observable<String> observable){

        observable.subscribe(s -> {
            mData.put(Constants.CUSTOMERSCHEMA.NAME, s);
        });
    }

    public void captureFiscalPeriod(Observable<String> observable){

        observable.subscribe(s -> {
            mData.put(Constants.FEERLAROC.FISCAL_PERIOD, s);
        });
    }

    public void capturePaidAmount(Observable<Double> observable){

        observable.subscribe(s -> {
            mData.put(Constants.PAYMENTSCHEMA.AMOUNT_PAID, String.valueOf(s));
        });
    }

    public void captureOutstandingAmount(Observable<Double> observable){

        observable.subscribe(s -> {
            mData.put(Constants.CUSTOMERSCHEMA.OUTSTANDING_AMOUNT, String.valueOf(s));
        });
    }

    public void capturePaymentDate(Observable<String> observable){

        observable.subscribe(s -> {
            mData.put(Constants.PAYMENTSCHEMA.PAYMENT_DATE, s);
        });
    }
}
