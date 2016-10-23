package com.lcc.msdq.comments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.lcc.adapter.CommentAdapter;
import com.lcc.base.BaseActivity;
import com.lcc.entity.Comments;
import com.lcc.entity.Replay;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.R;
import com.lcc.msdq.area.LoginDialogFragment;
import com.lcc.mvp.presenter.CommentsPresenter;
import com.lcc.mvp.presenter.impl.CommentsPresenterImpl;
import com.lcc.mvp.view.CommentsView;
import com.lcc.utils.KeyboardUtils;
import com.lcc.view.SendCommentButton;
import com.lcc.view.loadview.LoadingLayout;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.TimeUtils;
import zsbpj.lccpj.view.recyclerview.listener.OnRecycleViewScrollListener;
import zsbpj.lccpj.view.simplearcloader.ArcConfiguration;
import zsbpj.lccpj.view.simplearcloader.SimpleArcDialog;

public class CommentsActivity extends BaseActivity implements SendCommentButton.OnSendClickListener,
        CommentsView, SwipeRefreshLayout.OnRefreshListener, CommentAdapter.OnItemClickListener,
        CommentsDialog.onChoiceListener, View.OnClickListener {
    @Bind(R.id.contentRoot)
    LinearLayout contentRoot;
    @Bind(R.id.llAddComment)
    LinearLayout llAddComment;
    @Bind(R.id.etComment)
    EditText etComment;
    @Bind(R.id.btnSendComment)
    SendCommentButton btnSendComment;
    private LoadingLayout loading_layout;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private CommentsDialog dialog;
    private SimpleArcDialog mDialog;

    public static final String TYPE = "type";
    public static final String R_AUTHOR = "replay_author";
    public static final String ID = "id";
    protected static final int DEF_DELAY = 1000;
    protected final static int STATE_LOAD = 0;
    protected final static int STATE_NORMAL = 1;
    protected int currentState = STATE_NORMAL;
    protected long currentTime = 0;
    protected int currentPage = 1;
    private CommentAdapter commentsAdapter;
    private Replay replay = new Replay();
    private CommentsPresenter mPresenter;
    private String type = "";
    private String content_id;
    private String replay_at = "";

    public static void startCommentsActivity(String id, String type, String replay_author, Activity startActivity) {
        Intent intent = new Intent(startActivity, CommentsActivity.class);
        intent.putExtra(ID, id);
        intent.putExtra(R_AUTHOR, replay_author);
        intent.putExtra(TYPE, type);
        startActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPresenter = new CommentsPresenterImpl(this);
        content_id = getIntent().getStringExtra(ID);
        type = getIntent().getStringExtra(TYPE);
        replay_at = getIntent().getStringExtra(R_AUTHOR);
        if (!TextUtils.isEmpty(replay_at)) {
            replay.setReplay_author(replay_at);
        }
        replay.setNid(content_id);
        replay.setType(type);
        replay.setAuthor("");

        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        initRefreshView();
        initRecycleView();
        setupSendCommentButton();
        mPresenter.getData(1, content_id);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_comments;
    }

    private void setupSendCommentButton() {
        btnSendComment.setOnSendClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSendClickListener(View v) {
        String user_name = DataManager.getUserName();
        if (TextUtils.isEmpty(user_name)) {
            LoginDialogFragment dialog = new LoginDialogFragment();
            dialog.show(CommentsActivity.this.getFragmentManager(), "loginDialog");
            return;
        }

        if (validateComment()) {
            replay.setContent(etComment.getText().toString().trim());
            mPresenter.sendComments(replay);
        }
    }

    private boolean validateComment() {
        if (TextUtils.isEmpty(etComment.getText())) {
            btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }

        return true;
    }

    private void initRefreshView() {
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshWidget.setOnRefreshListener(this);
    }

    private void initRecycleView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(CommentsActivity.this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        commentsAdapter = new CommentAdapter();
        commentsAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(commentsAdapter);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.addOnScrollListener(new OnRecycleViewScrollListener() {
            @Override
            public void onLoadMore() {
                if (currentState == STATE_NORMAL) {
                    currentState = STATE_LOAD;
                    currentTime = TimeUtils.getCurrentTime();
                    commentsAdapter.setHasFooter(true);
                    mRecyclerView.scrollToPosition(commentsAdapter.getItemCount() - 1);
                    currentPage++;
                    mPresenter.loadMore(currentPage, content_id);
                }
            }
        });
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
    public void refreshDataSuccess(List<Comments> entities) {
        if (entities != null && entities.size() > 0) {
            commentsAdapter.bind(entities);
            loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
        } else {
            loading_layout.setLoadingLayout(LoadingLayout.NO_DATA);
        }
        mSwipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void loadMoreWeekDataSuccess(final List<Comments> entities) {
        int delay = 0;
        if (TimeUtils.getCurrentTime() - currentTime < DEF_DELAY) {
            delay = DEF_DELAY;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentState = STATE_NORMAL;
                if (entities.isEmpty()) {
                    commentsAdapter.setHasMoreDataAndFooter(false, false);
                    FrameManager.getInstance().toastPrompt("没有更多数据...");
                } else {
                    commentsAdapter.appendToList(entities);
                    commentsAdapter.setHasMoreDataAndFooter(true, false);
                }
                commentsAdapter.notifyDataSetChanged();
            }
        }, delay);
    }

    @Override
    public void rePlaying() {
        mDialog = new SimpleArcDialog(this);
        ArcConfiguration arcConfiguration = new ArcConfiguration(this);
        arcConfiguration.setText("正在发送评论...");
        mDialog.setConfiguration(arcConfiguration);
        mDialog.show();
    }

    @Override
    public void replaySuccess() {
        KeyboardUtils.hide(CommentsActivity.this);
        closeDialog();
        FrameManager.getInstance().toastPrompt("提交成功");
        onRefresh();
        etComment.setText(null);
        btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
    }

    @Override
    public void replayFail() {
        KeyboardUtils.hide(CommentsActivity.this);
        closeDialog();
        FrameManager.getInstance().toastPrompt("提交失败");
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshWidget.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage = 1;
                mSwipeRefreshWidget.setRefreshing(true);
                mPresenter.refresh(currentPage, content_id);
            }
        }, 500);
    }

    @Override
    public void onItemClick(Comments data) {
        dialog = new CommentsDialog(CommentsActivity.this, data);
        dialog.setOnChoiceListener(this);
        dialog.show();
    }

    @Override
    public void onChoice(Comments data) {
        etComment.setText("@" + data.getNickname() + " ");
        replay.setAuthor_code(data.getAuthor());
        replay.setReplay_nickname(data.getNickname());
        replay.setPid(data.getMid());
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
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
