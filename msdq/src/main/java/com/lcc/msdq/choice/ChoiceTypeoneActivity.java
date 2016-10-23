package com.lcc.msdq.choice;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.lcc.App;
import com.lcc.adapter.ChoiceType1Adapter;
import com.lcc.base.BaseActivity;
import com.lcc.entity.Type1;
import com.lcc.msdq.R;
import com.lcc.mvp.presenter.ChoiceTypePresenter;
import com.lcc.mvp.presenter.impl.ChoicePresenterImpl;
import com.lcc.mvp.view.ChoiceTypeView;
import com.lcc.view.loadview.LoadingLayout;
import java.util.List;
import zsbpj.lccpj.utils.GsonUtils;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  ChoiceTypeoneActivity
 */
public class ChoiceTypeoneActivity extends BaseActivity implements ChoiceTypeView,
        ChoiceType1Adapter.OnItemClickListener, View.OnClickListener {
    private ChoiceTypePresenter choiceTypePresenter;
    private LoadingLayout loading_layout;
    private RecyclerView mRecyclerView;
    private ChoiceType1Adapter mAdapter;
    private String flag = "";

    @Override
    protected void initView() {
        App.addActivity(this);
        flag = getIntent().getStringExtra("flag");
        findViewById(R.id.img_error).setOnClickListener(this);
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        initRecycleView();
        choiceTypePresenter = new ChoicePresenterImpl(this);
        choiceTypePresenter.getType1();
    }

    private void initRecycleView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChoiceTypeoneActivity.this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ChoiceType1Adapter();
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.layout_activity_type1;
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
    public void getDataSuccess(String msg) {
        try {
            List<Type1> data = GsonUtils.fromJsonArray(msg, Type1.class);
            if (data == null || data.size() == 0) {
                getDataEmpty();
            }
            mAdapter.bind(data);
            loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLoading() {

    }

    @Override
    public void setDataFail(String msg) {

    }

    @Override
    public void setDataSuccess() {

    }

    @Override
    public void onItemClick(Type1 data) {
        ChoiceTypetwoActivity.startChoiceTypetwoActivity(ChoiceTypeoneActivity.this, data.getN_id(),flag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_error:
                choiceTypePresenter.getType1();
                break;
        }
    }
}
