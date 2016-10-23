package zsbpj.lccpj.view.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zsbpj.lccpj.R;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.TimeUtils;
import zsbpj.lccpj.view.recyclerview.adapter.LoadMoreRecyclerAdapter;
import zsbpj.lccpj.view.recyclerview.listener.OnRecycleViewScrollListener;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    LoadMoreRecyclerFragment
 */
public abstract class LoadMoreRecyclerFragment<T> extends SuperFragment {

    protected static final  int DEF_DELAY=1000;
    protected final static int STATE_LOAD=0;
    protected final static int STATE_NORMAL=1;

    protected LoadMoreRecyclerAdapter adapter;

    private RecyclerView mRecyclerView;
    protected int currentState=STATE_NORMAL;
    protected long currentTime=0;
    protected int currentPage=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_loadmore_recyclerview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        mRecyclerView.addOnScrollListener(new OnRecycleViewScrollListener() {
            @Override
            public void onLoadMore() {
                if (currentState == STATE_NORMAL) {
                    currentState = STATE_LOAD;
                    currentTime = TimeUtils.getCurrentTime();
                    adapter.setHasFooter(true);
                    mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
                    onFragmentLoadMore();
                } else {
                    FrameManager.getInstance().toastPrompt("请稍等，正在加载");
                }
            }
        });
        onFragmentCreate();
    }

    @SuppressWarnings("unchecked")
    public void showMoreData(final List<T> dataList) {
        int delay = 0;
        if (TimeUtils.getCurrentTime() - currentTime < DEF_DELAY) {
            delay = DEF_DELAY;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentState = STATE_NORMAL;
                if (dataList.isEmpty()) {
                    adapter.setHasMoreDataAndFooter(false, false);
                  FrameManager.getInstance().toastPrompt("没有更多数据...");
                } else {
                    adapter.appendToList(dataList);
                    currentPage++;
                    adapter.setHasMoreDataAndFooter(true, false);
                }
                adapter.notifyDataSetChanged();
            }
        }, delay);
    }

    public void setAdapter(LoadMoreRecyclerAdapter mAdapter) {
        this.adapter = mAdapter;
    }


    public void showLoadError(String errorMsg) {
        adapter.setHasFooter(false);
        FrameManager.getInstance().toastPrompt("加载失败");
    }

    public void showLoadError(int errorMsgId) {
        showLoadError(getString(errorMsgId));
    }

    public int getCurrentPage() {
        return currentPage;
    }

    protected abstract void onFragmentLoadMore();

    protected abstract void onFragmentCreate();

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
