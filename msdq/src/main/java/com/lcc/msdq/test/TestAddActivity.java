package com.lcc.msdq.test;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.lcc.base.BaseActivity;
import com.lcc.entity.QuestionAdd;
import com.lcc.frame.Propertity;
import com.lcc.msdq.R;
import com.lcc.mvp.presenter.ComDesAddPresenter;
import com.lcc.mvp.presenter.QuestionAddPresenter;
import com.lcc.mvp.presenter.impl.QuestionAddPresenterImpl;
import com.lcc.mvp.view.QuestionAddView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.view.simplearcloader.ArcConfiguration;
import zsbpj.lccpj.view.simplearcloader.SimpleArcDialog;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  TestAddActivity
 */
public class TestAddActivity extends BaseActivity implements QuestionAddView ,View.OnClickListener,
        RadioGroup.OnCheckedChangeListener{
    public static final String ZY = "zy";
    private String zy = "";
    private List<File> files = new ArrayList<>();
    private QuestionAddPresenter presenter;
    private QuestionAdd questionAdd;

    private SimpleArcDialog mDialog;
    private EditText editText_title;
    private EditText editText_summary;
    private RadioGroup rg_options;

    public static void startTestAddActivity(Activity startingActivity, String zy) {
        Intent intent = new Intent(startingActivity, TestAddActivity.class);
        intent.putExtra(ZY, zy);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void initView() {
        zy = getIntent().getStringExtra(ZY);
        questionAdd=new QuestionAdd();
        questionAdd.setKeyword("");
        questionAdd.setSource("");
        questionAdd.setOptions(Propertity.OPTIONS.ZYZS);
        presenter = new QuestionAddPresenterImpl(this);

        rg_options= (RadioGroup) findViewById(R.id.rg_options);
        rg_options.setOnCheckedChangeListener(this);
        editText_summary= (EditText) findViewById(R.id.editText_summary);
        editText_title= (EditText) findViewById(R.id.editText_title);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.test_add_activity;
    }

    @Override
    public void adding() {
        mDialog = new SimpleArcDialog(this);
        ArcConfiguration arcConfiguration = new ArcConfiguration(this);
        arcConfiguration.setText("正在提交数据...");
        mDialog.setConfiguration(arcConfiguration);
        mDialog.show();
    }

    @Override
    public void addSuccess() {
        closeDialog();
        FrameManager.getInstance().toastPrompt("发布成功");
    }

    @Override
    public void addFail() {
        closeDialog();
        FrameManager.getInstance().toastPrompt("发布失败");
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSignUp:
                if (TextUtils.isEmpty(editText_title.getText().toString())||
                        TextUtils.isEmpty(editText_summary.getText().toString())){
                    FrameManager.getInstance().toastPrompt("有未填写的数据");
                    return;
                }

                if (editText_title.getText().toString().trim().length()<6||
                        editText_title.getText().toString().length()>16){
                    FrameManager.getInstance().toastPrompt("标题的长度不合法");
                    return;
                }

                adding();
                questionAdd.setTitle(editText_title.getText().toString());
                questionAdd.setSummary(editText_summary.getText().toString());
                questionAdd.setType(zy);
                presenter.QuestionAdd(questionAdd, files);
                break;
            case R.id.guillotine_hamburger:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId==R.id.rb_js){
            questionAdd.setOptions(Propertity.OPTIONS.ZYZS);
        }else if (checkedId==R.id.rb_rs){
            questionAdd.setOptions(Propertity.OPTIONS.RSZS);
        }else {
            questionAdd.setOptions(Propertity.OPTIONS.QT);
        }
    }

    @Override
    public void checkToken() {
        getToken();
    }
}
