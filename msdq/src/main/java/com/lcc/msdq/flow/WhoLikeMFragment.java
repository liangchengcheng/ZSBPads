package com.lcc.msdq.flow;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcc.adapter.JSAdapter;
import com.lcc.frame.fragment.base.BaseLazyLoadFragment;
import com.lcc.msdq.R;
import com.lcc.mvp.presenter.JSPresenter;

import zsbpj.lccpj.utils.TimeUtils;
import zsbpj.lccpj.view.recyclerview.listener.OnRecycleViewScrollListener;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description: WhoLikeMFragment(自己看的话就不用没后面的关注，但是别人看的话能关注)
 */
public class WhoLikeMFragment extends BaseLazyLoadFragment  implements
        SwipeRefreshLayout.OnRefreshListener{

    static final int ACTION_NONE = 0;
    protected static final int DEF_DELAY = 1000;
    protected final static int STATE_LOAD = 0;
    protected final static int STATE_NORMAL = 1;
    protected int currentState = STATE_NORMAL;
    protected long currentTime = 0;
    protected int currentPage = 1;

    private JSAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private JSPresenter mPresenter;

    public static WhoLikeMFragment newInstance(String fid) {
        WhoLikeMFragment mFragment = new WhoLikeMFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fid", fid);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public int initContentView() {
        return R.layout.fragment_wholikem;
    }

    @Override
    public void getBundle(Bundle bundle) {

    }

    @Override
    public void initUI(View view) {

    }

    private void initRefreshView(View view) {
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshWidget.setOnRefreshListener(this);
    }

    private void initRecycleView(View view) {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new JSAdapter();
//        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new OnRecycleViewScrollListener() {
            @Override
            public void onLoadMore() {
                if (currentState == STATE_NORMAL) {
                    currentState = STATE_LOAD;
                    currentTime = TimeUtils.getCurrentTime();
                    adapter.setHasFooter(true);
                    mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
                    currentPage++;
//                    mPresenter.loadMore(currentPage,fid,type);
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onRefresh() {

    }
}
