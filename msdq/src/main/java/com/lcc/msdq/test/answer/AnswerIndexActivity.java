package com.lcc.msdq.test.answer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.github.clans.fab.FloatingActionMenu;
import com.github.johnpersano.supertoasts.SuperToast;
import com.lcc.adapter.AnswerIndexAdapter;
import com.lcc.adapter.BaseRecyclerAdapter;
import com.lcc.adapter.UserListFavAdapter;
import com.lcc.base.BaseActivity;
import com.lcc.entity.Answer;
import com.lcc.entity.TestEntity;
import com.lcc.entity.UserListFav;
import com.lcc.frame.Propertity;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.R;
import com.lcc.msdq.area.LoginDialogFragment;
import com.lcc.msdq.comments.CommentsActivity;
import com.lcc.msdq.description.other.OtherUserProfileActivity;
import com.lcc.mvp.presenter.TestAnswerPresenter;
import com.lcc.mvp.presenter.TestPresenter;
import com.lcc.mvp.presenter.impl.TestAnswerPresenterImpl;
import com.lcc.mvp.view.TestAnswerView;
import com.lcc.utils.CoCoinToast;
import com.lcc.utils.ShareUtil;
import com.lcc.view.FullyLinearLayoutManager;
import com.lcc.view.LoadMoreRecyclerView;
import com.lcc.view.ObservableScrollView;
import com.lcc.view.StretchyTextView;
import com.lcc.view.loadview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.Md5Utils;
import zsbpj.lccpj.utils.TimeUtils;
import zsbpj.lccpj.view.recyclerview.adapter.LoadMoreRecyclerAdapter;
import zsbpj.lccpj.view.recyclerview.listener.OnRecycleViewScrollListener;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  AnswerIndexActivity
 */
