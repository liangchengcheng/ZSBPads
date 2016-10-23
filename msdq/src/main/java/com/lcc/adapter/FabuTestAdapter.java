package com.lcc.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcc.entity.FavEntity;
import com.lcc.entity.TestEntity;
import com.lcc.msdq.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zsbpj.lccpj.utils.TimeUtils;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2016年08月27日22:35:39
 * Description:  发布的资料的问题
 */
public class FabuTestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NORMAL_ITEM = 0;
    public static final int FOOTER_ITEM = 2;
    private List<TestEntity> mList = new ArrayList<>();
    private boolean hasFooter;
    private boolean hasMoreData = true;

    public void bind(List<TestEntity> messages) {
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
                    .inflate(R.layout.fabu_item, parent, false));
        } else {
            return new FootViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fav_layout_foot_loading, parent, false));
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
            final TestEntity weekData = mList.get(position);
            NormalViewHolder holder = (NormalViewHolder) viewHolder;

            String title = weekData.getTitle();
            String time = TimeUtils.getTimeFormatText(weekData.getCreated_time());
            SpannableString styledText = new SpannableString("发布了" + title + time);
            styledText.setSpan(new TextAppearanceSpan(holder.tv_nickname.getContext(),
                    R.style.style0), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(holder.tv_nickname.getContext(),
                    R.style.style1), 3, 3+title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(holder.tv_nickname.getContext(),
                    R.style.style2), 3+title.length(), 3+title.length()+time.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.tv_nickname.setText(styledText, TextView.BufferType.SPANNABLE);
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

    class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_all)
        RelativeLayout ll_all;
        @Bind(R.id.tv_nickname)
        TextView tv_nickname;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

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

    public void appendToList(List<TestEntity> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public List<TestEntity> getList() {
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
        void onItemClick(TestEntity data);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        this.mListener = li;
    }
}
