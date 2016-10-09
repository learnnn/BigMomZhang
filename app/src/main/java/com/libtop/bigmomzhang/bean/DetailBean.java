package com.libtop.bigmomzhang.bean;

import android.support.annotation.Nullable;

import com.libtop.bigmomzhang.utils.StringUtils;

import java.util.List;


/**
 * Created by LianTu on 2016-10-8.
 */

public class DetailBean extends BaseBean
{


    public ResultBean data;


    public class  ResultBean
    {
        private String article_id;
        private String article_title;
        private String article_price;
        private String article_format_date;
        /**
         * link : http://go.smzdm.com/e2c97d4323b4b05d/ce_aa_yh_163_6454205_135_1681_179?type=default
         * link_type : other
         * sub_type : jd
         * link_val : http://go.smzdm.com/e2c97d4323b4b05d/ce_aa_yh_163_6454205_135_1681_179?type=default
         * link_title :
         * isv_code_second : yh_163
         */

        private RedirectDataBean redirect_data;
        private String article_pic;
        private String article_mall;
        private String article_content;
        private String article_bl_reason;
        private String product_intro;
        /**
         * pic : http://y.zdmimg.com/201609/26/57e8ad187ecb36190.jpg
         * width : 513
         * height : 195
         */

        @Nullable
        public List<ArticleOrderScreenshotBean> article_order_screenshot;


        public String getProduct_intro()
        {
            return StringUtils.getString(product_intro);
        }


        public void setProduct_intro(String product_intro)
        {
            this.product_intro = product_intro;
        }


        public String getArticle_id()
        {
            return StringUtils.getString(article_id);
        }


        public void setArticle_id(String article_id)
        {
            this.article_id = article_id;
        }


        public String getArticle_title()
        {
            return StringUtils.getString(article_title);
        }


        public void setArticle_title(String article_title)
        {
            this.article_title = article_title;
        }


        public String getArticle_price()
        {
            return StringUtils.getString(article_price);
        }


        public void setArticle_price(String article_price)
        {
            this.article_price = article_price;
        }


        public String getArticle_format_date()
        {
            return StringUtils.getString(article_format_date);
        }


        public void setArticle_format_date(String article_format_date)
        {
            this.article_format_date = article_format_date;
        }


        public RedirectDataBean getRedirect_data()
        {
            return redirect_data;
        }


        public void setRedirect_data(RedirectDataBean redirect_data)
        {
            this.redirect_data = redirect_data;
        }


        public String getArticle_pic()
        {
            return StringUtils.getCoverUrl(article_pic);
        }


        public void setArticle_pic(String article_pic)
        {
            this.article_pic = article_pic;
        }


        public String getArticle_mall()
        {
            return StringUtils.getString(article_mall);
        }


        public void setArticle_mall(String article_mall)
        {
            this.article_mall = article_mall;
        }


        public String getArticle_content()
        {
            return StringUtils.getString(article_content);
        }


        public void setArticle_content(String article_content)
        {
            this.article_content = article_content;
        }


        public String getArticle_bl_reason()
        {
            return StringUtils.getString(article_bl_reason);
        }


        public void setArticle_bl_reason(String article_bl_reason)
        {
            this.article_bl_reason = article_bl_reason;
        }


    }


}
