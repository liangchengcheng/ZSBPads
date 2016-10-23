package com.lcc.msdq.choice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.lcc.base.BaseActivity;
import com.lcc.entity.AreaInfo;
import com.lcc.msdq.R;
import com.lcc.msdq.compony.CompanyAddActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;


public class ChoiceAreaSelectActivity extends BaseActivity implements
        AreaFragment.OnFragmentInteractionListener {

    private Fragment oneFragment;
    private Fragment twoFragment;
    private Map map = new HashMap();

    public static final String NAME = "name";


    public static void startAreaSelectActivity(String cn, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, ChoiceAreaSelectActivity.class);
        intent.putExtra(NAME, cn);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        oneFragment = AreaFragment.newInstance("");
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, oneFragment).commit();
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
        return R.layout.activity_area_select;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                } else {
                    finish();
                }
                break;
        }
        return true;
    }


    /**
     * 处理交互，hide前一个fragment，并且调用addToBackStack让Fragment可以点击back的时候显示前一个fragment
     * 如果是第三级地区则直接返回地区选择数据给上个Activity
     *
     * @param areaInfo 被点击的地区信息
     */
    @Override
    public void onFragmentInteraction(AreaInfo areaInfo) {
        if (areaInfo == null) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int level = areaInfo.getLevel();
        switch (level) {
            case 1:
                map.put("provId", areaInfo.getId());
                map.put("provName", areaInfo.getAreaName());
                if (areaInfo.isLeaf()) {
                    nextPage(map);
                } else {
                    transaction.hide(oneFragment);
                    transaction.add(R.id.content, twoFragment = AreaFragment.
                            newInstance(areaInfo.getAreaCode() + "")).addToBackStack(null).commit();
                }
                break;

            case 2:
                map.put("cityId", areaInfo.getId());
                map.put("cityName", areaInfo.getAreaName());
                if (areaInfo.isLeaf()) {
                    nextPage(map);
                } else {
                    transaction.hide(twoFragment);
                    transaction.add(R.id.content, AreaFragment.
                            newInstance(areaInfo.getAreaCode() + "")).addToBackStack(null).commit();
                }
                break;

            case 3:
                map.put("districtId", areaInfo.getId());
                map.put("districtName", areaInfo.getAreaName());
                nextPage(map);
                break;
        }

    }

    private void nextPage(Map map) {
        Intent intent = new Intent();
        intent.putExtra("addressInfo", (Serializable) map);
        setResult(12,intent);
        finish();
    }

}
