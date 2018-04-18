package com.libtop.bigmomzhang;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.libtop.bigmomzhang.bean.DetailBean;
import com.libtop.bigmomzhang.network.HttpRequest;
import com.libtop.bigmomzhang.utils.LogUtil;
import com.libtop.bigmomzhang.utils.MapUtil;
import com.libtop.bigmomzhang.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by LianTu on 2016-10-8.
 */

public class ItemDetailActivity extends BaseActivity
{
    public static final String CHANNEL_ID = "channel_id";
    public static final String LINK_VAL = "link_type";
    public static final String LINK_TYPE = "link_val";

    @BindView(R.id.img_detail_photo)
    ImageView imgDetailPhoto;
    @BindView(R.id.tv_detail_mall)
    TextView tvDetailMall;
    @BindView(R.id.tv_detail_time)
    TextView tvDetailTime;
    @BindView(R.id.rl_mall_time)
    LinearLayout rlMallTime;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.tv_detail_price)
    TextView tvDetailPrice;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.img_order)
    ImageView imgOrder;
    @BindView(R.id.ll_order)
    LinearLayout llOrder;

    private String url;
    private String channelId, linkVal;
    private Context context;
    private String arcticle_content;

    private String articleId;
    private String articleType;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        initData();
        initView();
        requestData();

    }


    private void initData()
    {
        context = this;
        Intent intent = getIntent();
        channelId = intent.getStringExtra(CHANNEL_ID);
        linkVal = intent.getStringExtra(LINK_VAL);
        articleType = intent.getStringExtra(LINK_TYPE);
    }


    //    https://api.smzdm.com/v2/youhui/articles/6454205?
    // channel_id=1&filtervideo=1&imgmode=0&displaymode=0&lastest_update_time=2016-09-26+04%3A52%3A34&show_dingyue=1&show_wiki=1&f=android&s=A7IepH35JcdbNwexZRT0dAaTrg3RrElV&v=320&weixin=0
    private void requestData()
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("channel_id", channelId);
        params.put("filtervideo", 1);
        params.put("imgmode", 0);
        params.put("displaymode", 0);
        params.put("lastest_update_time", "");
        params.put("show_dingyue", 0);
        params.put("show_wiki", 0);
        params.put("f", "android");
        params.put("s", "A7IepH35JcdbNwexZRT0dAaTrg3RrElV");
        params.put("v", "320");
        params.put("weixin", 0);
        HttpRequest.getBigMonApi().getDetial(linkVal, MapUtil.mapObject2String(params))
                .concatMap(new Function<DetailBean, ObservableSource<DetailBean.ResultBean>>() {
                    @Override
                    public ObservableSource<DetailBean.ResultBean> apply(DetailBean detailBean) throws Exception {
                        return Observable.just(detailBean.data);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<DetailBean.ResultBean>() {
                    @Override
                    public void onNext(DetailBean.ResultBean resultBean) {
                        arcticle_content = resultBean.getArticle_content();
                        articleId = resultBean.getArticle_id();
                        ImageLoadUtil.LoadImage(context, resultBean.getArticle_pic(), imgDetailPhoto);
                        tvDetailMall.setText(resultBean.getArticle_mall());
                        tvDetailTime.setText(" | " + resultBean.getArticle_format_date());
                        tvDetailTitle.setText(resultBean.getArticle_title());
                        tvDetailPrice.setText(resultBean.getArticle_price());
                        tvDesc.setMovementMethod(LinkMovementMethod.getInstance());
                        tvDesc.setText(Html.fromHtml(resultBean.getProduct_intro() + "\n" + resultBean.getArticle_content() + "\n" + resultBean.getArticle_bl_reason(), new URLImageParser(tvDesc, context.getResources(), Picasso.with(context)), null));
                        if (resultBean.article_order_screenshot != null && !resultBean.article_order_screenshot.isEmpty() && !StringUtils.isEmpty(resultBean.article_order_screenshot.get(0).pic))
                        {
                            ImageLoadUtil.LoadImage(context, resultBean.article_order_screenshot.get(0).pic, imgOrder);
                        }
                        else
                        {
                            llOrder.setVisibility(View.GONE);
                        }
                        url = resultBean.getRedirect_data().link;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void initView()
    {
        Toolbar toolbarDetail = (Toolbar) findViewById(R.id.toolbar_detail);
        toolbarDetail.setTitle("详情");
        toolbarDetail.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        setSupportActionBar(toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbarDetail.setTitleTextColor(Color.WHITE);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home://actionbar的左侧图标的点击事件处理
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_comment,R.id.btn_copy, R.id.btn_go_url})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_comment:
                commentClick();
                break;
            case R.id.btn_copy:
                copyClick();
                break;
            case R.id.btn_go_url:
                goUrlClick();
                break;
        }
    }


    private void commentClick()
    {

//        if (!StringUtils.isEmpty(arcticle_content)){
//            String type = getType(arcticle_content);
            if (!StringUtils.isEmpty(articleId)
                    || !StringUtils.isEmpty(articleType)){
                Intent intent = new Intent(context,CommentActivity.class);
                intent.putExtra(CommentActivity.ARTICLE_ID,articleId);
                intent.putExtra(CommentActivity.TYPE,articleType);
                startActivity(intent);
            }
//        }
    }

    private String getArcticle(String input){
        String pattern = "gtm_id=\\\"(\\w+)";
        String afterFilter = getStringAfterFilter(input,pattern);
        if (!StringUtils.isEmpty(afterFilter)){
            return afterFilter.replace("gtm_id=\"","");
        }else {
            return "";
        }
    }

    private String getType(String input){
        String pattern = "gtm_channel_name=\\\"(\\w+)";
        String afterFilter = getStringAfterFilter(input,pattern);
        if (!StringUtils.isEmpty(afterFilter)){
            return afterFilter.replace("gtm_channel_name=\"","");
        }else {
            return "";
        }
    }

    private String getStringAfterFilter(String input,String pattern){
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(input);
        if (m.find( )) {
            if (m.groupCount()>0){
                LogUtil.w("Found value: " + m.group(0) );
                return m.group(0);
            }else {
                return "";
            }
        } else {
            LogUtil.w("NO MATCH");
            return "";
        }
    }


    private void copyClick()
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("url",url);
        clipboard.setPrimaryClip(clip);
    }


    private void goUrlClick()
    {
        if (!TextUtils.isEmpty(url))
        {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
