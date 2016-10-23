package com.lcc.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcc.entity.FavEntity;
import com.lcc.entity.UserListFav;
import com.lcc.msdq.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zsbpj.lccpj.frame.ImageManager;
import zsbpj.lccpj.utils.TimeUtils;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  FavAdapter(收藏的适配器)
 */
public class UserListFavAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NORMAL_ITEM = 0;
    public static final int FOOTER_ITEM = 2;
    private boolean hasFooter;
    private boolean hasMoreData;
    private List<UserListFav> mList = new ArrayList<>();

    public void bind(List<UserListFav> messages) {
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
                    .inflate(R.layout.userlist_fav_item, parent, false));
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
            final UserListFav weekData = mList.get(position);
            NormalViewHolder holder = (NormalViewHolder) viewHolder;

            String name = weekData.getNickname();
            String time = TimeUtils.getTimeFormatText(weekData.getC_time());
            String qm = weekData.getQm();
            String url = weekData.getUser_image();

            holder.player_name.setText(name);
            holder.time_ago.setText(time);
            if (!TextUtils.isEmpty(url)) {
                ImageManager.getInstance().loadCircleImage(holder.player_avatar.getContext(), url,
                        holder.player_avatar);
            }
            if (!TextUtils.isEmpty(qm)) {
                holder.player_bio.setText(qm);
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

    class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_all)
        RelativeLayout ll_all;
        //昵称
        @Bind(R.id.player_name)
        TextView player_name;
        //时间
        @Bind(R.id.time_ago)
        TextView time_ago;
        //签名
        @Bind(R.id.player_bio)
        TextView player_bio;
        @Bind(R.id.player_avatar)
        ImageView player_avatar;

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

    public void appendToList(List<UserListFav> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public List<UserListFav> getList() {
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
        void onItemClick(UserListFav data);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        this.mListener = li;
    }
}
