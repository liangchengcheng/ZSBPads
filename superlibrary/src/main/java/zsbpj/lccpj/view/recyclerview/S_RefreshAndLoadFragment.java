package zsbpj.lccpj.view.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zsbpj.lccpj.R;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.TimeUtils;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:   RefreshAndLoadFragment
 */
public abstract class S_RefreshAndLoadFragment<T> extends LoadMoreRecyclerFragment
        implements SwipeRefreshLayout.OnRefreshListener {

    protected final static int STATE_REFRESH=2;
    private SwipeRefreshLayout mSwipeRefreshWidget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutView(), container, false);
    }

    protected abstract int getLayoutView();


    @SuppressWarnings("unchecked")
    public void showRefreshData(final List<T> dataList){
        int delay=0;
        if (TimeUtils.getCurrentTime()-currentTime<DEF_DELAY){
            delay=DEF_DELAY;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentState = STATE_NORMAL;
                mSwipeRefreshWidget.setRefreshing(false);
                currentPage = 2;
                adapter.clear();
                adapter.appendToList(dataList);
                adapter.notifyDataSetChanged();
            }
        },delay);
    }

    public abstract void onRefreshData();

    @Override
    protected void onFragmentCreate() {
        View view=getView();
        if (view!=null){
            //此处我对view进行了非空判断
            mSwipeRefreshWidget = (SwipeRefreshLayout)view .findViewById(R.id.swipe_refresh_widget);
            mSwipeRefreshWidget.setColorSchemeResources(R.color.colorPrimary);
            mSwipeRefreshWidget.setOnRefreshListener(this);
        }
    }

    @Override
    public void onRefresh() {
        if (currentState==STATE_NORMAL){
            currentState=STATE_REFRESH;
            currentTime=TimeUtils.getCurrentTime();
            adapter.setHasFooter(true);
            onRefreshData();
        }else {
            FrameManager.getInstance().toastPrompt("正在加载数据...");
        }
    }

    public SwipeRefreshLayout getSwipeRefreshWidget() {
        return mSwipeRefreshWidget;
    }
}
