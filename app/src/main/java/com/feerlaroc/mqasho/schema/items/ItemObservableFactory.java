package com.feerlaroc.mqasho.schema.items;


import com.feerlaroc.mqasho.http.ServiceFactory;
import com.feerlaroc.mqasho.schema.Constants;
import com.feerlaroc.zoho.entity.HttpResult;

import java.util.List;
import java.util.Map;

import rx.Observable;

public class ItemObservableFactory {

    public static Observable<HttpResult<List<Map<String, Object>>>> getItemByName(String id){

        return ServiceFactory.itemApi()
                .getItemByName(Constants.ZOHO.TOKEN, Constants.ZOHO.ORG_ID, id);
    }
}
