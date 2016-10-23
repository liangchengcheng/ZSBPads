package com.lcc.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lcc.entity.CompanyDescription;
import com.lcc.entity.CompanyEntity;
import com.lcc.msdq.R;

import de.hdodenhof.circleimageview.CircleImageView;
import zsbpj.lccpj.frame.ImageManager;
import zsbpj.lccpj.view.recyclerview.adapter.LoadMoreRecyclerAdapter;

public class CompanyAdapter extends LoadMoreRecyclerAdapter<CompanyDescription, CompanyAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private Activity mActivity;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CompanyAdapter(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, final int position) {
        final CompanyDescription entity = getItem(position);
        holder.ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(getItem(position));
            }
        });

        holder.tv_title.setText(entity.getCompany_name());
        holder.tv_content.setText(entity.getCompany_description());
        holder.tv_l_num.setText(entity.getLook_num());
        holder.tv_sc.setText(entity.getZ_num());
        String c_num=entity.getC_num();
        if (c_num.length()<2){
            c_num=""+c_num+"";
        }
        holder.tv_c_num.setText(c_num+"问");

        String url = entity.getCompany_image();
        ImageManager.getInstance().loadCircleImage(holder.iv_icon.getContext()
                , url, holder.iv_icon);
        holder.iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageListener.onImageClick(entity);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tv_title;
        public final TextView tv_l_num;
        public final TextView tv_sc;
        public final TextView tv_c_num;
        public final TextView tv_content;
        public final CardView ll_all;
        public final ImageView iv_icon;

        public ViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_l_num = (TextView) view.findViewById(R.id.tv_l_num);
            tv_c_num = (TextView) view.findViewById(R.id.tv_c_num);
            tv_sc = (TextView) view.findViewById(R.id.tv_sc);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            ll_all = (CardView) view.findViewById(R.id.ll_all);
            iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(CompanyDescription entity);
    }

    public interface OnImageClickListener {
        void onImageClick(CompanyDescription  user_phone);
    }

    private OnImageClickListener ImageListener;

    public void setOnImageClickListener(OnImageClickListener ImageListener) {
        this.ImageListener = ImageListener;
    }

}
