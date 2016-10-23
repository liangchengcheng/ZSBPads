package com.lcc.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lcc.entity.Article;
import com.lcc.entity.Comments;
import com.lcc.msdq.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zsbpj.lccpj.frame.ImageManager;
import zsbpj.lccpj.utils.TimeUtils;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  CommentAdapter
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NORMAL_ITEM = 0;
    public static final int FOOTER_ITEM = 2;
    private List<Comments> mList = new ArrayList<>();
    private boolean hasFooter;
    private boolean hasMoreData = true;

    public void bind(List<Comments> messages) {
        this.mList = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getBasicItemCount() && hasFooter) {
            return FOOTER_ITEM;
        } else {
            return NORMAL_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_ITEM) {
            return new NormalViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_comment, parent, false));
        } else {
            return new FootViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_foot_normal, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof FootViewHolder) {
            if (hasMoreData) {
                ((FootViewHolder) viewHolder).mProgressView.setVisibility(View.VISIBLE);
                ((FootViewHolder) viewHolder).mTextView.setText("正在加载...");
            }
        } else {
            final Comments weekData = mList.get(position);
            NormalViewHolder holder = (NormalViewHolder) viewHolder;
            holder.tv_nickname.setText(weekData.getNickname());

//            String aite=weekData.getReplay_author();
//            if (!TextUtils.isEmpty(aite)){
//                holder.tvComment.setText("@"+ aite+weekData.getContent());
//                SpannableStringBuilder builder = new SpannableStringBuilder( holder.tvComment.getText().toString());
//                ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);
//                builder.setSpan(blueSpan, 0, aite.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                holder.tvComment.setText(builder);
//            }else {
            if (weekData.getContent().contains("@")) {
                holder.tvComment.setText(weekData.getContent());
                SpannableStringBuilder builder = new SpannableStringBuilder(holder.tvComment.getText().toString());
                ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);
                builder.setSpan(blueSpan, 0, weekData.getReplay_nickname().length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tvComment.setText(builder);
            } else {
                holder.tvComment.setText(weekData.getContent());
            }

            //}

            if (!TextUtils.isEmpty(weekData.getUser_image())) {
                holder.ivUserAvatar.setVisibility(View.VISIBLE);
                ImageManager.getInstance().loadCircleImage(holder.ivUserAvatar.getContext(),
                        weekData.getUser_image(), holder.ivUserAvatar);
            }

            String time = weekData.getCreated_time();
            if (!TextUtils.isEmpty(time)) {
                String current = TimeUtils.getTimeFormatText(time);
                holder.tv_time.setText(current);
            }

            if (mListener != null) {
                holder.ll_all.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(weekData);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount() + (hasFooter ? 1 : 0);

    }

    public int getBasicItemCount() {
        return mList.size();
    }

    /**
     * 正常的布局
     */
    class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivUserAvatar)
        ImageView ivUserAvatar;
        @Bind(R.id.tvComment)
        TextView tvComment;
        @Bind(R.id.ll_all)
        LinearLayout ll_all;
        @Bind(R.id.tv_nickname)
        TextView tv_nickname;
        @Bind(R.id.tv_time)
        TextView tv_time;

        public NormalViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 头部的布局
     */
    class FootViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.mProgressView)
        ProgressBar mProgressView;
        @Bind(R.id.mTextView)
        TextView mTextView;

        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void appendToList(List<Comments> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public List<Comments> getList() {
        return mList;
    }

    public void setHasFooter(boolean hasFooter) {
        if (this.hasFooter != hasFooter) {
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }

    public boolean hasMoreData() {
        return hasMoreData;
    }

    public void setHasMoreData(boolean isMoreData) {
        if (this.hasMoreData != isMoreData) {
            this.hasMoreData = isMoreData;
            notifyDataSetChanged();
        }
    }

    public void setHasMoreDataAndFooter(boolean hasMoreData, boolean hasFooter) {
        if (this.hasMoreData != hasMoreData || this.hasFooter != hasFooter) {
            this.hasMoreData = hasMoreData;
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Comments data);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        this.mListener = li;
    }
}
