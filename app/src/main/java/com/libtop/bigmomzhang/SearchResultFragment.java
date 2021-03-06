package com.libtop.bigmomzhang;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.libtop.bigmomzhang.adapter.BigMonAdapter;
import com.libtop.bigmomzhang.bean.RowsBean;
import com.libtop.bigmomzhang.bean.SearchBean;
import com.libtop.bigmomzhang.func.OnBigMonClickListener;
import com.libtop.bigmomzhang.func.OnRVItemClickListener;
import com.libtop.bigmomzhang.network.HttpRequest;
import com.libtop.bigmomzhang.utils.LogUtil;
import com.libtop.bigmomzhang.utils.MapUtil;
import com.libtop.bigmomzhang.utils.SharedPrefsStrListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by LianTu on 2016-10-8.
 */

public class SearchResultFragment extends BaseFragment
{

    @BindView(R.id.rcv_search_list)
    RecyclerView rcvSearchList;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private BigMonAdapter bigMonAdapter;

    private List<RowsBean> lists = new ArrayList<>();


    //最少值得比率，最高100，最低0
//    private final int LIMIT_GOOD = 80;
    //最少评论数
//    private final int LIMIT_COMMENT = 5;

    private final int ONE_PAGE_SIZE = 20;
    private final int PRELOAD_SIZE = 5;
    private int mPage = 1;
    //加载下一页的个数
    private int offset = 0;
    private String keyword = "";

    private int count = 0;

    private boolean isFilter = true;
    private boolean hasMore = true;
    private InputMethodManager inputManager;

    private int limitGood;
    private int limitComment;

    public static SearchResultFragment getInstance(String title) {
        SearchResultFragment sf = new SearchResultFragment();
        sf.keyword = title;
        return sf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search_result_page, container, false);

        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initRecyclerView();
        initValue();
        requestData(true);
    }

    private void initValue() {
        String zhi = SharedPrefsStrListUtil.getStringValue(App.getContext(),SettingActivity.LOW_ZHI,"80");
        String discuss = SharedPrefsStrListUtil.getStringValue(App.getContext(),SettingActivity.LOW_DISCUSS,"5");
        limitGood = Integer.valueOf(zhi);
        limitComment = Integer.valueOf(discuss);
    }


    private void initView()
    {
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                count = 0;
                requestData(true);
            }
        });
        swipeRefreshLayout.measure(0,0);
        inputManager =
                (InputMethodManager) getContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }

    private void initRecyclerView()
    {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvSearchList.setLayoutManager(layoutManager);
        bigMonAdapter = new BigMonAdapter(getContext(), lists);
        rcvSearchList.setAdapter(bigMonAdapter);
//        rcvSearchList.setOnTouchListener(new ShowHideOnScroll(getActivity().findViewById(R.id.appbar)));
        rcvSearchList.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(inputManager.isActive()){
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
                return false;
            }
        });
        rcvSearchList.addOnScrollListener(getOnBottomListener(layoutManager));
        bigMonAdapter.setOnRVItemClickListener(new OnRVItemClickListener()
        {
            @Override
            public void onClick(View v, RowsBean rowsBean)
            {
                Intent intent = new Intent(getContext(),ItemDetailActivity.class);
                intent.putExtra(ItemDetailActivity.CHANNEL_ID,rowsBean.getArticle_channel_id());
                intent.putExtra(ItemDetailActivity.LINK_TYPE,rowsBean.getRedirect_data().link_type);
                intent.putExtra("sub_type",rowsBean.getRedirect_data().sub_type);
                intent.putExtra(ItemDetailActivity.LINK_VAL,rowsBean.getRedirect_data().link_val);
                startActivity(intent);
            }
        });
        bigMonAdapter.setOnBigMonClickListener(onBigMonClickListener);
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
        params.put("keyword", keyword);
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
//        subscription =
        HttpRequest.getBigMonApi().getSearchList("v1", "list", MapUtil.mapObject2String(params))
                .concatMap(new Function<SearchBean, ObservableSource<List<RowsBean>>>() {
                    @Override
                    public ObservableSource<List<RowsBean>> apply(SearchBean searchBean) throws Exception {
                        return Observable.just(searchBean.data.rows);
                    }
                })
                .concatMap(new Function<List<RowsBean>, ObservableSource<RowsBean>>() {
                    @Override
                    public ObservableSource<RowsBean> apply(List<RowsBean> rowsBeans) throws Exception {
                        if (rowsBeans.isEmpty())
                            hasMore = false;
                        return Observable.fromIterable(rowsBeans);
                    }
                })
                .filter(new Predicate<RowsBean>() {
                    @Override
                    public boolean test(RowsBean rowsBean) throws Exception {
                        if (isFilter)
                        {
                            return rowsBean.getWorthy() > limitGood && Integer.parseInt(rowsBean.getArticle_comment()) > limitComment;
                        }
                        else
                        {
                            return true;
                        }
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                })
//                .toSortedList(new Func2<RowsBean, RowsBean, Integer>()
//                {
//                    @Override
//                    public Integer call(RowsBean rowsBean, RowsBean rowsBean2)
//                    {
//                        return rowsBean2.getWorthy() - rowsBean.getWorthy();
//                    }
//                })
                .subscribe(new DisposableSingleObserver<List<RowsBean>>() {
                    @Override
                    public void onSuccess(List<RowsBean> rowsBeens){
                        count++;
                        if (clean){
                            lists.clear();
                            hasMore = true;
                        }
                        lists.addAll(rowsBeens);
                        bigMonAdapter.notifyDataSetChanged();
                        if (lists.size() < 30 && count < 60){
                            offset += ONE_PAGE_SIZE;
                            requestData(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

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
                if (!swipeRefreshLayout.isRefreshing() && isBottom && hasMore)
                {
//                    Toast.makeText(getContext(), "isBottom", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(true);
                    offset += ONE_PAGE_SIZE;
                    requestData(false);
                }
            }
        };
    }


    public String getKeyword()
    {
        return keyword;
    }
}
