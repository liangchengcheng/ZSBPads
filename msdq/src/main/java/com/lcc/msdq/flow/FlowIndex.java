package com.lcc.msdq.flow;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lcc.adapter.FlowMenuAdapter;
import com.lcc.adapter.IndexMenuAdapter;
import com.lcc.base.BaseActivity;
import com.lcc.entity.Article;
import com.lcc.entity.FlowIEntity;
import com.lcc.msdq.R;
import com.lcc.msdq.description.other.OtherUserProfileActivity;
import com.lcc.mvp.presenter.FlowPresenter;
import com.lcc.mvp.presenter.IndexMenuPresenter;
import com.lcc.mvp.presenter.impl.FlowPresenterImpl;
import com.lcc.mvp.presenter.impl.IndexMenuPresenterImpl;
import com.lcc.mvp.view.FlowView;
import com.lcc.view.loadview.LoadingLayout;
import java.util.List;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.TimeUtils;
import zsbpj.lccpj.view.recyclerview.listener.OnRecycleViewScrollListener;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  FlowIndex
 */
public class FlowIndex extends BaseActivity implements FlowView, SwipeRefreshLayout.OnRefreshListener,
        FlowMenuAdapter.OnItemClickListener ,View.OnClickListener{
    private LoadingLayout loading_layout;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private FlowMenuAdapter mAdapter;
    private TextView tv_title;

    protected static final int DEF_DELAY = 1000;
    protected final static int STATE_LOAD = 0;
    protected final static int STATE_NORMAL = 1;
    protected int currentState = STATE_NORMAL;
    protected long currentTime = 0;
    protected int currentPage = 1;
    private FlowPresenter mPresenter;
    private String state="me";
    private String value;
    public static final String STATE = "state";
    public static final String VALUE = "value";

    public static void startUserProfileFromLocation(String id,String value, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, FlowIndex.class);
        intent.putExtra(STATE, id);
        intent.putExtra(VALUE, value);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void initView() {
        value=getIntent().getStringExtra(VALUE);
        state=getIntent().getStringExtra(STATE);

        tv_title= (TextView) findViewById(R.id.tv_title);
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        mPresenter = new FlowPresenterImpl(this);
        initRefreshView();
        initRecycleView();
        if (state.equals("me")){
            tv_title.setText("我关注的人");
            mPresenter.getMyData(1,value);
        }else {
            tv_title.setText("我的粉丝");
            mPresenter.getYouData(1,value);
        }
    }

    private void initRefreshView() {
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshWidget.setOnRefreshListener(this);
    }

    private void initRecycleView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(FlowIndex.this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new FlowMenuAdapter();

        mAdapter.setOnItemClickListener(this);
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
                    if (state.equals("me")){
                        mPresenter.loadMyMore(currentPage,value);
                    }else{
                        mPresenter.loadYouMore(currentPage,value);
                    }
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
        return R.layout.flow_index;
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
    public void refreshDataSuccess(List<FlowIEntity> entities) {
        if (entities != null && entities.size() > 0) {
            mAdapter.bind(entities);
        }
        mSwipeRefreshWidget.setRefreshing(false);
        loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
    }

    @Override
    public void loadMoreDataSuccess(final List<FlowIEntity> entities) {
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
                if (state.equals("me")){
                    mPresenter.refreshMy(currentPage,value);
                }else{
                    mPresenter.refreshYou(currentPage,value);
                }
            }
        }, 500);
    }

    @Override
    public void onItemClick(FlowIEntity data) {
        OtherUserProfileActivity.starOtherUserProfileActivity(data.getPhone(),FlowIndex.this);
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
