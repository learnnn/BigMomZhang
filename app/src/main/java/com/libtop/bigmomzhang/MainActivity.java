package com.libtop.bigmomzhang;

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

import java.util.ArrayList;
import java.util.List;

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

    private final int PRELOAD_SIZE = 4;
    private int mPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initRecyclerView();
        requestData();

    }


    private void initRecyclerView()
    {
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        rcvSearchList.setLayoutManager(layoutManager);
        bigMonAdapter = new BigMonAdapter(MainActivity.this, lists);
        rcvSearchList.setAdapter(bigMonAdapter);
        rcvSearchList.addOnScrollListener(getOnBottomListener(layoutManager));
        bigMonAdapter.setOnRVItemClickListener(new OnRVItemClickListener()
        {
            @Override
            public void onClick(View v, RowsBean rowsBean)
            {
                LogUtil.w(rowsBean.article_title);
            }
        });
        bigMonAdapter.setOnBigMonClickListener(onBigMonClickListener);
    }


    private void initView()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                requestData();
            }
        });

    }


    private void requestData()
    {
        swipeRefreshLayout.setRefreshing(true);
        subscription = HttpRequest.getBigMonApi().getCollect().concatMap(new Func1<SearchBean, Observable<List<RowsBean>>>()
        {
            @Override
            public Observable<List<RowsBean>> call(SearchBean searchBean)
            {
                return Observable.just(searchBean.data.rows);
            }
        }).doAfterTerminate(new Action0()
        {
            @Override
            public void call()
            {
                swipeRefreshLayout.setRefreshing(false);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<RowsBean>>()
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
                    LogUtil.w(rowsBean.article_title);
                    break;
                case R.id.tv_item_title:
                    LogUtil.w(rowsBean.article_title);
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

    RecyclerView.OnScrollListener getOnBottomListener(final LinearLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(RecyclerView rv, int dx, int dy) {
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= bigMonAdapter.getItemCount() - PRELOAD_SIZE;
                if (!swipeRefreshLayout.isRefreshing() && isBottom) {
                    Toast.makeText(MainActivity.this,"isBottom",Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(true);
                    mPage += 1;
                    requestData();
                }
            }
        };
    }
}
