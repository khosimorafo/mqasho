package com.feerlaroc.mqasho.schema.tenant;

import com.feerlaroc.mqasho.http.ServiceFactory;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.zoho.entity.HttpResult;

import java.util.List;
import java.util.Map;

import rx.Observable;


public class CustomerObservableFactory {

    public static Observable<HttpResult<Map<String, Object>>> getCustomer(String id){

        return ServiceFactory
                .customerApi()
                .getSingleCustomer(id, Constants.ZOHO.TOKEN, Constants.ZOHO.ORG_ID);
    }

    public static Observable<HttpResult<List<Map<String, Object>>>> getCustomerInvoices(String id){

        return ServiceFactory.invoiceApi()
                .getInvoiceByCustomerID(Constants.ZOHO.TOKEN, Constants.ZOHO.ORG_ID, id);
    }

    public static Observable<HttpResult<List<Map<String, Object>>>> getCustomerPayments(String id){

        return ServiceFactory.paymentApi()
                .getPaymentsByCustomerID(Constants.ZOHO.TOKEN, Constants.ZOHO.ORG_ID, id);
    }
}
