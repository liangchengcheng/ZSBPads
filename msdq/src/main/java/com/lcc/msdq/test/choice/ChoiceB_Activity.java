package com.lcc.msdq.test.choice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.lcc.App;
import com.lcc.adapter.ChoiceType2Adapter;
import com.lcc.base.BaseActivity;
import com.lcc.db.test.UserInfo;
import com.lcc.entity.Type2;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.MainActivity;
import com.lcc.msdq.R;
import com.lcc.msdq.test.TestAddActivity;
import com.lcc.mvp.presenter.ChoiceTypePresenter;
import com.lcc.mvp.presenter.impl.ChoicePresenterImpl;
import com.lcc.mvp.view.ChoiceTypeView;
import com.lcc.view.loadview.LoadingLayout;

import java.util.List;

import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.view.simplearcloader.ArcConfiguration;
import zsbpj.lccpj.view.simplearcloader.SimpleArcDialog;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  ChoiceB_Activity
 */
public class ChoiceB_Activity extends BaseActivity implements ChoiceTypeView,
        ChoiceType2Adapter.OnItemClickListener, View.OnClickListener {
    public static final String NID = "nid";
    public static final String FLAG = "flag";
    private ChoiceTypePresenter choiceTypePresenter;
    private ChoiceType2Adapter mAdapter;
    private String nid;
    private String zy;
    private String flag;

    private LoadingLayout loading_layout;
    private SimpleArcDialog mDialog;
    private RecyclerView mRecyclerView;

    public static void startChoiceB_Activity(Activity startingActivity, String nid) {
        Intent intent = new Intent(startingActivity, ChoiceB_Activity.class);
        intent.putExtra(NID, nid);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void initView() {
        nid = getIntent().getStringExtra(NID);
        flag = getIntent().getStringExtra(FLAG);
        findViewById(R.id.img_error).setOnClickListener(this);
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        initRecycleView();
        choiceTypePresenter = new ChoicePresenterImpl(this);
        choiceTypePresenter.getType2(nid);
    }

    private void initRecycleView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChoiceB_Activity.this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ChoiceType2Adapter();
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
            List<Type2> data = GsonUtils.fromJsonArray(msg, Type2.class);
            if (data == null || data.size() == 0) {
                getDataEmpty();
            } else {
                mAdapter.bind(data);
                loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLoading() {
    }

    @Override
    public void setDataFail(String msg) {
        closeDialog();
    }

    @Override
    public void setDataSuccess() {
    }

    @Override
    public void onItemClick(Type2 data) {
        zy = data.getS_id();
        TestAddActivity.startTestAddActivity(ChoiceB_Activity.this, zy);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_error:
                choiceTypePresenter.getType2(nid);
                break;
        }
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
