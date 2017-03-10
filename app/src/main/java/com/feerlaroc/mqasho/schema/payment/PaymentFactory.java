package com.feerlaroc.mqasho.schema.payment;

import com.feerlaroc.mqasho.http.ServiceFactory;
import com.feerlaroc.mqasho.schema.BaseEntityFactory;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.utils.datetime.utils.FeerlarocDateUtils;
import com.feerlaroc.zoho.entity.HttpResult;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;


public class PaymentFactory extends BaseEntityFactory<PaymentFactory> {

    public PaymentFactory(){

    }

    @Override
    public Observable<HttpResult<Map<String, Object>>> post(){

        JSONObject _json = new JSONObject(mData);
        return ServiceFactory.paymentApi()
                .createPaymentObservable(Constants.ZOHO.TOKEN, Constants.ZOHO.ORG_ID, _json);
    }

    @Override
    public PaymentFactory initialize(){

        clearData();

        mData.put(Constants.ZOHOPAYMENTSCHEMA.PAYMENT_DATE,
                FeerlarocDateUtils.getSimpleDate(Calendar.getInstance().getTimeInMillis()));

        return this;
    }

    @Override
    public void clearData() {

        mData = new HashMap<>();
    }

    public void captureCustomerID(Observable<String> observable) {

        observable.subscribe(s -> {
            mData.put(Constants.ZOHOPAYMENTSCHEMA.CUSTOMER_ID, s);
        });
    }

    public void capturePaymentMode(Observable<String> observable) {

        observable.subscribe(s -> {
            mData.put(Constants.ZOHOPAYMENTSCHEMA.PAYMENT_MODE, s);
        });
    }

    public void capturePaymentAmount(Observable<Double> observable) {

        observable.subscribe(s -> {
            mData.put(Constants.ZOHOPAYMENTSCHEMA.PAYMENT_AMOUNT, s);
        });
    }

    public void capturePaymentDescription(Observable<String> observable) {

        observable.subscribe(s -> {
            mData.put(Constants.ZOHOPAYMENTSCHEMA.DESCRIPTION, s);
        });
    }

    public void capturePaymentReferenceNumber(Observable<String> observable) {

        observable.subscribe(s -> {
            mData.put(Constants.ZOHOPAYMENTSCHEMA.REFERENCE_NUMBER, s);
        });
    }

    public void capturePaymentDate(Observable<String> observable) {

        observable.subscribe(s -> {
            mData.put(Constants.ZOHOPAYMENTSCHEMA.PAYMENT_DATE, s);
        });
    }

    public void captureExchangeRate(Observable<String> observable) {

        observable.subscribe(s -> {
            mData.put(Constants.ZOHOPAYMENTSCHEMA.EXCHANGE_RATE, s);
        });
    }

    public void captureInvoiceList(Observable<List> observable) {

        observable.subscribe(s -> {
            mData.put(Constants.ZOHOPAYMENTSCHEMA.INVOICES, s);
        });
    }
}
