package com.lcc.msdq.news.replaynews;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lcc.adapter.ReplayAdapter;
import com.lcc.adapter.UserGoodAdapter;
import com.lcc.base.BaseActivity;
import com.lcc.entity.Comments;
import com.lcc.entity.UserGood;
import com.lcc.msdq.R;
import com.lcc.mvp.presenter.ReplayPresenter;
import com.lcc.mvp.presenter.UserGoodPresenter;
import com.lcc.mvp.presenter.impl.ReplayPresenterImpl;
import com.lcc.mvp.presenter.impl.UserGoodPresenterImpl;
import com.lcc.mvp.view.ReplayView;
import com.lcc.mvp.view.UserGoodView;
import com.lcc.view.loadview.LoadingLayout;

import java.util.List;

import zsbpj.lccpj.frame.FrameManager;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  ReplayActivity（回复的评论的列表）
 */
public class ReplayActivity extends BaseActivity implements ReplayView,
        ReplayAdapter.OnItemClickListener, View.OnClickListener {
    private LoadingLayout loading_layout;
    private RecyclerView mRecyclerView;
    private ReplayAdapter mAdapter;
    private ReplayPresenter xtNewsPresenter;

    @Override
    protected void initView() {
        initRecycleView();
        xtNewsPresenter = new ReplayPresenterImpl(this);
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("未读评论");
        xtNewsPresenter.getData();
    }

    private void initRecycleView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ReplayActivity.this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ReplayAdapter();
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_z_news;
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
    public void getDataSuccess(List<Comments> entities) {
        if (entities != null && entities.size() > 0) {
            mAdapter.bind(entities);
            loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
        } else {
            getDataEmpty();
        }
    }

    @Override
    public void onItemClick(Comments entities) {

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
