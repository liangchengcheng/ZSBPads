package zsbpj.lccpj.view.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.PublicKey;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.interfaces.PBEKey;

import zsbpj.lccpj.R;
import zsbpj.lccpj.view.progress.ProgressWheel;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    LoadMoreRecyclerAdapter
 */

public abstract class LoadMoreRecyclerAdapter<T,VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter {

    public static final int TYPE_FOOTER=Integer.MIN_VALUE;
    public static final int TYPE_ITEM=0;

    /**
     * 是否设置了footer
     */
    private boolean hasFooter;

    /**
     * 是否继续加载数据
     */
    private boolean hasMoreData;

    /**
     *  需要填充的数据
     */
    private final List<T> mList=new LinkedList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if (viewType==TYPE_FOOTER){
           View view= LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.item_view_load_more,parent,false);
           return new FooterViewHolder(view);
       }else{
           return onCreateItemViewHolder(parent,viewType);
       }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder){
            //要是没数据的话
            if (hasMoreData){
                ((FooterViewHolder) holder).mProgressView.setVisibility(View.VISIBLE);
                ((FooterViewHolder) holder).mTextView.setText("正在加载...");
            }
            else {
                ((FooterViewHolder) holder).mProgressView.setVisibility(View.GONE);
                ((FooterViewHolder) holder).mTextView.setText("没有更多数据...");
            }
        } else {
            onBindItemViewHolder((VH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount() + (hasFooter ? 1 : 0);
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder{

        public final ProgressWheel mProgressView;
        public final TextView mTextView;
        public FooterViewHolder(View view) {
            super(view);
            mProgressView = (ProgressWheel) view.findViewById(R.id.progress_view);
            mTextView = (TextView) view.findViewById(R.id.tv_content);
        }
    }

    /**
     * 数据itemViewHolder的实现
     */
    public abstract VH  onCreateItemViewHolder(ViewGroup parent,int viewType);

    public abstract void onBindItemViewHolder(final VH holder, int position);

    @Override
    public int getItemViewType(int position) {
        if (position == getBasicItemCount() && hasFooter) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public List<T> getList() {
        return mList;
    }

    private int getBasicItemCount() {
        return mList.size();
    }

    public void appendToList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
    }

    public void append(T t) {
        if (t == null) {
            return;
        }
        mList.add(t);
    }

    public void appendToTop(T item) {
        if (item == null) {
            return;
        }
        mList.add(0, item);
    }

    public void appendToTopList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(0, list);
    }

    public void remove(int position) {
        if (position < mList.size() - 1 && position >= 0) {
            mList.remove(position);
        }
    }

    public void clear() {
        mList.clear();
    }

    public T getItem(int position) {
        if (position > mList.size() - 1) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean hasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        if(this.hasFooter != hasFooter) {
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }

    public boolean hasMoreData() {
        return hasMoreData;
    }

    public void setHasMoreData(boolean isMoreData) {
        if(this.hasMoreData != isMoreData) {
            this.hasMoreData = isMoreData;
            notifyDataSetChanged();
        }
    }

    public void setHasMoreDataAndFooter(boolean hasMoreData, boolean hasFooter) {
        if(this.hasMoreData != hasMoreData || this.hasFooter != hasFooter) {
            this.hasMoreData = hasMoreData;
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }
}
