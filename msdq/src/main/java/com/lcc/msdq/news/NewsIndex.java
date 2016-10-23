package com.lcc.msdq.news;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.lcc.base.BaseActivity;
import com.lcc.entity.NewsInfo;
import com.lcc.msdq.R;
import com.lcc.msdq.news.letternews.LetterActivity;
import com.lcc.msdq.news.replaynews.ReplayActivity;
import com.lcc.msdq.news.xtnews.XtNewsActivity;
import com.lcc.msdq.news.znews.ZNewsActivity;
import com.lcc.mvp.presenter.NewsInfoPresenter;
import com.lcc.mvp.presenter.impl.NewsInfoPresenterImpl;
import com.lcc.mvp.view.NewsInfoView;

import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.view.simplearcloader.ArcConfiguration;
import zsbpj.lccpj.view.simplearcloader.SimpleArcDialog;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  NewsIndex
 */
public class NewsIndex extends BaseActivity implements View.OnClickListener, NewsInfoView {
    private SimpleArcDialog mDialog;
    private TextView tv_replay_count, tv_latter_count, tv_z_count, tv_xt_count;

    private NewsInfoPresenter newsInfoPresenter;

    public static void startNewsIndex(Activity startingActivity) {
        Intent intent = new Intent(startingActivity, NewsIndex.class);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void initView() {
        newsInfoPresenter = new NewsInfoPresenterImpl(this);
        tv_replay_count = (TextView) findViewById(R.id.tv_replay_count);
        tv_latter_count = (TextView) findViewById(R.id.tv_latter_count);
        tv_z_count = (TextView) findViewById(R.id.tv_z_count);
        tv_xt_count = (TextView) findViewById(R.id.tv_xt_count);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        findViewById(R.id.rl_z).setOnClickListener(this);
        findViewById(R.id.rl_xt).setOnClickListener(this);
        findViewById(R.id.rl_latter).setOnClickListener(this);
        findViewById(R.id.rl_comments).setOnClickListener(this);
        newsInfoPresenter.getNewsInfo();
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_news;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guillotine_hamburger:
                finish();
                break;

            case R.id.rl_xt:
                tv_xt_count.setVisibility(View.GONE);
                startActivity(new Intent(NewsIndex.this, XtNewsActivity.class));
                break;

            case R.id.rl_z:
                tv_z_count.setVisibility(View.GONE);
                startActivity(new Intent(NewsIndex.this, ZNewsActivity.class));
                break;

            case R.id.rl_latter:
                tv_latter_count.setVisibility(View.GONE);
                startActivity(new Intent(NewsIndex.this, LetterActivity.class));
                break;

            case R.id.rl_comments:
                tv_replay_count.setVisibility(View.GONE);
                startActivity(new Intent(NewsIndex.this, ReplayActivity.class));
                break;
        }
    }

    @Override
    public void showLoading() {
        mDialog = new SimpleArcDialog(this);
        ArcConfiguration arcConfiguration = new ArcConfiguration(this);
        arcConfiguration.setText("正在获取最新数据...");
        mDialog.setConfiguration(arcConfiguration);
        mDialog.show();
    }

    @Override
    public void NewsInfoFail(String msg) {
        closeDialog();
        FrameManager.getInstance().toastPrompt("获取信息失败");
    }

    @Override
    public void NewsInfoSuccess(NewsInfo newsInfo) {
        closeDialog();
        if (newsInfo != null) {
            if (newsInfo.getReplay_data() != 0) {
                tv_replay_count.setVisibility(View.VISIBLE);
                tv_replay_count.setText(newsInfo.getReplay_data() + "");
            }

            if (newsInfo.getLatter_data() != 0) {
                tv_latter_count.setVisibility(View.VISIBLE);
                tv_latter_count.setText(newsInfo.getLatter_data() + "");
            }

            if (newsInfo.getZ_data() != 0) {
                tv_z_count.setText(newsInfo.getZ_data() + "");
                tv_z_count.setVisibility(View.VISIBLE);
            }

            if (newsInfo.getXt_data() != 0) {
                tv_xt_count.setText(newsInfo.getXt_data() + "");
                tv_xt_count.setVisibility(View.VISIBLE);
            }
        }
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void checkToken() {
        getToken();
    }
}
