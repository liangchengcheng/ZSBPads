package com.lcc.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lcc.entity.CodeEntity;
import com.lcc.entity.TestEntity;
import com.lcc.msdq.R;

import zsbpj.lccpj.view.recyclerview.adapter.LoadMoreRecyclerAdapter;

public class CodeAdapter extends LoadMoreRecyclerAdapter<CodeEntity,CodeAdapter.ViewHolder>{
    private  OnItemClickListener onItemClickListener;
    private Activity mActivity;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public CodeAdapter(Activity activity){
        this.mActivity=activity;
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.code_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, final int position) {
        CodeEntity entity=getItem(position);
        holder.ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(getItem(position));
            }
        });
        holder.tv_title.setText(entity.getName());
        holder.tv_content.setText(entity.getJj());
        holder.tv_time.setText(entity.getDate());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView tv_title;
        public final TextView tv_content;
        public final TextView tv_time;
        public final CardView ll_all;

        public ViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            ll_all = (CardView) view.findViewById(R.id.ll_all);
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(CodeEntity entity);
    }
}
