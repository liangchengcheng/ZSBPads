package com.lcc.msdq.news.letternews;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.lcc.adapter.LetterAdapter;
import com.lcc.adapter.UserGoodAdapter;
import com.lcc.base.BaseActivity;
import com.lcc.entity.Letter;
import com.lcc.entity.UserGood;
import com.lcc.msdq.R;
import com.lcc.mvp.presenter.LetterPresenter;
import com.lcc.mvp.presenter.UserGoodPresenter;
import com.lcc.mvp.presenter.impl.LetterPresenterImpl;
import com.lcc.mvp.presenter.impl.UserGoodPresenterImpl;
import com.lcc.mvp.view.LetterView;
import com.lcc.view.loadview.LoadingLayout;
import java.util.List;
import zsbpj.lccpj.frame.FrameManager;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  LetterActivity(个人的私信的界面)
 */
public class LetterActivity extends BaseActivity implements LetterView,
        LetterAdapter.OnItemClickListener {

    private LoadingLayout loading_layout;
    private RecyclerView mRecyclerView;
    private LetterAdapter mAdapter;
    private LetterPresenter Presenter;

    @Override
    protected void initView() {
        initRecycleView();
        Presenter = new LetterPresenterImpl(this);
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        Presenter.getData();
    }

    private void initRecycleView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(LetterActivity.this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new LetterAdapter();
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.letter_index;
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
    public void getDataSuccess(List<Letter> entities) {
        if (entities != null && entities.size() > 0) {
            mAdapter.bind(entities);
            loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
        }else {
            getDataEmpty();
        }
    }

    @Override
    public void onItemClick(Letter data) {
        LetterDetailsActivity.startLetterDetailsActivity(data,LetterActivity.this);
    }

    @Override
    public void checkToken() {
        getToken();
    }
}