public class AnswerIndexActivity extends BaseActivity implements TestAnswerView, SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener, AnswerIndexAdapter.OnItemClickListener, AnswerIndexAdapter.OnFavClickListener,
        AnswerIndexAdapter.OnImageClickListener, AnswerIndexAdapter.OnAnswerClickListener {
    public static final String ID = "id";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private LoadingLayout loading_layout;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private TextView tv_count;
    private FloatingActionMenu floatingMenu;

    protected static final int DEF_DELAY = 1000;
    protected final static int STATE_LOAD = 0;
    protected final static int STATE_NORMAL = 1;
    protected int currentState = STATE_NORMAL;
    protected long currentTime = 0;
    protected int currentPage = 1;
    private String fid;
    private TestEntity entity;
    private TestAnswerPresenter mPresenter;
    private AnswerIndexAdapter mAdapter;
    private boolean isfavEntity;
    //收藏的页码表示
    protected int favPage = 1;
    //收藏用户列表
    private RecyclerView recyclerView;
    //收藏用户适配器
    private UserListFavAdapter adapter;
    //收藏用户的标示
    protected static final int UDEF_DELAY = 1000;
    protected final static int USTATE_LOAD = 0;
    protected final static int USTATE_NORMAL = 1;
    protected int UcurrentState = USTATE_NORMAL;

    public static void startAnswerIndexActivity(TestEntity entity, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, AnswerIndexActivity.class);
        intent.putExtra(ID, entity);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void initView() {
        currentPage = 1;
        floatingMenu = (FloatingActionMenu) findViewById(R.id.floatingMenu);
        tv_count = (TextView) findViewById(R.id.tv_count);
        entity = (TestEntity) getIntent().getSerializableExtra(ID);
        tv_count.setText("共" + entity.getC_num() + "个回答");
        fid = entity.getMid();
        mPresenter = new TestAnswerPresenterImpl(this);
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        findViewById(R.id.iv_share).setOnClickListener(this);
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
        findViewById(R.id.floatingfabu).setOnClickListener(this);
        findViewById(R.id.floatingComment).setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new AnswerIndexAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnFavClickListener(this);
        mAdapter.setOnImageClickListener(this);
        mAdapter.setOnAnswerClickListener(this);
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
    public void refreshView(List<Answer> entities) {
        List<Object> objects = new ArrayList<>();
        objects.add(entity);
        if (entities != null && entities.size() > 0) {
            for (int i = 0; i < entities.size(); i++) {
                objects.add(entities.get(i));
            }
        }

        mAdapter.bind(objects);
        mAdapter.setFav(isfavEntity);
        mSwipeRefreshWidget.setRefreshing(false);
        loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
    }

    @Override
    public void loadMoreView(final List<Answer> entities) {
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
        entity.setZ_num((Integer.parseInt(entity.getZ_num()) + 1) + "");
        mAdapter.notifyDataSetChanged();
        Fav();
    }

    @Override
    public void FavFail(String msg) {
        FrameManager.getInstance().toastPrompt("收藏失败");
    }

    @Override
    public void UnFavSuccess() {
        changeFavState(false);
        int count = Integer.parseInt(entity.getZ_num());
        if (count > 0) {
            entity.setZ_num((count - 1) + "");
            mAdapter.notifyDataSetChanged();
        }
        UnFav();
    }

    @Override
    public void UnFavFail(String msg) {
        FrameManager.getInstance().toastPrompt("取消收藏失败");
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
                startActivity(new Intent(AnswerIndexActivity.this, CommentsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String user_name = null;
        Intent intent = null;
        switch (v.getId()) {

            case R.id.floatingfabu:
                user_name = DataManager.getUserName();
                if (TextUtils.isEmpty(user_name)) {
                    LoginDialogFragment dialog = new LoginDialogFragment();
                    dialog.show(AnswerIndexActivity.this.getFragmentManager(), "loginDialog");
                    floatingMenu.close(false);
                    return;
                }

                intent = new Intent(AnswerIndexActivity.this, AnswerAddActivity.class);
                intent.putExtra("fid", fid);
                startActivity(intent);
                floatingMenu.close(false);
                break;

            case R.id.iv_share:
                shareThis();
                break;

            case R.id.floatingComment:
                user_name = DataManager.getUserName();
                if (TextUtils.isEmpty(user_name)) {
                    LoginDialogFragment dialog = new LoginDialogFragment();
                    dialog.show(AnswerIndexActivity.this.getFragmentManager(), "loginDialog");
                    floatingMenu.close(false);
                    return;
                }

                CommentsActivity.startCommentsActivity(entity.getMid(), Propertity.Test.QUESTION,
                        entity.getAuthor(), AnswerIndexActivity.this);
                floatingMenu.close(false);
                break;

            case R.id.guillotine_hamburger:
                finish();
                break;

        }
    }

    @Override
    public void onItemClick(Answer data) {
        AnswerContentActivity.startAnswerContentActivity(entity, data, AnswerIndexActivity.this);
    }

    @Override
    public void onFavClick() {
        String user_name = DataManager.getUserName();
        if (TextUtils.isEmpty(user_name)) {
            LoginDialogFragment dialog = new LoginDialogFragment();
            dialog.show(AnswerIndexActivity.this.getFragmentManager(), "loginDialog");
            floatingMenu.close(false);
            return;
        }

        if (!isfavEntity) {
            mPresenter.Fav(entity, Propertity.Test.QUESTION);
        } else {
            mPresenter.UnFav(entity, Propertity.Test.QUESTION);
        }
    }

    private void changeFavState(boolean state) {
        this.isfavEntity = state;
        if (mAdapter != null) {
            mAdapter.setFav(isfavEntity);
        }
    }

    private void shareThis() {
        ShareUtil shareUtil = new ShareUtil();
        shareUtil.showShare(AnswerIndexActivity.this);
    }

    @Override
    public void onImageClick(String user_phone) {
        OtherUserProfileActivity.starOtherUserProfileActivity(user_phone, AnswerIndexActivity.this);
    }

    private View loading;
    private TextView tv_result;

    @Override
    public void OnAnswerClick(final TestEntity object) {
        favPage = 1;
        mPresenter.getUserListData(favPage, object.getMid());
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.sheet_dialog_layout, null);
        loading = view.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        tv_result = (TextView) view.findViewById(R.id.tv_result);
        tv_result.setVisibility(View.GONE);
        recyclerView = (RecyclerView) view.findViewById(R.id.bs_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserListFavAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnRecycleViewScrollListener() {
            @Override
            public void onLoadMore() {
                if (UcurrentState == USTATE_NORMAL) {
                    UcurrentState = USTATE_LOAD;
                    currentTime = TimeUtils.getCurrentTime();
                    adapter.setHasFooter(true);
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                    favPage++;
                    mPresenter.getUserListData(favPage, object.getMid());
                }
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private void showBSDialog(List<UserListFav> entities) {
        if (entities == null || entities.size() == 0) {
            tv_result.setText("暂无数据");
            tv_result.setVisibility(View.VISIBLE);
        }
        adapter.bind(entities);
    }

    @Override
    public void getUserListLoading() {

    }

    @Override
    public void getUserListEmpty() {

    }

    @Override
    public void getUserListFail(String msg) {
        loading.setVisibility(View.GONE);
        tv_result.setText("获取数据失败");
        tv_result.setVisibility(View.VISIBLE);
    }

    @Override
    public void UserListLoadFail(String msg) {
        FrameManager.getInstance().toastPrompt("加载更多数据失败...");
    }

    @Override
    public void refreshUserListView(List<UserListFav> entities) {
        loading.setVisibility(View.GONE);
        showBSDialog(entities);
    }

    @Override
    public void loadMoreUserListView(final List<UserListFav> entities) {
        int delay = 0;
        if (TimeUtils.getCurrentTime() - currentTime < DEF_DELAY) {
            delay = DEF_DELAY;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UcurrentState = USTATE_NORMAL;
                if (entities.isEmpty()) {
                    adapter.setHasMoreDataAndFooter(false, false);
                    FrameManager.getInstance().toastPrompt("没有更多数据...");
                } else {
                    adapter.appendToList(entities);
                    adapter.setHasMoreDataAndFooter(true, false);
                }
                adapter.notifyDataSetChanged();
            }
        }, delay);
    }


    @Override
    public void checkToken() {
        getToken();
    }
}
