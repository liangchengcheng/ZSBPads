package com.lcc.msdq.personinfo;

import android.view.View;

import com.lcc.base.BaseActivity;
import com.lcc.msdq.R;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:
 */
public class HelpActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void initView() {
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_help;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guillotine_hamburger:
                finish();
                break;
        }
    }
}
