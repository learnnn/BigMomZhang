package com.libtop.bigmomzhang.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.libtop.bigmomzhang.App;
import com.libtop.bigmomzhang.bean.ArticleOrderScreenshotBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
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
    private final static Type articleOrderScreenshotListType = new TypeToken<List<ArticleOrderScreenshotBean>>() {}.getType();
    final static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .registerTypeAdapter(articleOrderScreenshotListType, new ArticleOrderScreenshotTypeAdapter())
            .serializeNulls()
            .create();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create(gson);
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    public static BigMonApi getBigMonApi()
    {
        if (bigMonApi == null)
        {
            int cacheSize = 100 * 1024 * 1024;
            Cache cache = new Cache(App.getInstance().getCacheDir(), cacheSize);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS).addInterceptor(logging).cache(cache).build();
            Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(APIAddress.BASE).addConverterFactory(gsonConverterFactory).addCallAdapterFactory(rxJavaCallAdapterFactory).build();
            bigMonApi = retrofit.create(BigMonApi.class);
        }
        return bigMonApi;
    }

    private static class ArticleOrderScreenshotTypeAdapter implements JsonDeserializer<List<ArticleOrderScreenshotBean>>
    {
        public List<ArticleOrderScreenshotBean> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) {
            List<ArticleOrderScreenshotBean> vals = new ArrayList<ArticleOrderScreenshotBean>();
            if (json.isJsonArray()) {
                for (JsonElement e : json.getAsJsonArray()) {
                    vals.add((ArticleOrderScreenshotBean) ctx.deserialize(e, ArticleOrderScreenshotBean.class));
                }
            } else if (json.isJsonObject()) {
                vals.add((ArticleOrderScreenshotBean) ctx.deserialize(json, ArticleOrderScreenshotBean.class));
            } else {
                throw new RuntimeException("Unexpected JSON type: " + json.getClass());
            }
            return vals;
        }
    }


}
