package com.lcc.msdq.compony.answer;

import android.widget.ImageView;

import com.lcc.base.BaseActivity;
import com.lcc.msdq.R;

import zsbpj.lccpj.frame.ImageManager;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2016年08月10日22:12:39
 * Description:  PhotoActivity
 */
public class PhotoActivity extends BaseActivity {

    public static final String url = "url";
    private String URL;
    private  ImageView iv_content;

    @Override
    protected void initView() {
        URL = getIntent().getStringExtra(url);
        iv_content= (ImageView) findViewById(R.id.iv_content);
        ImageManager.getInstance().loadUrlImage(PhotoActivity.this, URL, iv_content);
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.layout_photo;
    }
}
