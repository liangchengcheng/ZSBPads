package com.lcc.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.lcc.entity.Answer;
import com.lcc.entity.TestEntity;
import com.lcc.msdq.R;
import com.lcc.view.StretchyTextView;
import java.util.ArrayList;
import java.util.List;
import android.view.View.OnClickListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import zsbpj.lccpj.frame.ImageManager;
import zsbpj.lccpj.utils.TimeUtils;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  AnswerIndexAdapter
 */
public class AnswerIndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NORMAL_ITEM = 0;
    private static final int HEAD_ITEM = 1;
    private static final int FOOTER_ITEM = 2;
    private List<Object> mList = new ArrayList<>();
    private boolean hasFooter;
    private boolean hasMoreData = true;
    private boolean isFav;

    public void bind(List<Object> messages) {
        this.mList = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_ITEM;
        } else if (position == getBasicItemCount() && hasFooter) {
            return FOOTER_ITEM;
        } else {
            return NORMAL_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_ITEM) {
            return new NormalViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_answer_item, parent, false));
        } else if (viewType == FOOTER_ITEM) {
            return new FootViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_foot_loading, parent, false));
        } else {
            return new HeadViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_answer_header, parent, false));
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof HeadViewHolder) {
            final TestEntity object = (TestEntity) mList.get(position);
            HeadViewHolder holder = (HeadViewHolder) viewHolder;
            holder.tv_name.setMaxLineCount(3);
            holder.tv_name.setContent(object.getSummary());
            holder.tv_name.setContentTextColor(Color.parseColor("#6D6D6D"));
            holder.tv_title.setText(object.getTitle());

            String s_num = object.getZ_num();
            if (!TextUtils.isEmpty(s_num) || s_num.equals("0")) {
                holder.tv_llsc.setText("共有" + s_num + "人收藏了这个问题");
                holder.tv_llsc.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        answerClickListener.OnAnswerClick(object);
                    }
                });
            }

            if (isFav) {
                holder.tv_sc.setText("已收藏");
                holder.tv_sc.setBackgroundResource(R.drawable.bg_cop);
                holder.tv_sc.setTextColor(Color.parseColor("#000000"));
            } else {
                holder.tv_sc.setBackgroundResource(R.drawable.bg_con);
                holder.tv_sc.setText("收藏");
                holder.tv_sc.setTextColor(Color.parseColor("#006FCB"));
            }

            if (favListener != null) {
                holder.tv_sc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        favListener.onFavClick();
                    }
                });
            }

        } else if (viewHolder instanceof FootViewHolder) {
            if (hasMoreData) {
                ((FootViewHolder) viewHolder).mProgressView.setVisibility(View.VISIBLE);
                ((FootViewHolder) viewHolder).mTextView.setText("正在加载...");
            }
        } else {
            Object object = mList.get(position);
            final Answer answer = (Answer) object;
            NormalViewHolder holder = (NormalViewHolder) viewHolder;
            holder.des_content.setText(Html.fromHtml(answer.getAnswer()).toString().trim());
            holder.tv_name.setText(answer.getNickname());
            ImageManager.getInstance().loadCircleImage(holder.iv_image.getContext(),
                    answer.getUser_image(), holder.iv_image);
            holder.tv_znum.setText(answer.getP_num()+"赞");
            holder.tv_c_time.setText(TimeUtils.getTimeFormatText(answer.getCreated_time()));
            if (mListener != null) {
                holder.ll_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(answer);
                    }
                });
            }

            holder.iv_image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageListener.onImageClick(answer.getPhone());
                }
            });
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
        @Bind(R.id.des_content)
        TextView des_content;
        @Bind(R.id.tv_c_time)
        TextView tv_c_time;
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.tv_znum)
        TextView tv_znum;
        @Bind(R.id.iv_image)
        ImageView iv_image;
        @Bind(R.id.ll_all)
        CardView ll_all;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 头部的布局
     */
    class HeadViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.spread_textview)
        StretchyTextView tv_name;
        @Bind(R.id.tv_llrs)
        TextView tv_llrs;
        @Bind(R.id.tv_llsc)
        TextView tv_llsc;
        @Bind(R.id.tv_sc)
        TextView tv_sc;

        public HeadViewHolder(View itemView) {
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

    public void appendToList(List<Answer> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public List<Object> getList() {
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

    public void setFav(boolean isFav) {
        this.isFav = isFav;
        notifyDataSetChanged();
    }

    public void setHasMoreDataAndFooter(boolean hasMoreData, boolean hasFooter) {
        if (this.hasMoreData != hasMoreData || this.hasFooter != hasFooter) {
            this.hasMoreData = hasMoreData;
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(com.lcc.entity.Answer data);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        this.mListener = li;
    }

    public interface OnFavClickListener {
        void onFavClick();
    }

    private OnFavClickListener favListener;

    public void setOnFavClickListener(OnFavClickListener favListener) {
        this.favListener = favListener;
    }

    public interface OnImageClickListener {
        void onImageClick(String  user_phone);
    }

    private OnImageClickListener ImageListener;

    public void setOnImageClickListener(OnImageClickListener ImageListener) {
        this.ImageListener = ImageListener;
    }

    public interface OnAnswerClickListener {
        void OnAnswerClick(TestEntity object);
    }

    private OnAnswerClickListener answerClickListener;

    public void setOnAnswerClickListener(OnAnswerClickListener answerClickListener) {
        this.answerClickListener = answerClickListener;
    }

}
