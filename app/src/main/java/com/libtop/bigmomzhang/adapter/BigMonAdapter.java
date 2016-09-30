package com.libtop.bigmomzhang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libtop.bigmomzhang.ImageLoadUtil;
import com.libtop.bigmomzhang.R;
import com.libtop.bigmomzhang.bean.RowsBean;
import com.libtop.bigmomzhang.func.OnBigMonClickListener;
import com.libtop.bigmomzhang.func.OnRVItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by LianTu on 2016-9-29.
 */

public class BigMonAdapter extends RecyclerView.Adapter<BigMonAdapter.ViewHolder>
{


    private Context context;
    private List<RowsBean> lists;

    private OnBigMonClickListener onBigMonClickListener;
    private OnRVItemClickListener onRVItemClickListener;


    public BigMonAdapter(Context context, List<RowsBean> rowsBeanList)
    {
        this.context = context;
        lists = rowsBeanList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        RowsBean rowsBean = lists.get(position);
        holder.tvPrice.setText(rowsBean.getArticle_price());
        holder.tvComment.setText(rowsBean.getArticle_comment());
        holder.tvTime.setText(rowsBean.getArticle_format_date());
        holder.tvMall.setText(rowsBean.getArticle_mall()+" | ");
        holder.tvTag.setText(rowsBean.getArticle_channel_name());
        holder.tvTitle.setText(rowsBean.getArticle_title());
        int worthy = rowsBean.getArticle_worthy();
        int unWorthy = rowsBean.getArticle_unworthy();
        int all = worthy + unWorthy;
        if (all>0){
            holder.tvZhi.setText((int) ((worthy/(float)all)*100)+"%");
        }else {
            holder.tvZhi.setText(100+"%");
        }
        ImageLoadUtil.LoadImage(context, rowsBean.getArticle_pic(), holder.ivPic);
    }


    @Override
    public int getItemCount()
    {
        return lists.size();
    }


    public void setOnBigMonClickListener(OnBigMonClickListener onBigMonClickListener)
    {
        this.onBigMonClickListener = onBigMonClickListener;
    }


    public void setOnRVItemClickListener(OnRVItemClickListener onRVItemClickListener)
    {
        this.onRVItemClickListener = onRVItemClickListener;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        @Bind(R.id.iv_pic)
        ImageView ivPic;
        @Bind(R.id.tv_tag)
        TextView tvTag;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.ly_titleprice)
        LinearLayout lyTitleprice;
        @Bind(R.id.tv_mall)
        TextView tvMall;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.rl_mall_time)
        RelativeLayout rlMallTime;
        @Bind(R.id.tv_comment)
        TextView tvComment;
        @Bind(R.id.tv_zhi)
        TextView tvZhi;
        @Bind(R.id.rl_comment_zhi)
        LinearLayout rlCommentZhi;
        @Bind(R.id.rl_bottom_show)
        RelativeLayout rlBottomShow;


        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
//            ivPic.setOnClickListener(this);
//            tvTitle.setOnClickListener(this);
//            llItemContainer.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            if (onRVItemClickListener!=null){
                onRVItemClickListener.onClick(v, lists.get(getAdapterPosition()));
            }
            if (onBigMonClickListener != null)
            {
                onBigMonClickListener.onClick(v, lists.get(getAdapterPosition()));
            }
        }
    }
}
