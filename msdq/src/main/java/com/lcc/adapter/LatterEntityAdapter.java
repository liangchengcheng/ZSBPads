package com.lcc.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcc.db.test.UserInfo;
import com.lcc.entity.LatterEntity;
import com.lcc.entity.Letter;
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
public class LatterEntityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LatterEntity> mList = new ArrayList<>();
    private UserInfo userInfo = new UserInfo();
    private Letter letter = new Letter();

    private static final int YOU_ITEM = 0;
    private static final int ME_ITEM = 1;

    public LatterEntityAdapter(UserInfo userInfo, Letter letter) {
        this.userInfo = userInfo;
        this.letter = letter;
    }

    public void bind(List<LatterEntity> messages) {
        this.mList = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        LatterEntity latterEntity = mList.get(position);
        if (latterEntity.getFrom_w().equals(letter.getFrom_w())){
            return YOU_ITEM;
        }else{
            return ME_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.e("lccx",viewType+"viewtype");
        if (viewType == YOU_ITEM) {
            return new YouViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_one, parent, false));
        } else {
            return new MeViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_two, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        LatterEntity latterEntity = mList.get(position);
        Log.e("lccx",position+"");
        String message_body = latterEntity.getMessage_body();
        if (viewHolder instanceof YouViewHolder) {
            YouViewHolder holder = (YouViewHolder) viewHolder;
            String URL = letter.getUser_image();
            if (!TextUtils.isEmpty(URL)) {
                ImageManager.getInstance().loadCircleImage(holder.iv_you.getContext(),
                        URL, holder.iv_you);
            }
            holder.tv_you.setText(message_body);
        }else {
            MeViewHolder holder = (MeViewHolder) viewHolder;
            String URL = userInfo.getUser_image();
            if (!TextUtils.isEmpty(URL)) {
                ImageManager.getInstance().loadCircleImage(holder.iv_me.getContext(),
                        URL, holder.iv_me);
            }
            holder.tv_me.setText(message_body);
        }
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount();
    }

    public int getBasicItemCount() {
        return mList.size();
    }

    class YouViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_you)
        TextView tv_you;
        @Bind(R.id.iv_you)
        ImageView iv_you;

        public YouViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MeViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_me)
        TextView tv_me;
        @Bind(R.id.iv_me)
        ImageView iv_me;

        public MeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void appendToList(List<LatterEntity> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public List<LatterEntity> getList() {
        return mList;
    }

}
