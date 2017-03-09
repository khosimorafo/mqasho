package com.feerlaroc.mqasho.schema;


import com.feerlaroc.zoho.entity.HttpResult;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by root on 2017/01/16.
 */

public abstract class BaseEntityFactory<T> {

    public Map<String, Object> mData = new HashMap<>();

    public abstract Observable<HttpResult<Map<String, Object>>> post();

    public abstract T initialize();
    public abstract void clearData();

    public Map<String, Object> getData() {
        return mData;
    }

    public void setData(Map<String, Object> mData) {
        this.mData = mData;
    }
}
