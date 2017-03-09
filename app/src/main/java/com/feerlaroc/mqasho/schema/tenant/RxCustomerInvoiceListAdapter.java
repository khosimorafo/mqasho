package com.feerlaroc.mqasho.schema.tenant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.feerlaroc.mqasho.R;
import com.feerlaroc.mqasho.http.ServiceFactory;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.utils.datetime.utils.FeerlarocDateUtils;
import com.feerlaroc.utils.fiscal.FiscalDate;
import com.feerlaroc.widgets.view.PinItem;
import com.feerlaroc.zoho.rx.RxZohoListAdapter;
import com.feerlaroc.zoho.transformer.DefaultTransformer;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by root on 2017/01/14.
 */

public class RxCustomerInvoiceListAdapter extends RxZohoListAdapter {

    public RxCustomerInvoiceListAdapter(Context context, int resource) {

        super(context, resource);
    }

    @Override
    public Observable<List<Map<String, Object>>> getObservable(String entityID) {

        return ServiceFactory.invoiceApi()
                .getInvoiceByCustomerID(Constants.ZOHO.TOKEN, Constants.ZOHO.ORG_ID, entityID)
                .compose(new DefaultTransformer<>());
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PinItem _pinItem = new PinItem(getContext());

        if(position >= 4)
            return new View(getContext());

        final int _parentWidth = parent.getMeasuredWidth()
                - parent.getPaddingLeft() - parent.getPaddingRight();

        final int _itemWidth = _parentWidth/4;

        // make view a square
        _pinItem.setMinimumWidth(_itemWidth);
        _pinItem.setMinimumHeight(_itemWidth);
        _pinItem.setPadding(5,5,5,5);

        Map<String, Object> _map = (Map<String, Object>) getItem(position);

        String _status = String.valueOf(_map.get(Constants.ZOHOINVOICESCHEMA.STATUS));

        switch(_status) {

            case Constants.STATUS.PAID:
                _pinItem.setDrawable(R.drawable.ic_money_paid_24);
                _pinItem.setBottomText(String.valueOf(_map.get(Constants.ZOHOINVOICESCHEMA.INVOICE_NUMBER)));
                break;
            case Constants.STATUS.UNPAID:
                _pinItem.setDrawable(R.drawable.ic_unpaid_24);
                _pinItem.setBottomText(String.valueOf(_map.get(Constants.ZOHOINVOICESCHEMA.INVOICE_NUMBER)));
                break;
            case Constants.STATUS.OVERDUE:
                _pinItem.setDrawable(R.drawable.ic_time_span_24);
                _pinItem.setBottomText(String.valueOf(_map.get(Constants.ZOHOINVOICESCHEMA.INVOICE_NUMBER)));
                break;
            case Constants.STATUS.DRAFT:
            default:
                _pinItem.setDrawable(R.drawable.ic_unpaid_24);
                break;
        }

        String _due_date = String.valueOf(_map.get(Constants.ZOHOINVOICESCHEMA.DUE_DATE));
        long _date = FeerlarocDateUtils.timestampToMillis(_due_date, Calendar.getInstance().getTimeInMillis());
        String _period = new FiscalDate(_date).getPeriodShort();

        _pinItem.setTopText(_period);

        return _pinItem;
    }
}
