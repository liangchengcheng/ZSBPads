package com.lcc.msdq.favorite;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lcc.base.BaseActivity;
import com.lcc.frame.Propertity;
import com.lcc.msdq.R;
import com.lcc.msdq.compony.content.CodeFragment;
import com.lcc.msdq.compony.content.HrFragment;
import com.lcc.msdq.compony.content.OtherFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  FavoriteList（收藏的列表）
 */
public class FavoriteList extends BaseActivity implements View.OnClickListener {

    @Override
    protected void initView() {
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        setViewPager();
    }

    private void setViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (viewPager != null && tabLayout != null) {
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
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
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.favorite_list_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
}
