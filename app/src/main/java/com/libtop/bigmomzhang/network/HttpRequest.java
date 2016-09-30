package com.libtop.bigmomzhang.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by LianTu on 2016-9-29.
 */

public class HttpRequest
{

    private static BigMonApi bigMonApi;
    private static OkHttpClient okHttpClient;
    final static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create(gson);
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    public static BigMonApi getBigMonApi()
    {
        if (bigMonApi == null)
        {
            int cacheSize = 100 * 1024 * 1024;
//            Cache cache = new Cache(App.getInstance().getCacheDir(), cacheSize);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS).addInterceptor(logging).build();
            Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(APIAddress.BASE).addConverterFactory(gsonConverterFactory).addCallAdapterFactory(rxJavaCallAdapterFactory).build();
            bigMonApi = retrofit.create(BigMonApi.class);
        }
        return bigMonApi;
    }


}
