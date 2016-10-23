package com.lcc.msdq.news.xtnews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcc.adapter.IndexMenuAdapter;
import com.lcc.adapter.XtNewsAdapter;
import com.lcc.base.BaseActivity;
import com.lcc.entity.XtNewsEntity;
import com.lcc.msdq.R;
import com.lcc.mvp.presenter.XtNewsPresenter;
import com.lcc.mvp.presenter.impl.XtNewsPresenterImpl;
import com.lcc.mvp.view.XtNewsView;
import com.lcc.view.loadview.LoadingLayout;

import java.util.List;

import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.TimeUtils;
import zsbpj.lccpj.view.recyclerview.listener.OnRecycleViewScrollListener;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  XtNewsActivity
 */
public class XtNewsActivity extends BaseActivity implements XtNewsAdapter.OnItemClickListener,
        XtNewsView, View.OnClickListener {
    private LoadingLayout loading_layout;
    private RecyclerView mRecyclerView;
    private XtNewsAdapter mAdapter;
    private XtNewsPresenter xtNewsPresenter;

    @Override
    protected void initView() {
        initRecycleView();
        xtNewsPresenter = new XtNewsPresenterImpl(this);
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        xtNewsPresenter.getData();
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.xt_news_layout;
    }

    @Override
    public void onItemClick(XtNewsEntity data) {

    }

    private void initRecycleView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(XtNewsActivity.this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new XtNewsAdapter();
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
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
        FrameManager.getInstance().toastPrompt("数据加载失败");
    }

    @Override
    public void getDataSuccess(List<XtNewsEntity> entities) {
        if (entities != null && entities.size() > 0) {
            mAdapter.bind(entities);
            loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
        }else {
            getDataEmpty();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
