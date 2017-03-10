package com.feerlaroc.mqasho.schema.payment;

import android.content.Context;
import android.widget.Toast;

import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.mqasho.schema.receipt.PrinterResult;
import com.feerlaroc.mqasho.schema.receipt.ReceiptFactory;
import com.feerlaroc.mqasho.schema.tenant.CustomerObservable;
import com.feerlaroc.zoho.entity.HttpResult;
import com.feerlaroc.zoho.utils.DialogHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import timber.log.Timber;


public class PaymentHandler {

    PaymentFactory mPaymentFactory = new PaymentFactory().initialize();
    ReceiptFactory mReceiptFactory = new ReceiptFactory().initialize();

    static PublishSubject<String> mPaymentCustomerIDSubject         = PublishSubject.create();
    static PublishSubject<Double> mPaymentAmountSubject             = PublishSubject.create();
    static PublishSubject<String> mPaymentModeSubject               = PublishSubject.create();
    static PublishSubject<String> mPaymentDescriptionSubject        = PublishSubject.create();
    static PublishSubject<String> mPaymentReferenceNumberSubject    = PublishSubject.create();
    static PublishSubject<String> mPaymentExchageRateSubject        = PublishSubject.create();
    static PublishSubject<String> mPaymentDateSubject               = PublishSubject.create();
    static PublishSubject<List>   mPaymentInvoicesSubject           = PublishSubject.create();

    static PublishSubject<String> mReceiptCustomerNameSubject       = PublishSubject.create();
    static PublishSubject<String> mReceiptFiscalPeriodSubject       = PublishSubject.create();
    static PublishSubject<Double> mReceiptPaidAmountSubject         = PublishSubject.create();
    static PublishSubject<Double> mReceiptOutstandingAmountSubject  = PublishSubject.create();
    static PublishSubject<String> mReceiptPaymentDateSubject        = PublishSubject.create();

    static PublishSubject<Boolean> mPaymentResultSubject            = PublishSubject.create();

    private Context mContext;
    private Double mAppliedAmount;
    private String mInvoiceID;

    public PaymentHandler(Context context, Double amount, String invoice_id){

        mContext = context;
        mAppliedAmount = amount;
        mInvoiceID = invoice_id;
        configureObservables();
    }

    public PaymentHandler create() {

        if(mAppliedAmount <= 0)
            return this;

        mPaymentInvoicesSubject.onNext(getInvoiceList(mAppliedAmount));
        mPaymentModeSubject.onNext(Constants.ZOHOPAYMENTSCHEMA.MODE_CASH);
        mPaymentAmountSubject.onNext(mAppliedAmount);

        mPaymentFactory.post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult<Map<String, Object>>>() {
                    @Override
                    public void onCompleted() {

                        Toast.makeText(mContext,
                                "Payment saved.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(mContext,
                                "Failed to save payment.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(HttpResult<Map<String, Object>> result) {

                        Timber.i(String.valueOf(result.getMessage()));

                        if(result.getCode() == 0){

                            mReceiptPaymentDateSubject
                                    .onNext(String.valueOf(result.getData().get(Constants.ZOHOPAYMENTSCHEMA.PAYMENT_DATE)));
                            mReceiptPaidAmountSubject
                                    .onNext(Double.valueOf(result.getData().get(Constants.ZOHOPAYMENTSCHEMA.PAYMENT_AMOUNT).toString()));

                            CustomerObservable.getInstance().set(mContext, String.valueOf(result.getData().get(Constants.ZOHOPAYMENTSCHEMA.CUSTOMER_ID)));

                            printReceipt();

                        } else {    onError(new Throwable("Failed to save payment."));  }

                    }
                });

        return this;
    }

    private void printReceipt(){

        mReceiptFactory.print()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PrinterResult<String>>() {
                    @Override
                    public void onCompleted() {

                        DialogHelper.stopProgressDlg();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Timber.i("Failed to print receipt Error!");
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                        DialogHelper.stopProgressDlg();
                    }

                    @Override
                    public void onNext(PrinterResult<String> stringPrinterResult) {

                        Timber.i("Receipt has been printed!");
                    }
                });
    }

    private List<Map<String, Object>> getInvoiceList(Double _applied_amt) {

        List<Map<String, Object>> _invoiceList = new ArrayList<>();
        Map<String, Object> _invoiceToPay = new HashMap<>();

        _invoiceToPay.put(Constants.ZOHOPAYMENTSCHEMA.INVOICE_ID, mInvoiceID);
        _invoiceToPay.put(Constants.ZOHOPAYMENTSCHEMA.TAX_AMOUNT_WITHHELD, 0d);
        _invoiceToPay.put(Constants.ZOHOPAYMENTSCHEMA.AMOUNT_APPLIED_TO_INVOICE, _applied_amt);
        _invoiceList.add(_invoiceToPay);

        return _invoiceList;
    }

    private void configureObservables(){

        mPaymentFactory.captureCustomerID(CustomerObservable.getCustomerIDSubject());
        mPaymentFactory.capturePaymentMode(mPaymentModeSubject);
        mPaymentFactory.capturePaymentAmount(mPaymentAmountSubject);
        mPaymentFactory.capturePaymentDescription(mPaymentDescriptionSubject);
        mPaymentFactory.capturePaymentReferenceNumber(mPaymentReferenceNumberSubject);
        mPaymentFactory.capturePaymentDate(mPaymentDateSubject);
        mPaymentFactory.captureExchangeRate(mPaymentExchageRateSubject);
        mPaymentFactory.captureInvoiceList(mPaymentInvoicesSubject);

        mReceiptFactory.captureCustomerName(CustomerObservable.getCustomerNameSubject());
        mReceiptFactory.captureFiscalPeriod(mReceiptFiscalPeriodSubject);
        mReceiptFactory.capturePaidAmount(mReceiptPaidAmountSubject);
        mReceiptFactory.captureOutstandingAmount(CustomerObservable.getAmountOustandingSubject());
        mReceiptFactory.capturePaymentDate(mReceiptPaymentDateSubject);
    }

    public PublishSubject<Boolean> getPaymentResultSubject(){

        return mPaymentResultSubject;
    }
}
