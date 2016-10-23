package com.lcc.msdq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcc.view.LivingTabsLayout;
import com.lcc.view.menu.GuillotineAnimation;

import java.util.Locale;

public class MainActivity1 extends AppCompatActivity {

    private FrameLayout root;
    private static final long RIPPLE_DURATION = 250;
    private ImageView iv_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root= (FrameLayout) findViewById(R.id.root);
        iv_menu= (ImageView) findViewById(R.id.iv_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(null);

        setSupportActionBar(toolbar); getSupportActionBar().setTitle(null);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(1);
        LivingTabsLayout tabs = (LivingTabsLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.
                findViewById(R.id.guillotine_hamburger), iv_menu)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter implements LivingTabsLayout.DrawableResIconAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.popular).toUpperCase(l);
                case 1:
                    return getString(R.string.home).toUpperCase(l);
                case 2:
                    return getString(R.string.account).toUpperCase(l);
            }
            return null;
        }

        @Override
        public int getIcon(int position) {
            switch (position) {
                case 1:
                    return R.drawable.ic_home;
                case 0:

                    return R.drawable.ic_fire;
                case 2:
                    return R.drawable.ic_account;
            }
            return -1;
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            TextView textView = (TextView) view.findViewById(R.id.section_label);
            textView.setText(getString(R.string.page, sectionNumber));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
