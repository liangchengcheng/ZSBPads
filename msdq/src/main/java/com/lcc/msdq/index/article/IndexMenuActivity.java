package com.lcc.msdq.index.article;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lcc.adapter.IndexMenuAdapter;
import com.lcc.base.BaseActivity;
import com.lcc.entity.Article;
import com.lcc.msdq.R;
import com.lcc.mvp.presenter.IndexMenuPresenter;
import com.lcc.mvp.presenter.impl.IndexMenuPresenterImpl;
import com.lcc.mvp.view.IndexMenuView;
import com.lcc.view.loadview.LoadingLayout;
import java.util.List;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.TimeUtils;
import zsbpj.lccpj.view.recyclerview.listener.OnRecycleViewScrollListener;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  IndexMenuActivity
 */
public class IndexMenuActivity extends BaseActivity implements IndexMenuView, SwipeRefreshLayout.OnRefreshListener,
        IndexMenuAdapter.OnFavClickListener, IndexMenuAdapter.OnItemClickListener,View.OnClickListener{
    public static final String TYPE = "type";
    private LoadingLayout loading_layout;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private IndexMenuAdapter mAdapter;

    protected static final int DEF_DELAY = 1000;
    protected final static int STATE_LOAD = 0;
    protected final static int STATE_NORMAL = 1;
    protected int currentState = STATE_NORMAL;
    protected long currentTime = 0;
    protected int currentPage = 1;
    private IndexMenuPresenter mPresenter;
    private String type="面试准备";

    public static void startIndexMenuActivity(Activity startingActivity, String type) {
        Intent intent = new Intent(startingActivity, IndexMenuActivity.class);
        intent.putExtra(TYPE, type);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void initView() {
        type=getIntent().getStringExtra(TYPE);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText(type);
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        mPresenter = new IndexMenuPresenterImpl(this);
        initRefreshView();
        initRecycleView();
        mPresenter.getData(1,type);
    }

    private void initRefreshView() {
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshWidget.setOnRefreshListener(this);
    }

    private void initRecycleView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(IndexMenuActivity.this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new IndexMenuAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnFavClickListener(this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new OnRecycleViewScrollListener() {
            @Override
            public void onLoadMore() {
                if (currentState == STATE_NORMAL) {
                    currentState = STATE_LOAD;
                    currentTime = TimeUtils.getCurrentTime();
                    mAdapter.setHasFooter(true);
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    currentPage++;
                    mPresenter.loadMore(currentPage,type);
                }
            }
        });
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.index_menu_activity;
    }

    @Override
    public void getLoading() {
        loading_layout.setLoadingLayout(LoadingLayout.NETWORK_LOADING);
    }

    @Override
    public void getDataEmpty() {
        loading_layout.setLoadingLayout(LoadingLayout.NO_DATA);
    }

    @Override
    public void getDataFail(String msg) {
        loading_layout.setLoadingLayout(LoadingLayout.LOADDATA_ERROR);
    }

    @Override
    public void refreshOrLoadFail(String msg) {
        if (mSwipeRefreshWidget.isRefreshing()) {
            mSwipeRefreshWidget.setRefreshing(false);
            loading_layout.setLoadingLayout(LoadingLayout.LOADDATA_ERROR);
        } else {
            FrameManager.getInstance().toastPrompt(msg);
        }
    }

    @Override
    public void refreshDataSuccess(List<Article> entities) {
        if (entities != null && entities.size() > 0) {
            mAdapter.bind(entities);
        }
        mSwipeRefreshWidget.setRefreshing(false);
        loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
    }

    @Override
    public void loadMoreWeekDataSuccess(final List<Article> entities) {
        int delay = 0;
        if (TimeUtils.getCurrentTime() - currentTime < DEF_DELAY) {
            delay = DEF_DELAY;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentState = STATE_NORMAL;
                if (entities.isEmpty()) {
                    mAdapter.setHasMoreDataAndFooter(false, false);
                    FrameManager.getInstance().toastPrompt("没有更多数据...");
                } else {
                    mAdapter.appendToList(entities);
                    mAdapter.setHasMoreDataAndFooter(true, false);
                }
                mAdapter.notifyDataSetChanged();
            }
        }, delay);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshWidget.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = 1;
                mSwipeRefreshWidget.setRefreshing(true);
                mPresenter.refresh(currentPage,type);
            }
        }, 500);
    }

    @Override
    public void onOnFavClick(Article data) {
        FrameManager.getInstance().toastPrompt(data.getMid());
    }

    @Override
    public void onItemClick(Article data) {
        IndexMenuWebView.startIndexMenuWebView(IndexMenuActivity.this,data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.guillotine_hamburger:
                finish();
                break;
        }
    }

    @Override
    public void checkToken() {
        getToken();
    }
}
