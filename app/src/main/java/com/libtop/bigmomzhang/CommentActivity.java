package com.libtop.bigmomzhang;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.libtop.bigmomzhang.adapter.CommentAdapter;
import com.libtop.bigmomzhang.bean.CommentBean;
import com.libtop.bigmomzhang.bean.CommentRowsBean;
import com.libtop.bigmomzhang.network.HttpRequest;
import com.libtop.bigmomzhang.utils.LogUtil;
import com.libtop.bigmomzhang.utils.MapUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.libtop.bigmomzhang.App.getContext;


/**
 * Created by LianTu on 2016-10-20.
 */

public class CommentActivity extends BaseActivity
{

    public static final String ARTICLE_ID = "article_id";
    public static final String TYPE = "articleType";
    @BindView(R.id.rcv_comment)
    RecyclerView rcvComment;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar_detail)
    Toolbar toolbarDetail;

    private Context context;
    private String articleId;
    private String articleType;

    private final int ONE_PAGE_SIZE = 20;
    private final int PRELOAD_SIZE = 5;

    //加载下一页的个数
    private int offset = 0;

    private List<CommentRowsBean> lists = new ArrayList<>();
    private CommentAdapter adapter;
    private boolean hasMore = true;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        initData();
        initView();
        initRecyclerView();
        requestData(true);

    }


    private void initRecyclerView()
    {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvComment.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(getContext(), lists);
        rcvComment.setAdapter(adapter);
//        RecyclerView.ItemDecoration dividerItemDecoration = new RecyclerView.ItemDecoration(rcvComment.getContext(),
//                layoutManager.getOrientation()) {
//        };
//        rcvComment.addItemDecoration(dividerItemDecoration);
        rcvComment.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        rcvComment.addOnScrollListener(getOnBottomListener(layoutManager));
    }


    private void initData()
    {
        context = this;
        Intent intent = getIntent();
        articleId = intent.getStringExtra(ARTICLE_ID);
        articleType = intent.getStringExtra(TYPE);
    }


    private void initView()
    {
        toolbarDetail.setTitle("评论");
        toolbarDetail.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        setSupportActionBar(toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbarDetail.setTitleTextColor(Color.WHITE);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                requestData(true);
            }
        });
        swipeRefreshLayout.measure(0, 0);
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


    //    https://api.smzdm.com/v1/comments?article_id=6520395&articleType=haitao&limit=20&offset=0&smiles=0&atta=0&ishot=1&f=android&s=H8el38hOie2uPFuIUo5VJ1hn1bIcAM9r&v=335&weixin=0
    private void requestData(final boolean clean)
    {
        if (clean)
        {
            offset = 0;
        }
        swipeRefreshLayout.setRefreshing(true);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("article_id", articleId);
        params.put("type", articleType);
        params.put("limit", ONE_PAGE_SIZE);
        params.put("offset", offset);
        params.put("smiles", 0);
        params.put("atta", 0);
        params.put("ishot", 1);
        params.put("f", "android");
        params.put("s", "A7IepH35JcdbNwexZRT0dAaTrg3RrElV");
        params.put("v", "320");
        params.put("weixin", 0);
        HttpRequest.getBigMonApi().getComment(MapUtil.mapObject2String(params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                })
                .subscribe(new DisposableObserver<CommentBean>() {
                    @Override
                    public void onNext(CommentBean commentBean) {
                        LogUtil.w(commentBean.toString());
                        if (clean)
                        {
                            lists.clear();
                            hasMore = true;
                        }
                        lists.addAll(commentBean.data.rows);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    RecyclerView.OnScrollListener getOnBottomListener(final LinearLayoutManager layoutManager)
    {
        return new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy)
            {
                boolean isBottom = layoutManager.findLastCompletelyVisibleItemPosition() >= adapter.getItemCount() - PRELOAD_SIZE;
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

}
