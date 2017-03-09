package com.feerlaroc.mqasho.schema.items;


import com.feerlaroc.mqasho.schema.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 2017/01/20.
 */

public class LineItemCreator {


    public static Map<String, Object> get(Map<String, Object> itemMap, Double quantity){

        Map<String, Object> item = new HashMap<>();

        List<String> _list = new ArrayList<>();
        _list.add(Constants.ZOHOITEMSCHEMA.ITEM_ID);
        _list.add(Constants.ZOHOITEMSCHEMA.ITEM_NAME);
        _list.add(Constants.ZOHOITEMSCHEMA.DESCRIPTION);
        _list.add(Constants.ZOHOITEMSCHEMA.RATE);

        for (Map.Entry<String, Object> entry : itemMap.entrySet()) {

            String search = entry.getKey();
            for(String str: _list) {

                if(str.trim().contains(search)) {

                    item.put(entry.getKey(), entry.getValue());
                }
            }
        }

        item.put(Constants.ZOHOLINEITEMSCHEMA.QUANTITY, quantity);


        return item;
    }
}
