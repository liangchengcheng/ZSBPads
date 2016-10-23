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
import android.widget.TextView;

import com.lcc.entity.UserGood;
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
public class UserGoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<UserGood> mList = new ArrayList<>();

    public void bind(List<UserGood> messages) {
        this.mList = messages;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_good_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final UserGood weekData = mList.get(position);
        NormalViewHolder holder = (NormalViewHolder) viewHolder;

        String title = weekData.getTitle()+"";
        String time = weekData.getCreated_time()+"";
        String gooder = weekData.getNickname()+"";

        SpannableString styledText = new SpannableString(gooder +"赞了"+ title);

        styledText.setSpan(new TextAppearanceSpan(holder.tv_content.getContext(),
                R.style.style0), 0, gooder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(holder.tv_content.getContext(),
                R.style.style1), gooder.length(), 2 + gooder.length()+title.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tv_content.setText(styledText, TextView.BufferType.SPANNABLE);
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
        @Bind(R.id.tv_content)
        TextView tv_content;
        @Bind(R.id.tv_time)
        TextView tv_time;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void appendToList(List<UserGood> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public List<UserGood> getList() {
        return mList;
    }

    public interface OnItemClickListener {
        void onItemClick(UserGood data);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        this.mListener = li;
    }
}
