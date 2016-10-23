package com.lcc.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcc.entity.FavEntity;
import com.lcc.entity.NewsInfo;
import com.lcc.entity.XtNewsEntity;
import com.lcc.msdq.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  FavAdapter(收藏的适配器)
 */
public class XtNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<XtNewsEntity> mList = new ArrayList<>();

    public void bind(List<XtNewsEntity> messages) {
        this.mList = messages;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.xt_news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final XtNewsEntity weekData = mList.get(position);
        NormalViewHolder holder = (NormalViewHolder) viewHolder;

        String title = weekData.getTitle();
        String time = weekData.getCreated_time();
        String content = weekData.getMessage_body();

        holder.tv_title.setText(title);
        holder.tv_content.setText(content);
        holder.tv_time.setText(time);
        if (mListener != null) {
            holder.ll_all.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(weekData);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return getBasicItemCount();
    }

    public int getBasicItemCount() {
        return mList.size();
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_all)
        LinearLayout ll_all;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.tv_time)
        TextView tv_time;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void appendToList(List<XtNewsEntity> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public List<XtNewsEntity> getList() {
        return mList;
    }

    public interface OnItemClickListener {
        void onItemClick(XtNewsEntity data);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        this.mListener = li;
    }
}
