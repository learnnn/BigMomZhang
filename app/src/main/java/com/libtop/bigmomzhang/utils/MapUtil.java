package com.libtop.bigmomzhang.utils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by LianTu on 2016-9-30.
 */

public class MapUtil
{
    public static Map<String,String> mapObject2String(Map<String, Object> map)
    {
        if (map==null)
            return null;
        Map<String,String> map1 = new HashMap<>();
        for (String key : map.keySet()){
            Object object = map.get(key);
            map1.put(key,String.valueOf(object));
        }
        return  map1;
    }
}
