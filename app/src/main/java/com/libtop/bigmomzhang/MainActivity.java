package com.libtop.bigmomzhang;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.libtop.bigmomzhang.adapter.BigMonAdapter;
import com.libtop.bigmomzhang.bean.RowsBean;
import com.libtop.bigmomzhang.bean.SearchBean;
import com.libtop.bigmomzhang.func.OnBigMonClickListener;
import com.libtop.bigmomzhang.func.OnRVItemClickListener;
import com.libtop.bigmomzhang.network.HttpRequest;
import com.libtop.bigmomzhang.utils.LogUtil;
import com.libtop.bigmomzhang.utils.MapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MainActivity extends BaseActivity
{

    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.rcv_search_list)
    RecyclerView rcvSearchList;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private BigMonAdapter bigMonAdapter;

    private List<RowsBean> lists = new ArrayList<>();

    private final int ONE_PAGE_SIZE = 20;
    private final int PRELOAD_SIZE = 4;
    private int mPage = 1;
    //加载下一页的个数
    private int offset = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initRecyclerView();
        requestData(true);

    }


    private void initRecyclerView()
    {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvSearchList.setLayoutManager(layoutManager);
        bigMonAdapter = new BigMonAdapter(MainActivity.this, lists);
        rcvSearchList.setAdapter(bigMonAdapter);
        rcvSearchList.addOnScrollListener(getOnBottomListener(layoutManager));
        bigMonAdapter.setOnRVItemClickListener(new OnRVItemClickListener()
        {
            @Override
            public void onClick(View v, RowsBean rowsBean)
            {
                LogUtil.w(rowsBean.getArticle_title());
                LogUtil.w(rowsBean.getArticle_worthy()+"");
                LogUtil.w(rowsBean.getArticle_unworthy()+"");
            }
        });
        bigMonAdapter.setOnBigMonClickListener(onBigMonClickListener);
    }


    private void initView()
    {
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                requestData(true);
            }
        });

    }


    //    @GET("https://api.smzdm.com/v1/list?keyword=笔记本&type=home&category_id=&brand_id=&mall_id=&order=score&day=&limit=20&offset=0&f=android&s=A7IepH35JcdbNwexZRT0dAaTrg3RrElV&v=320&weixin=0")
    private void requestData(final boolean clean)
    {
        if (clean)
        {
            offset = 0;
        }
        swipeRefreshLayout.setRefreshing(true);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keyword", "手机");
        params.put("type", "home");
        params.put("category_id", "");
        params.put("brand_id", "");
        params.put("mall_id", "");
        params.put("order", "score");
        params.put("limit", ONE_PAGE_SIZE);
        params.put("offset", offset);
        params.put("f", "android");
        params.put("s", "A7IepH35JcdbNwexZRT0dAaTrg3RrElV");
        params.put("v", "320");
        params.put("weixin", 0);
        subscription = HttpRequest.getBigMonApi().getSearchList("v1", "list", MapUtil.mapObject2String(params)).concatMap(new Func1<SearchBean, Observable<List<RowsBean>>>()
        {
            @Override
            public Observable<List<RowsBean>> call(SearchBean searchBean)
            {
                return Observable.just(searchBean.data.rows);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doAfterTerminate(new Action0()
        {
            @Override
            public void call()
            {
                swipeRefreshLayout.setRefreshing(false);
            }
        }).subscribe(new Observer<List<RowsBean>>()
        {
            @Override
            public void onCompleted()
            {

            }


            @Override
            public void onError(Throwable e)
            {
                LogUtil.e(e.toString());
            }


            @Override
            public void onNext(List<RowsBean> rowsBeens)
            {
                if (clean)
                {
                    lists.clear();
                }
                lists.addAll(rowsBeens);
                bigMonAdapter.notifyDataSetChanged();
            }
        });
    }


    private OnBigMonClickListener onBigMonClickListener = new OnBigMonClickListener()
    {
        @Override
        public void onClick(View v, RowsBean rowsBean)
        {
            switch (v.getId())
            {
                case R.id.img_item_photo:
                    LogUtil.w(rowsBean.getArticle_title());
                    break;
                case R.id.tv_item_title:
                    LogUtil.w(rowsBean.getArticle_title());
                    break;
                case R.id.ll_item_container:
                    break;
            }
        }
    };


    @OnClick({R.id.activity_main})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.activity_main:
                break;
        }
    }


    RecyclerView.OnScrollListener getOnBottomListener(final LinearLayoutManager layoutManager)
    {
        return new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy)
            {
                boolean isBottom = layoutManager.findLastCompletelyVisibleItemPosition() >= bigMonAdapter.getItemCount() - PRELOAD_SIZE;
                if (!swipeRefreshLayout.isRefreshing() && isBottom)
                {
                    Toast.makeText(MainActivity.this, "isBottom", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(true);
                    offset += ONE_PAGE_SIZE;
                    requestData(false);
                }
            }
        };
    }
}
