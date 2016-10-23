package com.lcc.msdq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.lcc.base.BaseActivity;
import com.lcc.msdq.choice.ChoiceTypeoneActivity;
import com.lcc.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private List<View> views = new ArrayList<View>();
    private int[] ids = new int[]{R.drawable.zhigan,
            R.drawable.zhigan, R.drawable.zhigan,
            R.drawable.zhigan};

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharePreferenceUtil.setGuide();
        button= (Button) findViewById(R.id.button);
        for (int i = 0; i < ids.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.guid_item_image, null);
            ImageView iv_ad = (ImageView) view.findViewById(R.id.iv_ad);
            iv_ad.setImageResource(ids[i]);
            if (i == ids.length - 1) {
                iv_ad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            views.add(view);
        }
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent=null;
                String type = SharePreferenceUtil.getUserType();
                if (TextUtils.isEmpty(type)) {
                    intent = new Intent(GuideActivity.this, ChoiceTypeoneActivity.class);
                } else {
                    intent = new Intent(GuideActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });
        ViewPager viewPager = (ViewPager) findViewById(R.id.welcomevp);
        MyPageAdapter welcomeAdapter = new MyPageAdapter(views);
        viewPager.setAdapter(welcomeAdapter);
        viewPager.setOnPageChangeListener(this);
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
        return R.layout.activity_guid_page;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyPageAdapter extends PagerAdapter {
        private List<View> views;

        public MyPageAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views == null ? 0 : views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(views.get(position), 0);
            return views.get(position);
        }
    }
}
