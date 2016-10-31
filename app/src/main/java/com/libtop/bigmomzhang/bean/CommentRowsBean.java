package com.libtop.bigmomzhang.bean;

import com.libtop.bigmomzhang.utils.StringUtils;

import java.util.List;


/**
 * Created by LianTu on 2016-10-20.
 */

public class CommentRowsBean
{
    public String comment_ID;
    public String user_id;
    public String user_smzdm_id;
    public String comment_author;
    private String comment_content;
    public String comment_parent;
    public String comment_parent_ids;
    public String support_count;
    public String oppose_count;
    public String comment_date;
    public String format_date;
    public String format_date_client;
    public String ding_class;
    public String post_author;
    public boolean has_christmas_hat;
    public String floor;
    public String head;
    public String level;
    public String level_logo;
    public String official;
    public String have_current_user;
    public String is_anonymous;
    public String from_client;
    public String from_client_version;
    public String from_client_uri;
    public String from_client_suff;
    /**
     * img : http://res.smzdm.com/images/user_logo/signin201609.png
     * desc : 5周年纪念徽章
     */

    public List<MedalsBean> medals;
    public List<ParentDataBean> parent_data;


    public String getComment_content()
    {
        return StringUtils.getString(comment_content);
    }


    public void setComment_content(String comment_content)
    {
        this.comment_content = comment_content;
    }
}
