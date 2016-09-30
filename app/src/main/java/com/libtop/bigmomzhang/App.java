package com.libtop.bigmomzhang;

import android.app.Application;
import android.content.Context;


/**
 * Created by LianTu on 2016-9-29.
 */

public class App extends Application
{
    private static App mInstance;
    private static Context context;

    public synchronized static App getInstance()
    {
        return mInstance;
    }

    public static Context getContext()
    {
        return context;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        mInstance = this;
        context = getApplicationContext();
    }
}
