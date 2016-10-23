package com.lcc.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lcc.entity.Article;
import com.lcc.entity.WeekData;
import com.lcc.msdq.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zsbpj.lccpj.frame.ImageManager;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  IndexMenuAdapter
 */
public class IndexMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NORMAL_ITEM = 0;
    public static final int FOOTER_ITEM = 2;
    private List<Article> mList = new ArrayList<>();
    private boolean hasFooter;
    private boolean hasMoreData = true;

    public void bind(List<Article> messages) {
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
                    .inflate(R.layout.index_menu_item, parent, false));
        } else {
            return new FootViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_foot_loading, parent, false));
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
            final Article weekData = mList.get(position);
            NormalViewHolder holder = (NormalViewHolder) viewHolder;
            holder.tv_title.setText(weekData.getTitle());
            holder.tv_summary.setText(weekData.getSummary());
            holder.l_num.setText(weekData.getL_num());
            //精品文章的收藏事件
            holder.ll_sc.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    favListener.onOnFavClick(weekData);
                }
            });
            if (TextUtils.isEmpty(weekData.getImage_url())) {
                holder.iv_head.setVisibility(View.GONE);
            } else {
                holder.iv_head.setVisibility(View.VISIBLE);
                ImageManager.getInstance().loadUrlImage(holder.iv_head.getContext(),
                        weekData.getImage_url(), holder.iv_head);
            }

            if (weekData.getState().equals("0")) {
                holder.state.setVisibility(View.GONE);
                holder.state_red.setVisibility(View.GONE);
            } else {
                if (weekData.getState().equals("1")) {
                    holder.state.setVisibility(View.GONE);
                    holder.state_red.setVisibility(View.VISIBLE);
                }
                if (weekData.getState().equals("2")) {
                    holder.state_red.setVisibility(View.GONE);
                    holder.state.setVisibility(View.VISIBLE);
                }
                if (weekData.getState().equals("3")) {
                    holder.state.setVisibility(View.VISIBLE);
                    holder.state_red.setVisibility(View.VISIBLE);
                }
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
        @Bind(R.id.iv_head)
        ImageView iv_head;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_summary)
        TextView tv_summary;
        @Bind(R.id.ll_all)
        CardView ll_all;
        @Bind(R.id.state)
        TextView state;
        @Bind(R.id.state_red)
        TextView state_red;
        @Bind(R.id.l_num)
        TextView l_num;
        @Bind(R.id.ll_sc)
        LinearLayout ll_sc;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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

    public void appendToList(List<Article> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public List<Article> getList() {
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
        void onItemClick(Article data);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        this.mListener = li;
    }

    public interface OnFavClickListener {
        void onOnFavClick(Article data);
    }

    private OnFavClickListener favListener;

    public void setOnFavClickListener(OnFavClickListener li) {
        this.favListener = li;
    }


}
