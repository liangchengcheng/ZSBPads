package com.lcc.msdq.index.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lcc.AppConstants;
import com.lcc.base.BaseActivity;
import com.lcc.msdq.R;
import com.lcc.mvp.presenter.IndexContentPresenter;
import com.lcc.mvp.presenter.impl.IndexContentPresenterImpl;
import com.lcc.mvp.view.IndexContentView;

import com.lcc.view.loadview.LoadingLayout;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  活动的内容的界面
 */
public class IndexWebView extends BaseActivity implements IndexContentView, Toolbar.OnMenuItemClickListener {
    public static final String KEY_URL = "url";
    public static final String IMAGE_URL = "image";
    public static final String TITLE = "title";
    private String id;
    private String image_url;
    private String title;
    private IndexContentPresenter indexContentPresenter;

    private Toolbar toolbar;
    private WebView webView;
    private ImageView ivZhihuStory;
    private CollapsingToolbarLayout ctl;
    private NestedScrollView nest;
    private FloatingActionButton fabButton;
    private LoadingLayout loading_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        getData();
    }

    private void initData() {
        id = getIntent().getStringExtra(KEY_URL);
        image_url = getIntent().getStringExtra(IMAGE_URL);
        title = getIntent().getStringExtra(TITLE);
        indexContentPresenter = new IndexContentPresenterImpl(this);
    }

    @Override
    protected void initView() {
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        ivZhihuStory = (ImageView) findViewById(R.id.ivZhihuStory);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            toolbar.setOnMenuItemClickListener(this);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        nest = (NestedScrollView) findViewById(R.id.nest);
        fabButton = (FloatingActionButton) findViewById(R.id.fabButton);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nest.smoothScrollTo(0, 0);
            }
        });
        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        //settings.setUseWideViewPort(true);造成文字太小
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setWebChromeClient(new WebChromeClient());
        ctl = (CollapsingToolbarLayout) findViewById(R.id.ctl);
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.index_webview;
    }

    private void getData() {
        indexContentPresenter.getActivityContent(id);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            webView.getClass().getMethod("onResume").invoke(webView, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            webView.getClass().getMethod("onPause").invoke(webView, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @Override
    public void loading() {
        loading_layout.setLoadingLayout(LoadingLayout.NETWORK_LOADING);
    }

    @Override
    public void getLoginFail(String msg) {
        loading_layout.setLoadingLayout(LoadingLayout.LOADDATA_ERROR);
    }

    @Override
    public void getSuccess(String result) {

        try {
            webView.loadDataWithBaseURL("about:blank", result, "text/html", "utf-8", null);
            // webView.loadData(URLEncoder.encode(result, "utf-8"), "text/html", "utf-8");
            String head_img = image_url;
            Glide.with(IndexWebView.this)
                    .load(head_img)
                    .placeholder(R.drawable.loading1)
                    .error(R.drawable.bg_ps)
                    .centerCrop()
                    .into(ivZhihuStory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void checkToken() {
        getToken();
    }
}
