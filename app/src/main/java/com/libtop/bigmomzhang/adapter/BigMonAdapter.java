package com.libtop.bigmomzhang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    public BigMonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bigmon, parent, false);
        v.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onRVItemClickListener!=null){
                    onRVItemClickListener.onClick(v,(RowsBean) v.getTag());
                }
            }
        });
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(BigMonAdapter.ViewHolder holder, int position)
    {
        RowsBean rowsBean = lists.get(position);
        holder.tvItemTitle.setText(rowsBean.article_title);
        ImageLoadUtil.LoadImage(context,rowsBean.article_pic,holder.imgItemPhoto);
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

        @Bind(R.id.img_item_photo)
        ImageView imgItemPhoto;
        @Bind(R.id.tv_item_title)
        TextView tvItemTitle;
        @Bind(R.id.ll_item_container)
        LinearLayout llItemContainer;


        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgItemPhoto.setOnClickListener(this);
            tvItemTitle.setOnClickListener(this);
            llItemContainer.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            if (onBigMonClickListener!=null){
                onBigMonClickListener.onClick(v,lists.get(getAdapterPosition()));
            }
        }
    }
}
