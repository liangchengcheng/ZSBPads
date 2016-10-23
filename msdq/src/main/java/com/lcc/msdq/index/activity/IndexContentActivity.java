package com.lcc.msdq.index.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lcc.AppConstants;
import com.lcc.base.BaseActivity;
import com.lcc.msdq.R;
import com.lcc.utils.ResourcesUtils;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2016年04月21日07:17:52
 * Description: 显示内容
 */
public class IndexContentActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
,View.OnClickListener{

    public static final String KEY_URL = "url";
    public static final String IMAGE_URL = "image";
    private String url;
    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        initWebView();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        int color = ResourcesUtils.getColor(this, R.color.colorPrimary);
        progressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        url = getIntent().getStringExtra(KEY_URL);
        webView.loadUrl(AppConstants.RequestPath.ACTIVITY_CONTENT+url);
        findViewById(R.id.iv_back).setOnClickListener(this);

    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_webview;
    }

    @Override
    public void onRefresh() {
        webView.reload();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void toolbarAsBackButton(Toolbar toolbar) {
        toolbar.setBackgroundColor(getColorPrimary());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

//    /**
//     * 显示popupWindow
//     */
//    private void showPopwindow() {
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.dialog_layout, null);
//        //下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
//        PopupWindow window = new PopupWindow(view,
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setFocusable(true);
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        window.setBackgroundDrawable(dw);
//        window.setAnimationStyle(R.style.mypopwindow_anim_style);
//        window.showAtLocation(WebViewActivity.this.findViewById(R.id.root_View),
//                Gravity.BOTTOM, 0, 0);
//        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
//
//            @Override
//            public void onDismiss() {
//            }
//        });
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initWebView() {
        webView = (WebView) findViewById(R.id.webview);
        WebSettings setting = webView.getSettings();
        setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        setting.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }

        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress != 100) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
