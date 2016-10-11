package com.libtop.bigmomzhang;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by LianTu on 2016-10-8.
 */

public class ItemDetailActivity extends BaseActivity
{
    @Bind(R.id.img_detail_photo)
    ImageView imgDetailPhoto;
    @Bind(R.id.tv_detail_mall)
    TextView tvDetailMall;
    @Bind(R.id.tv_detail_time)
    TextView tvDetailTime;
    @Bind(R.id.rl_mall_time)
    LinearLayout rlMallTime;
    @Bind(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @Bind(R.id.tv_detail_price)
    TextView tvDetailPrice;
    @Bind(R.id.tv_desc)
    TextView tvDesc;
    @Bind(R.id.img_order)
    ImageView imgOrder;
    @Bind(R.id.ll_order)
    LinearLayout llOrder;

    private String url;
    private String channelId,linkVal;
    private Context context;


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
        channelId = intent.getStringExtra("channel_id");
        linkVal = intent.getStringExtra("link_val");
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
        HttpRequest.getBigMonApi().getDetial( linkVal, MapUtil.mapObject2String(params)).concatMap(new Func1<DetailBean, Observable<DetailBean.ResultBean>>()
        {
            @Override
            public Observable<DetailBean.ResultBean> call(DetailBean detailBean)
            {
                return Observable.just(detailBean.data);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DetailBean.ResultBean>()
        {
            @Override
            public void onCompleted()
            {

            }


            @Override
            public void onError(Throwable e)
            {
                LogUtil.w(e.toString());

            }


            @Override
            public void onNext(DetailBean.ResultBean resultBean)
            {
                ImageLoadUtil.LoadImage(context, resultBean.getArticle_pic(), imgDetailPhoto);
                tvDetailMall.setText(resultBean.getArticle_mall());
                tvDetailTime.setText(" | "+resultBean.getRedirect_data());
                tvDetailTitle.setText(resultBean.getArticle_title());
                tvDetailPrice.setText(resultBean.getArticle_price());
                tvDesc.setMovementMethod(LinkMovementMethod.getInstance());
                tvDesc.setText(Html.fromHtml(resultBean.getProduct_intro()+"\n"+resultBean.getArticle_content()+"\n"+resultBean.getArticle_bl_reason()
                        ,new URLImageParser(tvDesc,context.getResources(), Picasso.with(context))
                        ,null));
                if (resultBean.article_order_screenshot != null
                       && !resultBean.article_order_screenshot.isEmpty() && !StringUtils.isEmpty(resultBean.article_order_screenshot.get(0).pic))
                {
                    ImageLoadUtil.LoadImage(context, resultBean.article_order_screenshot.get(0).pic, imgOrder);
                }
                else
                {
                    llOrder.setVisibility(View.GONE);
                }
                url = resultBean.getRedirect_data().link;
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


    @OnClick(R.id.btn_go_url)
    public void onClick()
    {
        if (!TextUtils.isEmpty(url)){
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

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
}
