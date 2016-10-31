package com.libtop.bigmomzhang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.libtop.bigmomzhang.R;
import com.libtop.bigmomzhang.bean.CommentRowsBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by zxg on 2016-10-31.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>
{



    private Context context;
    private List<CommentRowsBean> lists;


    public CommentAdapter(Context context, List<CommentRowsBean> commentRowsBeanList)
    {
        this.context = context;
        lists = commentRowsBeanList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        CommentRowsBean bean = lists.get(position);
        holder.tvCommentContent.setText(bean.getComment_content());
    }


    @Override
    public int getItemCount()
    {
        return lists.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {

        @Bind(R.id.tv_comment_content)
        TextView tvCommentContent;


        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
