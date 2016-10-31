package com.libtop.bigmomzhang.bean;

import java.util.List;


/**
 * Created by LianTu on 2016-10-20.
 */

public class CommentBean extends BaseBean
{

    public CommentDataBean data;




    public class CommentDataBean
    {
        /**
         * comment_ID : 75270470
         * user_id : 4854957
         * user_smzdm_id : 7286891365
         * comment_author : 程年旧事
         * comment_content : 程序员，只敲代码，偶尔看看电影，美剧，不玩游戏，合适吗，各位？
         * comment_parent : 0
         * comment_parent_ids :
         * support_count : 0
         * oppose_count : 0
         * comment_date : 2016-10-19 23:00:27
         * format_date : 17小时前
         * format_date_client : 10-19
         * ding_class :
         * post_author :
         * has_christmas_hat : false
         * floor : 43楼
         * head : http://avatarimg.smzdm.com/default/7286891365/56cc2aa3c31c1-big.jpg
         * level : 29
         * level_logo : <a title="双11签到月" target="_blank" href="http://news.smzdm.com/p/28397/" ><i class="icon-signin201609" title="双11签到月"></i></a><div class="rank face-stuff-level" title="29级"><a href="http://zhiyou.smzdm.com/user/tequan/" target="_blank"><i class="icon-biaoqing-sun"></i><i class="icon-biaoqing-moon"></i><i class="icon-biaoqing-moon"></i><i class="icon-biaoqing-moon"></i><i class="icon-biaoqing-star"></i></a></div>
         * medals : [{"img":"http://res.smzdm.com/images/user_logo/signin201609.png","desc":"5周年纪念徽章"}]
         * official : 0
         * have_current_user : 1
         * is_anonymous : 0
         * from_client : iPhone
         * from_client_version :
         * from_client_uri : http://www.smzdm.com/push/
         * from_client_suff : 客户端
         */

        public List<CommentRowsBean> rows;
        public List<CommentRowsBean> hot_comments;


    }
}
