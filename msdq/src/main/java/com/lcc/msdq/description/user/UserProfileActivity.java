package com.lcc.msdq.description.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.lcc.frame.Propertity;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.R;
import com.lcc.msdq.compony.content.CodeFragment;
import com.lcc.msdq.compony.content.HrFragment;
import com.lcc.msdq.compony.content.OtherFragment;
import com.lcc.msdq.favorite.ArticleFragment;
import com.lcc.msdq.flow.FlowIndex;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.frame.ImageManager;

public class UserProfileActivity extends BaseActivity implements View.OnClickListener {
    public static final String UserInfo = "UserInfo";
    private UserInfo userInfo;

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
    @Bind(R.id.iv_edit)
    ImageView iv_edit;
    @Bind(R.id.tv_fs)
    TextView tv_fs;
    @Bind(R.id.tv_gz)
    TextView tv_gz;

    public static void startUserProfileActivity(UserInfo userInfo, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, UserProfileActivity.class);
        intent.putExtra(UserInfo, userInfo);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        userInfo = (UserInfo) getIntent().getSerializableExtra(UserInfo);

        tv_fs = (TextView) findViewById(R.id.tv_fs);
        tv_gz = (TextView) findViewById(R.id.tv_gz);
        tv_me.setOnClickListener(this);
        tv_you.setOnClickListener(this);
        iv_edit.setOnClickListener(this);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        setData();
        setViewPager();
    }

    private void setData() {
        String et = userInfo.getUser_image().toString();
        if (!TextUtils.isEmpty(et)) {
            ImageManager.getInstance().loadCircleImage(UserProfileActivity.this, userInfo.getUser_image(),
                    ivUserProfilePhoto);
        } else {
            ImageManager.getInstance().loadResImage(UserProfileActivity.this,
                    R.drawable.default_user_logo, ivUserProfilePhoto);
        }

        tv_gz.setText(userInfo.getGz_num() + "/关注");
        tv_fs.setText(userInfo.getFs_num() + "/粉丝");
        tv_nickname.setText(userInfo.getNickname());
        if (TextUtils.isEmpty(userInfo.getQm())) {
            tv_gxqm.setText("这个家伙很懒，什么也没留下");
        } else {
            tv_gxqm.setText(userInfo.getQm());
        }
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
        return R.layout.activity_user_profile;
    }

    private void setViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
            tlUserProfileTabs.setupWithViewPager(viewPager);
        }
    }

    private void setupTabs() {
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_grid_on_white));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_list_white));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_place_white));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_label_white));
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(ArticleFragment.newInstance(Propertity.Article.NAME), "收藏文章");
        adapter.addFragment(ArticleFragment.newInstance(Propertity.Test.QUESTION), "收藏资料");
        adapter.addFragment(ArticleFragment.newInstance(Propertity.COM.QUESTION), "收藏真题");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_me:
                FlowIndex.startUserProfileFromLocation("me", userInfo.getPhone(), UserProfileActivity.this);
                break;

            case R.id.tv_you:
                FlowIndex.startUserProfileFromLocation("you", userInfo.getPhone(), UserProfileActivity.this);
                break;

            case R.id.iv_edit:
                Intent intent = new Intent(UserProfileActivity.this, UserEditActivity.class);
                intent.putExtra(UserEditActivity.USERINFO, userInfo);
                startActivityForResult(intent, 101);
                break;

            case R.id.guillotine_hamburger:
                finish();
                break;
        }
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

    public void onEvent(Integer event) {
        switch (event) {
            case 0x02:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == 101) {
            userInfo = DataManager.getUserInfo();
            setData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
