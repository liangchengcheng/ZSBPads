package com.lcc.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcc.entity.CompanyEntity;
import com.lcc.entity.CompanyTest;
import com.lcc.entity.TestEntity;
import com.lcc.msdq.R;

import org.w3c.dom.Text;

import zsbpj.lccpj.frame.ImageManager;
import zsbpj.lccpj.utils.TimeUtils;
import zsbpj.lccpj.view.recyclerview.adapter.LoadMoreRecyclerAdapter;

public class TestAdapter extends LoadMoreRecyclerAdapter<TestEntity, TestAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private Activity mActivity;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TestAdapter(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, final int position) {
        final TestEntity entity = getItem(position);
        holder.ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(getItem(position));
            }
        });
        holder.tv_title.setText(entity.getTitle());
        holder.tv_content.setText(entity.getSummary());
        String time = entity.getUpdated_time();
        if (!TextUtils.isEmpty(time)){
            holder.tv_time.setText(TimeUtils.getTimeFormatText(time));
        }
        //评论
        holder.tv_ll.setText(entity.getL_num());
        //收藏
        holder.tv_sc.setText(entity.getZ_num());

        ImageManager.getInstance().loadCircleImage(mActivity, entity.getUser_image(), holder.iv_image);

        if (ImageListener != null) {
            holder.iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageListener.onImageClick(entity.getPhone());
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tv_title;
        public final TextView tv_content;
        public final TextView tv_time;
        public final CardView ll_all;
        public final ImageView iv_image;
        public final TextView tv_ll;
        public final TextView tv_sc;

        public ViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_sc = (TextView) view.findViewById(R.id.tv_sc);
            tv_ll = (TextView) view.findViewById(R.id.tv_ll);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            ll_all = (CardView) view.findViewById(R.id.ll_all);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(TestEntity entity);
    }

    public interface OnImageClickListener {
        void onImageClick(String user_phone);
    }

    private OnImageClickListener ImageListener;

    public void setOnImageClickListener(OnImageClickListener ImageListener) {
        this.ImageListener = ImageListener;
    }
}
