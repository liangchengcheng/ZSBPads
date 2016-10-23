package com.lcc.msdq.index.article;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lcc.base.BaseActivity;
import com.lcc.entity.Article;
import com.lcc.entity.ArticleContent;
import com.lcc.frame.Propertity;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.R;
import com.lcc.msdq.area.LoginDialogFragment;
import com.lcc.msdq.comments.CommentsActivity;
import com.lcc.mvp.presenter.MenuContentPresenter;
import com.lcc.mvp.presenter.impl.MenuContentPresenterImpl;
import com.lcc.mvp.view.MenuContentView;
import com.lcc.view.MyScrollView;
import com.lcc.view.MyWebView;
import com.lcc.view.loadview.LoadingLayout;

import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.frame.ImageManager;
import zsbpj.lccpj.utils.LogUtils;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  IndexMenuWebView
 */
public class IndexMenuWebView extends BaseActivity implements MenuContentView, View.OnClickListener,
        MyWebView.OnScrollChangedCallback, View.OnScrollChangeListener {
    public static final String DATA = "data";
    private MenuContentPresenter indexContentPresenter;
    private Article article;
    private ArticleContent articleContent;
    boolean isBottomShow = true;

    private MyWebView webView;
    private ImageView ivZhihuStory;
    private LoadingLayout loading_layout;
    private TextView tv_question, tv_source;
    private ImageView iv_state;
    private LinearLayout ll_bottom_state;
    private TextView tv_comments;
    private View llDetailBottom;
    private MyScrollView nest;

    public static void startIndexMenuWebView(Activity startingActivity, Article article) {
        Intent intent = new Intent(startingActivity, IndexMenuWebView.class);
        intent.putExtra(DATA, article);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        getData();
    }

    private void initData() {
        articleContent = new ArticleContent();
        article = (Article) getIntent().getSerializableExtra(DATA);
        indexContentPresenter = new MenuContentPresenterImpl(this);
        if (!article.getL_num().equals("0")) {
            tv_comments.setText(article.getL_num());
        }
    }

    @Override
    protected void initView() {
        nest = (MyScrollView) findViewById(R.id.nest);
        nest.setOnScrollChangeListener(this);
        llDetailBottom = findViewById(R.id.llDetailBottom);
        findViewById(R.id.ll_issc).setOnClickListener(this);
        findViewById(R.id.ll_comments).setOnClickListener(this);
        findViewById(R.id.tv_to_comments).setOnClickListener(this);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        tv_comments = (TextView) findViewById(R.id.tv_comments);
        ll_bottom_state = (LinearLayout) findViewById(R.id.ll_bottom_state);
        iv_state = (ImageView) findViewById(R.id.iv_state);
        tv_question = (TextView) findViewById(R.id.tv_question);
        loading_layout = (LoadingLayout) findViewById(R.id.loading_layout);
        ivZhihuStory = (ImageView) findViewById(R.id.user_head);

        webView = (MyWebView) findViewById(R.id.webView);
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
        settings.setDisplayZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setOnScrollChangedCallback(this);
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.index_menu_webview;
    }

    private void getData() {
        Loading();
        indexContentPresenter.getArticleContent(article.getMid());
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
    public void Loading() {
        loading_layout.setLoadingLayout(LoadingLayout.NETWORK_LOADING);
    }

    @Override
    public void getFail(String msg) {
        loading_layout.setLoadingLayout(LoadingLayout.LOADDATA_ERROR);
    }

    @Override
    public void getSuccess(ArticleContent result) {
        try {
            articleContent = result;
            if (TextUtils.isEmpty(result.getContent())) {
                loading_layout.setLoadingLayout(LoadingLayout.NO_DATA);
            } else {
                //判断是否有介绍的图片
                String head_img = article.getImage_url();
                if (TextUtils.isEmpty(head_img)) {
                    ivZhihuStory.setVisibility(View.GONE);
                } else {
                    ivZhihuStory.setVisibility(View.VISIBLE);
                    Glide.with(IndexMenuWebView.this).load(head_img)
                            .placeholder(R.drawable.loading1).centerCrop().into(ivZhihuStory);
                }

                //判断是否被我收藏了
                if (result.getAuthor() == null) {
                    iv_state.setBackgroundResource(R.drawable.details_page_toolbar_icon_guanxin_normal);
                } else {
                    iv_state.setBackgroundResource(R.drawable.details_page_toolbar_icon_red_guanxin_selected);
                }
                webView.loadDataWithBaseURL("about:blank", result.getContent(), "text/html", "utf-8", null);
                tv_question.setText(article.getTitle());
                loading_layout.setLoadingLayout(LoadingLayout.HIDE_LAYOUT);
            }
            ll_bottom_state.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        String user = DataManager.getUserName();
        switch (v.getId()) {
            case R.id.ll_comments:
                if (TextUtils.isEmpty(user)) {
                    LoginDialogFragment dialog = new LoginDialogFragment();
                    dialog.show(IndexMenuWebView.this.getFragmentManager(), "loginDialog");
                    return;
                }
                CommentsActivity.startCommentsActivity(article.getMid(), Propertity.Article.NAME,"",
                        IndexMenuWebView.this);
                break;

            case R.id.tv_to_comments:
                if (TextUtils.isEmpty(user)) {
                    LoginDialogFragment dialog = new LoginDialogFragment();
                    dialog.show(IndexMenuWebView.this.getFragmentManager(), "loginDialog");
                    return;
                }
                CommentsActivity.startCommentsActivity(article.getMid(), Propertity.Article.NAME, "",
                        IndexMenuWebView.this);
                break;

            case R.id.guillotine_hamburger:
                finish();
                break;

            case R.id.ll_issc:
                if (TextUtils.isEmpty(user)) {
                    LoginDialogFragment dialog = new LoginDialogFragment();
                    dialog.show(IndexMenuWebView.this.getFragmentManager(), "loginDialog");
                    return;
                }

                if (articleContent.getAuthor() != null) {
                    indexContentPresenter.UnFav(article);
                }

                if (articleContent.getAuthor() == null) {
                    indexContentPresenter.Fav(article, Propertity.Article.NAME);
                }
                break;
        }
    }

    @Override
    public void FavSuccess() {
        articleContent.setAuthor("");
        ImageManager.getInstance().loadResImage(IndexMenuWebView.this,
                R.drawable.details_page_toolbar_icon_red_guanxin_selected, iv_state);
        Fav();
    }

    @Override
    public void FavFail(String msg) {
        FrameManager.getInstance().toastPrompt("收藏失败," + msg);
    }

    @Override
    public void UnFavSuccess() {
        articleContent.setAuthor(null);
        UnFav();
        ImageManager.getInstance().loadResImage(IndexMenuWebView.this,
                R.drawable.details_page_toolbar_icon_guanxin_normal, iv_state);
    }

    @Override
    public void UnFavFail(String msg) {
        FrameManager.getInstance().toastPrompt("取消收藏失败," + msg);
    }

    @Override
    public void onScroll(int dx, int dy) {
        if (Math.abs(dy) > 4) {
            if (dy < 0 && isBottomShow) {
                isBottomShow = false;
                llDetailBottom.animate().translationY(llDetailBottom.getHeight());
            } else {
                isBottomShow = true;
                llDetailBottom.animate().translationY(0);
            }
        }
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //下移隐藏
        if (scrollY - oldScrollY > 0 && isBottomShow) {
            isBottomShow = false;
            llDetailBottom.animate().translationY(llDetailBottom.getHeight());
            //上移出现
        } else if (scrollY - oldScrollY < 0 && !isBottomShow) {
            isBottomShow = true;
            llDetailBottom.animate().translationY(0);
        }

    }

    @Override
    public void checkToken() {
        getToken();
    }
}
