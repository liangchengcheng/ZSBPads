package com.lcc.msdq;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lcc.base.BaseActivity;

import net.youmi.android.AdManager;
import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;

import zsbpj.lccpj.frame.ImageManager;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  AboutActivity(关于作者)
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private ImageView about_avatar_iv;

    @Override
    protected void initView() {
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        about_avatar_iv = (ImageView) findViewById(R.id.about_avatar_iv);
        ImageManager.getInstance().loadCircleResImage(AboutActivity.this, R.drawable.touxs, about_avatar_iv);
        runApp();
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_about;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guillotine_hamburger:
                finish();
                break;
        }
    }
    /**
     * 跑应用的逻辑
     */
    private void runApp() {
        //初始化SDK
        AdManager.getInstance(AboutActivity.this).init("3d1578baf5b6f77d", "61a61a1f986043db", false, true);
        //设置开屏
        setupSplashAd();
    }

    /**
     * 设置开屏广告
     */
    private void setupSplashAd() {
        // 实例化 LayoutParams（重要）
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        // 设置广告条的悬浮位置
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // 这里示例为右下角

        // 获取广告条
        View bannerView = BannerManager.getInstance(AboutActivity.this)
                .getBannerView(new BannerViewListener() {
                    @Override
                    public void onRequestSuccess() {

                    }

                    @Override
                    public void onSwitchBanner() {

                    }

                    @Override
                    public void onRequestFailed() {

                    }
                });

        // 调用 Activity 的 addContentView 函数
        ((Activity) this).addContentView(bannerView, layoutParams);
    }




}
