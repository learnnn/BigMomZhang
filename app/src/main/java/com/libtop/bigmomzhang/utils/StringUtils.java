package com.libtop.bigmomzhang.utils;

/**
 * Created by LianTu on 2016-9-30.
 */

public class StringUtils
{
    public static boolean isEmpty(String str)
    {
        return (str == null || str.length() == 0);
    }

    public static String getString(Object object)
    {
        return (object != null ? object.toString() : "");
    }

    public static String getCoverUrl(String url)
    {
        if (url == null || url.trim().equals("")){
            url = "http://";
        }
        return url;
    }
}
