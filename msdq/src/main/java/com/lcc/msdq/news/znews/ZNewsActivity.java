package com.lcc.msdq.news.znews;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.johnpersano.supertoasts.SuperToast;
import com.lcc.adapter.UserGoodAdapter;
import com.lcc.adapter.XtNewsAdapter;
import com.lcc.base.BaseActivity;
import com.lcc.entity.UserGood;
import com.lcc.frame.Propertity;
import com.lcc.msdq.R;
import com.lcc.msdq.look.good.LookAnswerContentActivity;
import com.lcc.msdq.test.answer.AnswerContentActivity;
import com.lcc.mvp.presenter.UserGoodPresenter;
import com.lcc.mvp.presenter.XtNewsPresenter;
import com.lcc.mvp.presenter.impl.UserGoodPresenterImpl;
import com.lcc.mvp.presenter.impl.XtNewsPresenterImpl;
import com.lcc.mvp.view.UserGoodView;
import com.lcc.utils.CoCoinToast;
import com.lcc.view.loadview.LoadingLayout;

import java.util.List;

import zsbpj.lccpj.frame.FrameManager;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  ZNewsActivity（赞的列表）
 */
public class ZNewsActivity extends BaseActivity implements UserGoodView, UserGoodAdapter.OnItemClickListener, View.OnClickListener {
    private LoadingLayout loading_layout;
    private RecyclerView mRecyclerView;
    private UserGoodAdapter mAdapter;
    private UserGoodPresenter xtNewsPresenter;

    @Override
    protected void initView() {
        initRecycleView();
        xtNewsPresenter = new UserGoodPresenterImpl(this);
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        xtNewsPresenter.getData();
    }

    private void initRecycleView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ZNewsActivity.this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new UserGoodAdapter();
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
    public void getDataSuccess(List<UserGood> entities) {
        if (entities != null && entities.size() > 0) {
            mAdapter.bind(entities);
            loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
        } else {
            getDataEmpty();
        }
    }

    @Override
    public void onItemClick(UserGood entities) {
        if (entities != null) {
            FrameManager.getInstance().toastPrompt("查看功能正在开发...");
            return;
        }

        Intent intent = null;
        if (entities.getType().equals(Propertity.Test.ANSWER)) {
            intent = new Intent(ZNewsActivity.this, LookAnswerContentActivity.class);
        } else if (entities.getType().equals(Propertity.COM.ANSWER)) {

        }

        startActivity(intent);


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
