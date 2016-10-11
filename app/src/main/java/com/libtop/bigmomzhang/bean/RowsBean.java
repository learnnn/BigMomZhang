package com.libtop.bigmomzhang.bean;

import com.libtop.bigmomzhang.utils.StringUtils;


/**
 * Created by LianTu on 2016-9-29.
 */

public   class RowsBean
{
    private String article_channel_id;
    private String article_channel_name;
    private String article_id;
    private String article_url;
    private String article_title;
    private String article_price;
    private String article_format_date;
    private String article_pic;
    private String article_collection;
    private String article_comment;
    private String article_referrals;
    private String article_type_id;
    private String article_type_name;
    private boolean article_anonymous;
    private String article_mall;
    private String article_worthy;
    private String article_unworthy;
    private String article_status_name;
    private String article_is_sold_out;
    private String article_is_timeout;
    private String sync_home;
    private int worthy;
    /**
     * link : http://www.smzdm.com/p/6465165
     * link_type : faxian
     * sub_type : detail
     * link_val : 6465165
     * link_title :
     * isv_code_second :
     */

    private RedirectDataBean redirect_data;

    public int getWorthy()
    {
        int all = getArticle_unworthy()+getArticle_unworthy();
        if (all>0){
            return (int) (getArticle_worthy() / (float) (getArticle_worthy() + getArticle_unworthy()) * 100);
        }else {
            return 0;
        }
    }


    public String getArticle_channel_id()
    {
        return article_channel_id;
    }


    public void setArticle_channel_id(String article_channel_id)
    {
        this.article_channel_id = article_channel_id;
    }


    public String getArticle_channel_name()
    {
        return article_channel_name;
    }


    public void setArticle_channel_name(String article_channel_name)
    {
        this.article_channel_name = article_channel_name;
    }


    public String getArticle_id()
    {
        return article_id;
    }


    public void setArticle_id(String article_id)
    {
        this.article_id = article_id;
    }


    public String getArticle_url()
    {
        return article_url;
    }


    public void setArticle_url(String article_url)
    {
        this.article_url = article_url;
    }


    public String getArticle_title()
    {
        return article_title;
    }


    public void setArticle_title(String article_title)
    {
        this.article_title = article_title;
    }


    public String getArticle_price()
    {
        return article_price;
    }


    public void setArticle_price(String article_price)
    {
        this.article_price = article_price;
    }


    public String getArticle_format_date()
    {
        return article_format_date;
    }


    public void setArticle_format_date(String article_format_date)
    {
        this.article_format_date = article_format_date;
    }


    public String getArticle_pic()
    {
        return article_pic;
    }


    public void setArticle_pic(String article_pic)
    {
        this.article_pic = article_pic;
    }


    public String getArticle_collection()
    {
        return article_collection;
    }


    public void setArticle_collection(String article_collection)
    {
        this.article_collection = article_collection;
    }


    public String getArticle_comment()
    {
        return article_comment;
    }


    public void setArticle_comment(String article_comment)
    {
        this.article_comment = article_comment;
    }


    public String getArticle_referrals()
    {
        return article_referrals;
    }


    public void setArticle_referrals(String article_referrals)
    {
        this.article_referrals = article_referrals;
    }


    public String getArticle_type_id()
    {
        return article_type_id;
    }


    public void setArticle_type_id(String article_type_id)
    {
        this.article_type_id = article_type_id;
    }


    public String getArticle_type_name()
    {
        return article_type_name;
    }


    public void setArticle_type_name(String article_type_name)
    {
        this.article_type_name = article_type_name;
    }


    public boolean isArticle_anonymous()
    {
        return article_anonymous;
    }


    public void setArticle_anonymous(boolean article_anonymous)
    {
        this.article_anonymous = article_anonymous;
    }


    public String getArticle_mall()
    {
        return StringUtils.getString(article_mall);
    }


    public void setArticle_mall(String article_mall)
    {
        this.article_mall = article_mall;
    }


    public int getArticle_worthy()
    {
        if (StringUtils.isEmpty(article_worthy)){
            return 0;
        }else {
            return Integer.valueOf(article_worthy);
        }
    }


    public void setArticle_worthy(String article_worthy)
    {
        this.article_worthy = article_worthy;
    }


    public int getArticle_unworthy()
    {
        if (StringUtils.isEmpty(article_unworthy)){
            return 0;
        }else {
            return Integer.valueOf(article_unworthy);
        }
    }


    public void setArticle_unworthy(String article_unworthy)
    {
        this.article_unworthy = article_unworthy;
    }


    public String getArticle_status_name()
    {
        return article_status_name;
    }


    public void setArticle_status_name(String article_status_name)
    {
        this.article_status_name = article_status_name;
    }


    public String getArticle_is_sold_out()
    {
        return article_is_sold_out;
    }


    public void setArticle_is_sold_out(String article_is_sold_out)
    {
        this.article_is_sold_out = article_is_sold_out;
    }


    public String getArticle_is_timeout()
    {
        return article_is_timeout;
    }


    public void setArticle_is_timeout(String article_is_timeout)
    {
        this.article_is_timeout = article_is_timeout;
    }


    public String getSync_home()
    {
        return sync_home;
    }


    public void setSync_home(String sync_home)
    {
        this.sync_home = sync_home;
    }


    public RedirectDataBean getRedirect_data()
    {
        return redirect_data;
    }


    public void setRedirect_data(RedirectDataBean redirect_data)
    {
        this.redirect_data = redirect_data;
    }
}
