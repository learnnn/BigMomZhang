package com.libtop.bigmomzhang.network;

import com.libtop.bigmomzhang.bean.SearchBean;

import retrofit2.http.GET;
import rx.Observable;


/**
 * Created by LianTu on 2016-9-29.
 */

public interface BigMonApi
{
    @GET("https://api.smzdm.com/v1/list?keyword=笔记本&type=home&category_id=&brand_id=&mall_id=&order=score&day=&limit=20&offset=0&f=android&s=A7IepH35JcdbNwexZRT0dAaTrg3RrElV&v=320&weixin=0")
    Observable<SearchBean> getCollect();

}
