package com.lcc.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcc.entity.Type1;
import com.lcc.entity.Type2;
import com.lcc.msdq.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2016年07月01日13:40:00
 * Description:  ChoiceType2Adapter
 */
public class ChoiceType2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Type2> mList = new ArrayList<>();

    public void bind(List<Type2> messages) {
        this.mList = messages;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choice_type_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Type2 weekData = mList.get(position);
        NormalViewHolder holder = (NormalViewHolder) viewHolder;

        String title = weekData.getS_name();
        holder.tv_nickname.setText(title);
        if (mListener != null) {
            holder.rl_choice.setOnClickListener(new OnClickListener() {
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

        @Bind(R.id.rl_choice)
        RelativeLayout rl_choice;

        @Bind(R.id.tv_nickname)
        TextView tv_nickname;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public List<Type2> getList() {
        return mList;
    }

    public interface OnItemClickListener {
        void onItemClick(Type2 data);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        this.mListener = li;
    }
}
