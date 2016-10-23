package com.lcc.msdq.look.fav;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;
import com.lcc.adapter.CompanyAnswerAdapter;
import com.lcc.base.BaseActivity;
import com.lcc.entity.CompanyAnswer;
import com.lcc.entity.CompanyTest;
import com.lcc.entity.FavEntity;
import com.lcc.frame.Propertity;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.R;
import com.lcc.msdq.area.LoginDialogFragment;
import com.lcc.msdq.comments.CommentsActivity;
import com.lcc.msdq.compony.answer.AnswerAddActivity;
import com.lcc.msdq.compony.answer.CompanyAnswerWebView;
import com.lcc.msdq.compony.answer.PhotoActivity;
import com.lcc.mvp.presenter.CompanyAnswerPresenter;
import com.lcc.mvp.presenter.LookCompanyAnswerPresenter;
import com.lcc.mvp.presenter.impl.CompanyAnswerPresenterImpl;
import com.lcc.mvp.presenter.impl.LookCompanyAnswerPresenterImpl;
import com.lcc.mvp.view.CompanyAnswerView;
import com.lcc.mvp.view.LookCompanyAnswerView;
import com.lcc.view.loadview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.TimeUtils;
import zsbpj.lccpj.view.recyclerview.listener.OnRecycleViewScrollListener;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  收藏的公司的问题
 */
