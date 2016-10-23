package com.lcc.msdq.choice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.lcc.App;
import com.lcc.adapter.ChoiceType1Adapter;
import com.lcc.adapter.ChoiceType2Adapter;
import com.lcc.base.BaseActivity;
import com.lcc.db.test.UserInfo;
import com.lcc.entity.Type1;
import com.lcc.entity.Type2;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.MainActivity;
import com.lcc.msdq.R;
import com.lcc.mvp.presenter.ChoiceTypePresenter;
import com.lcc.mvp.presenter.impl.ChoicePresenterImpl;
import com.lcc.mvp.view.ChoiceTypeView;
import com.lcc.utils.SharePreferenceUtil;
import com.lcc.view.loadview.LoadingLayout;

import java.util.List;

import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.CacheUtil;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.view.simplearcloader.ArcConfiguration;
import zsbpj.lccpj.view.simplearcloader.SimpleArcDialog;
import zsbpj.lccpj.view.toast.SuperCustomToast;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  选择职业
 */
public class ChoiceTypetwoActivity extends BaseActivity implements ChoiceTypeView,
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

    public static void startChoiceTypetwoActivity(Activity startingActivity, String nid, String flag) {
        Intent intent = new Intent(startingActivity, ChoiceTypetwoActivity.class);
        intent.putExtra(NID, nid);
        intent.putExtra(FLAG, flag);
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
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChoiceTypetwoActivity.this,
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
        mDialog = new SimpleArcDialog(this);
        ArcConfiguration arcConfiguration = new ArcConfiguration(this);
        arcConfiguration.setText("正在设置你的职业类型...");
        mDialog.setConfiguration(arcConfiguration);
        mDialog.show();
    }

    @Override
    public void setDataFail(String msg) {
        closeDialog();
        FrameManager.getInstance().toastPrompt("设置失败");
    }

    @Override
    public void setDataSuccess() {
        closeDialog();
        UserInfo userInfo = DataManager.getUserInfo();
        userInfo.setZy(zy);
        DataManager.editUser(userInfo);
        setSuccess();
        App.exit();
        if (TextUtils.isEmpty(flag)) {
            finish();
        } else {
            startActivity(new Intent(ChoiceTypetwoActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onItemClick(Type2 data) {
        zy = data.getS_name()+"."+data.getS_id();
        choiceTypePresenter.setDataType(zy);
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

    public void setSuccess() {
        SuperCustomToast toasts = SuperCustomToast.getInstance(getApplicationContext());
        toasts.setDefaultTextColor(Color.WHITE);
        toasts.show("设置成功", R.layout.choice_toast_item, R.id.content_toast, ChoiceTypetwoActivity.this);
    }

}
