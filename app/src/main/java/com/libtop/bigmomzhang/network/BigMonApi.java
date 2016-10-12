package com.libtop.bigmomzhang.network;

import com.libtop.bigmomzhang.bean.DetailBean;
import com.libtop.bigmomzhang.bean.SearchBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * Created by LianTu on 2016-9-29.
 */

public interface BigMonApi
{
    @GET("https://api.smzdm.com/v1/list?keyword=笔记本&type=home&category_id=&brand_id=&mall_id=&order=score&day=&limit=20&offset=0&f=android&s=A7IepH35JcdbNwexZRT0dAaTrg3RrElV&v=320&weixin=0")
    Observable<SearchBean> getCollect();
//    @GET("https://api.smzdm.com/v1/list?keyword=笔记本&type=home&category_id=&brand_id=&mall_id=&order=score&day=&limit=20&offset=0&f=android&s=A7IepH35JcdbNwexZRT0dAaTrg3RrElV&v=320&weixin=0")
    @Headers("Cache-Control: public, max-age=60")
    @GET("{type}/{method}")
    Observable<SearchBean> getSearchList(@Path("type") String type, @Path("method") String method, @QueryMap Map<String,String> options);
    @Headers("Cache-Control: public, max-age=3600")
    @GET("v2/youhui/articles/{value}")
    Observable<DetailBean> getDetial(@Path("value") String value, @QueryMap Map<String,String> options);

}
