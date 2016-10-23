package com.lcc.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcc.entity.Letter;
import com.lcc.entity.XtNewsEntity;
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
 * Description:  FavAdapter(收藏的适配器)
 */
public class LetterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Letter> mList = new ArrayList<>();

    public void bind(List<Letter> messages) {
        this.mList = messages;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.letter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Letter weekData = mList.get(position);
        NormalViewHolder holder = (NormalViewHolder) viewHolder;

        String nickname = weekData.getNickname();
        holder.tv_nickname.setText(nickname);

        String URL = weekData.getUser_image();
        if (!TextUtils.isEmpty(URL)) {
            ImageManager.getInstance().loadCircleImage(holder.iv_head.getContext(),
                    URL, holder.iv_head);
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

    @Override
    public int getItemCount() {
        return getBasicItemCount();
    }

    public int getBasicItemCount() {
        return mList.size();
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_all)
        RelativeLayout ll_all;
        @Bind(R.id.iv_head)
        ImageView iv_head;
        @Bind(R.id.tv_nickname)
        TextView tv_nickname;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void appendToList(List<Letter> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public List<Letter> getList() {
        return mList;
    }

    public interface OnItemClickListener {
        void onItemClick(Letter data);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        this.mListener = li;
    }
}
