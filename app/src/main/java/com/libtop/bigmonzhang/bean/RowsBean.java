package com.libtop.bigmonzhang.bean;

/**
 * Created by LianTu on 2016-9-29.
 */

public  class RowsBean
{
    public String article_channel_id;
    public String article_channel_name;
    public String article_id;
    public String article_url;
    public String article_title;
    public String article_price;
    public String article_format_date;
    public String article_pic;
    public String article_collection;
    public String article_comment;
    public String article_referrals;
    public String article_type_id;
    public String article_type_name;
    public boolean article_anonymous;
    public String article_mall;
    public String article_worthy;
    public String article_unworthy;
    public String article_status_name;
    public String article_is_sold_out;
    public String article_is_timeout;
    public String sync_home;
    /**
     * link : http://www.smzdm.com/p/6465165
     * link_type : faxian
     * sub_type : detail
     * link_val : 6465165
     * link_title :
     * isv_code_second :
     */

    public RedirectDataBean redirect_data;


    public static class RedirectDataBean
    {
        public String link;
        public String link_type;
        public String sub_type;
        public String link_val;
        public String link_title;
        public String isv_code_second;
    }
}
