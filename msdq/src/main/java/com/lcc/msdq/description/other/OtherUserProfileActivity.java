package com.lcc.msdq.description.other;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lcc.base.BaseActivity;
import com.lcc.db.test.UserInfo;
import com.lcc.entity.GzBean;
import com.lcc.entity.otherUserInfo;
import com.lcc.frame.Propertity;
import com.lcc.msdq.R;
import com.lcc.msdq.description.user.UserEditActivity;
import com.lcc.msdq.description.user.UserProfileAdapter;
import com.lcc.msdq.favorite.ArticleFragment;
import com.lcc.msdq.flow.FlowIndex;
import com.lcc.mvp.presenter.GetUserInfoPresenter;
import com.lcc.mvp.presenter.impl.GetUserInfoPresenterImpl;
import com.lcc.mvp.view.GetUserInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.frame.ImageManager;
import zsbpj.lccpj.view.toast.SuperCustomToast;

public class OtherUserProfileActivity extends BaseActivity implements View.OnClickListener, GetUserInfoView {
    public static final String PHONE = "phone";
    private String phone;
    private GetUserInfoPresenter getUserInfoPresenter;
    private GzBean gzBean;
    private boolean state;
    private otherUserInfo otherUserInfo = new otherUserInfo();

    @Bind(R.id.tlUserProfileTabs)
    TabLayout tlUserProfileTabs;
    @Bind(R.id.ivUserProfilePhoto)
    ImageView ivUserProfilePhoto;
    @Bind(R.id.vUserDetails)
    View vUserDetails;
    @Bind(R.id.vUserStats)
    View vUserStats;
    @Bind(R.id.vUserProfileRoot)
    View vUserProfileRoot;
    @Bind(R.id.tv_nickname)
    TextView tv_nickname;
    @Bind(R.id.tv_gxqm)
    TextView tv_gxqm;
    @Bind(R.id.tv_me)
    LinearLayout tv_me;
    @Bind(R.id.tv_you)
    LinearLayout tv_you;
    @Bind(R.id.tv_fs)
    TextView tv_fs;
    @Bind(R.id.tv_gz)
    TextView tv_gz;
    @Bind(R.id.display)
    TextView display;

    public static void starOtherUserProfileActivity(String phone, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, OtherUserProfileActivity.class);
        intent.putExtra(PHONE, phone);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        phone = getIntent().getStringExtra(PHONE);
        getUserInfoPresenter = new GetUserInfoPresenterImpl(this);
        tv_me.setOnClickListener(this);
        tv_you.setOnClickListener(this);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        findViewById(R.id.display).setOnClickListener(this);
        getUserInfoPresenter.getData(phone);
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
        return R.layout.activity_other_user_profile;
    }

    private void setViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
            tlUserProfileTabs.setupWithViewPager(viewPager);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(ArticleFragment.newInstance(Propertity.Article.NAME), "收藏文章");
        adapter.addFragment(ArticleFragment.newInstance(Propertity.Test.QUESTION), "收藏资料");
        adapter.addFragment(ArticleFragment.newInstance(Propertity.COM.QUESTION), "收藏的公司");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_me:
                FlowIndex.startUserProfileFromLocation("me", phone, OtherUserProfileActivity.this);
                break;

            case R.id.tv_you:
                FlowIndex.startUserProfileFromLocation("you", phone, OtherUserProfileActivity.this);
                break;

            case R.id.guillotine_hamburger:
                finish();
                break;

            case R.id.display:
                GzClick();
                break;
        }
    }

    @Override
    public void getLoading() {

    }

    @Override
    public void getDataEmpty() {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getDataSuccess(otherUserInfo otherUserInfo) {
        if (otherUserInfo != null) {
            this.otherUserInfo = otherUserInfo;
            ImageManager.getInstance().loadCircleImage(OtherUserProfileActivity.this,
                    otherUserInfo.getUser_image(), ivUserProfilePhoto);
            tv_nickname.setText(otherUserInfo.getNickname());
            if (TextUtils.isEmpty(otherUserInfo.getQm())) {
                tv_gxqm.setText("这个家伙很懒，什么也没留下");
            } else {
                tv_gxqm.setText(otherUserInfo.getQm());
            }

            display.setVisibility(View.VISIBLE);
            tv_gz.setText(otherUserInfo.getGz_num() + "/关注");
            tv_fs.setText(otherUserInfo.getFs_num() + "/粉丝");

            setViewPager();
        }
    }

    @Override
    public void checkToken() {
        getToken();
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public void HaveGz(GzBean bean) {
        this.gzBean = bean;
        changeState(gzBean);
    }

    private void changeState(GzBean gzBean) {
        if (gzBean != null) {
            state = true;
        } else {
            state = false;
        }
        changeSate(state);
    }

    @Override
    public void GzSuccess() {
        state = true;
        Fav();
        changeSate(state);
    }

    @Override
    public void unGzSuccess() {
        state = false;
        UnFav();
        changeSate(state);
    }

    @Override
    public void GzFail(String msg) {
        FrameManager.getInstance().toastPrompt("关注失败");
    }

    @Override
    public void unGzFail(String msg) {
        FrameManager.getInstance().toastPrompt("取消关注失败");
    }

    private void changeSate(boolean isState) {
        if (isState) {
            display.setText("已关注");
        } else {
            display.setText("+关注");
        }
    }

    private void GzClick() {
        if (state) {
            getUserInfoPresenter.UnGz(otherUserInfo.getPhone());
        } else {
            getUserInfoPresenter.Gz(otherUserInfo.getPhone());
        }
    }


    public void Fav() {
        SuperCustomToast toasts = SuperCustomToast.getInstance(getApplicationContext());
        toasts.setDefaultTextColor(Color.WHITE);
        toasts.show("关注成功", R.layout.fav_toast_item, R.id.content_toast, OtherUserProfileActivity.this);
    }


    public void UnFav() {
        SuperCustomToast toasts = SuperCustomToast.getInstance(getApplicationContext());
        toasts.setDefaultTextColor(Color.WHITE);
        toasts.show("取消成功", R.layout.unfav_toast_item, R.id.content_toast, OtherUserProfileActivity.this);
    }

}