public class LookComQuestionIndexActivity extends BaseActivity implements LookCompanyAnswerView,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, CompanyAnswerAdapter.OnImageClickListener,
        CompanyAnswerAdapter.OnFavClickListener, CompanyAnswerAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CompanyAnswerAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private LoadingLayout loading_layout;
    private FloatingActionMenu floatingMenu;

    private LookCompanyAnswerPresenter mPresenter;
    protected static final int DEF_DELAY = 1000;
    protected final static int STATE_LOAD = 0;
    protected final static int STATE_NORMAL = 1;
    protected int currentState = STATE_NORMAL;
    protected long currentTime = 0;
    protected int currentPage = 1;
    private boolean isfavEntity;
    private CompanyTest companyTest;
    public static final String ID = "id";
    private FavEntity favEntity;
    private String fid;

    public static void startLookQuestionsActivity(FavEntity favEntity, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, LookComQuestionIndexActivity.class);
        intent.putExtra(ID, favEntity);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void initView() {
        mPresenter = new LookCompanyAnswerPresenterImpl(this);
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        favEntity = (FavEntity) getIntent().getSerializableExtra(ID);
        fid = favEntity.getNid();

        floatingMenu = (FloatingActionMenu) findViewById(R.id.floatingMenu);
        findViewById(R.id.floatingComment).setOnClickListener(this);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        initRefreshView();
        initRecycleView();
        mPresenter.getData(currentPage, fid);
    }

    private void initRefreshView() {
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshWidget.setOnRefreshListener(this);
    }

    private void initRecycleView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new CompanyAnswerAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnFavClickListener(this);
        mAdapter.setOnImageClickListener(this);
        findViewById(R.id.floatingfabu).setOnClickListener(this);
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
                    mPresenter.loadMore(currentPage, fid);
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
        return R.layout.activity_answer_index;
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
            FrameManager.getInstance().toastPrompt("刷新数据失败");
        } else {
            FrameManager.getInstance().toastPrompt("加载数据失败");
        }
    }

    @Override
    public void refreshView(List<CompanyAnswer> entities, CompanyTest companyTest) {
        if (companyTest == null) {
           return;
        }

        this.companyTest = companyTest;
        if (entities != null && entities.size() > 0) {
            List<Object> objects = new ArrayList<>();
            objects.add(companyTest);
            for (int i = 0; i < entities.size(); i++) {
                objects.add(entities.get(i));
            }
            mAdapter.bind(objects);
            mAdapter.setFav(isfavEntity);
        }
        mSwipeRefreshWidget.setRefreshing(false);
        loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
    }

    @Override
    public void loadMoreView(final List<CompanyAnswer> entities) {
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
    public void isHaveFav(boolean isfavEntity) {
        this.isfavEntity = isfavEntity;
        if (mAdapter != null) {
            mAdapter.setFav(isfavEntity);
        }
    }

    @Override
    public void FavSuccess() {
        changeFavState(true);
        Fav();
    }

    @Override
    public void FavFail(String msg) {
        FrameManager.getInstance().toastPrompt("收藏失败");
    }

    @Override
    public void UnFavSuccess() {
        changeFavState(false);
        UnFav();
    }

    @Override
    public void UnFavFail(String msg) {
        FrameManager.getInstance().toastPrompt("取消收藏失败");
    }

    private void changeFavState(boolean state) {
        this.isfavEntity = state;
        if (mAdapter != null) {
            mAdapter.setFav(isfavEntity);
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshWidget.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = 1;
                mSwipeRefreshWidget.setRefreshing(true);
                mPresenter.refresh(currentPage, fid);
            }
        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        menu.findItem(R.id.action_share).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.removeItem(R.id.action_use_browser);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                break;

            case R.id.action_use_browser:
                startActivity(new Intent(LookComQuestionIndexActivity.this, CommentsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        String user_name = null;
        switch (v.getId()) {
            case R.id.tv_sc:
                startActivity(new Intent(LookComQuestionIndexActivity.this, CommentsActivity.class));
                break;

            case R.id.fabButton:
                user_name = DataManager.getUserName();
                if (TextUtils.isEmpty(user_name)) {
                    LoginDialogFragment dialog = new LoginDialogFragment();
                    dialog.show(LookComQuestionIndexActivity.this.getFragmentManager(), "loginDialog");
                    floatingMenu.close(false);
                    return;
                }

                intent = new Intent(LookComQuestionIndexActivity.this, AnswerAddActivity.class);
                intent.putExtra("fid", fid);
                startActivity(intent);
                break;

            case R.id.floatingfabu:
                user_name = DataManager.getUserName();
                if (TextUtils.isEmpty(user_name)) {
                    LoginDialogFragment dialog = new LoginDialogFragment();
                    dialog.show(LookComQuestionIndexActivity.this.getFragmentManager(), "loginDialog");
                    floatingMenu.close(false);
                    return;
                }

                intent = new Intent(LookComQuestionIndexActivity.this, AnswerAddActivity.class);
                intent.putExtra("fid", fid);
                startActivity(intent);
                floatingMenu.close(false);
                break;

            //发布评论
            case R.id.floatingComment:
                CommentsActivity.startCommentsActivity(fid, Propertity.COM.QUESTION,companyTest.getAuthor(),
                        LookComQuestionIndexActivity.this);
                floatingMenu.close(false);
                break;

            //关闭
            case R.id.guillotine_hamburger:
                finish();
                break;
        }
    }

    @Override
    public void onFavClick() {
        String user_name = DataManager.getUserName();
        if (TextUtils.isEmpty(user_name)) {
            LoginDialogFragment dialog = new LoginDialogFragment();
            dialog.show(LookComQuestionIndexActivity.this.getFragmentManager(), "loginDialog");
            floatingMenu.close(false);
            return;
        }

        if (!isfavEntity) {
            mPresenter.Fav(companyTest, Propertity.COM.QUESTION);
        } else {
            mPresenter.UnFav(companyTest);
        }
    }

    @Override
    public void onItemClick(CompanyAnswer data) {
        CompanyAnswerWebView.startCompanyAnswerWebView(LookComQuestionIndexActivity.this, data, companyTest);
    }

    @Override
    public void OnImageClick(String url) {
        Intent intent = new Intent(LookComQuestionIndexActivity.this, PhotoActivity.class);
        intent.putExtra(PhotoActivity.url, url);
        startActivity(intent);
    }

    @Override
    public void checkToken() {
        getToken();
    }
}
