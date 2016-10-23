package com.lcc.msdq.description.com;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcc.base.BaseActivity;
import com.lcc.entity.CompanyDescription;
import com.lcc.msdq.R;

import zsbpj.lccpj.frame.ImageManager;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  CompanyDesMain
 */
public class CompanyDesMain extends BaseActivity {
    public static final String ID="CompanyDesMain";
    private CompanyDescription companyDescription;
    private TextView com_num;

    public static void startCompanyDesMain(CompanyDescription id, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, CompanyDesMain.class);
        intent.putExtra(ID, id);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void initView() {
        companyDescription= (CompanyDescription) getIntent().getSerializableExtra(ID);
        ImageView iv_question_des= (ImageView) findViewById(R.id.iv_question_des);
        TextView tv_com_name= (TextView) findViewById(R.id.tv_com_name);
        TextView tv_com_position= (TextView) findViewById(R.id.tv_com_position);
        TextView tv_phone= (TextView) findViewById(R.id.tv_phone);
        TextView tv_summary= (TextView) findViewById(R.id.tv_summary);
        com_num= (TextView) findViewById(R.id.com_num);
        tv_com_name.setText(companyDescription.getCompany_name());
        tv_com_position.setText(companyDescription.getLocation());
        tv_phone.setText(companyDescription.getCompany_phone());
        tv_summary.setText(companyDescription.getCompany_description());
        com_num.setText(companyDescription.getC_num());

        ImageManager.getInstance().loadCircleImage(CompanyDesMain.this,
                companyDescription.getCompany_image(),iv_question_des);
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_com_des;
    }
}
