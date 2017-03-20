package com.feerlaroc.mqasho.schema.tenant;


import com.feerlaroc.mqasho.http.ServiceFactory;
import com.feerlaroc.mqasho.schema.BaseEntityFactory;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.zoho.entity.HttpResult;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

public class CustomerFactory extends BaseEntityFactory<CustomerFactory> {

    //Create zoho contact person for customer
    private Map<String, Object> mContactPerson = new HashMap<>();
    private Map<String, Object> mCustomFields = new HashMap<>();


    @Override
    public Observable<HttpResult<Map<String, Object>>> post() {

        //Update contact list.
        List<Map<String, Object>> _list = new ArrayList<>();
        _list.add(mContactPerson);
        mData.put(Constants.ZOHOCONTACTSCHEMA.CONTACT_PERSONS, _list);

        //Make api customer post call.
        JSONObject _json = new JSONObject(mData);
        return ServiceFactory.customerApi()
                .createCustomer(Constants.ZOHO.TOKEN, Constants.ZOHO.ORG_ID, _json);
    }

    @Override
    public CustomerFactory initialize() {

        return this;
    }

    public void captureCustomerName(Observable<String> observable){

        observable.subscribe(s -> {
            int i = s.indexOf(" "); // 4

            String _name = StringUtils.capitalize(s.substring(0, i)); // from 0 to the appearance of the space
            String _surname = StringUtils.capitalize(s.substring(i+1)); // after the space to the rest of the line

            mContactPerson.put(Constants.ZOHOCONTACTSCHEMA.FIRST_NAME, _name);
            mContactPerson.put(Constants.ZOHOCONTACTSCHEMA.LAST_NAME, _surname);

            mData.put(Constants.ZOHOCONTACTSCHEMA.CUSTOMER_NAME, capsFirst(s.toLowerCase()));
            mData.put(Constants.ZOHOCONTACTSCHEMA.COMPANY_NAME, capsFirst(s.toLowerCase()));
        });
    }

    String capsFirst(String str) {
        String[] words = str.split(" ");
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < words.length; i++) {
            ret.append(Character.toUpperCase(words[i].charAt(0)));
            ret.append(words[i].substring(1));
            if(i < words.length - 1) {
                ret.append(' ');
            }
        }
        return ret.toString();
    }

    public void captureCustomerZAID(Observable<String> observable){

        observable.subscribe(s -> {

            Map<String, Object> _map = new HashMap<>();
            _map.put(Constants.ZOHO.INDEX, 4d);
            _map.put(Constants.ZOHO.VALUE, s);

            List<Map<String, Object>> _list = new ArrayList<>();
            _list.add(_map);

            mCustomFields.put(Constants.ZOHOCONTACTSCHEMA.CUSTOM_FIELDS, _list);
        });
    }

    public void captureCustomerMobile(Observable<String> observable){

        observable.subscribe(s -> {
            mData.put(Constants.ZOHOCONTACTSCHEMA.MOBILE, s);

            mContactPerson.put(Constants.ZOHOCONTACTSCHEMA.MOBILE, s);
        });
    }

    public void captureCustomerTelephone(Observable<String> observable){

        observable.subscribe(s -> {
            mData.put(Constants.ZOHOCONTACTSCHEMA.TELEPHONE, s);
        });
    }

    public void captureCustomerGender(Observable<String> observable){

        observable.subscribe(s -> {

            Map<String, Object> _map = new HashMap<>();
            _map.put(Constants.ZOHO.INDEX, 5d);
            _map.put(Constants.ZOHO.VALUE, s);

            List<Map<String, Object>> _list = new ArrayList<>();
            _list.add(_map);

            mCustomFields.put(Constants.ZOHOCONTACTSCHEMA.CUSTOM_FIELDS, _list);
        });
    }

    public void captureCustomerSite(Observable<String> observable){

        observable.subscribe(s -> {

            Map<String, Object> _map = new HashMap<>();
            _map.put(Constants.ZOHO.INDEX, 6d);
            _map.put(Constants.ZOHO.VALUE, s);

            List<Map<String, Object>> _list = new ArrayList<>();
            _list.add(_map);

            mCustomFields.put(Constants.ZOHOCONTACTSCHEMA.CUSTOM_FIELDS, _list);
        });
    }


    @Override
    public void clearData() {}
}
