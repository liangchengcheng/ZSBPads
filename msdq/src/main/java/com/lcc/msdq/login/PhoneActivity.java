package com.lcc.msdq.login;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lcc.base.BaseActivity;
import com.lcc.msdq.R;
import com.lcc.utils.FormValidation;
import com.lcc.utils.KeyboardUtils;
import com.lcc.utils.WidgetUtils;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.view.simplearcloader.ArcConfiguration;
import zsbpj.lccpj.view.simplearcloader.SimpleArcDialog;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2016年10月11日08:08:36
 * Description:  PhoneActivity
 */
public class PhoneActivity extends BaseActivity implements View.OnClickListener {
    private TextInputLayout mTextInputLayoutPhone;
    private TextView iv_head;
    private String flag;
    private SimpleArcDialog mDialog;

    @Override
    protected void initView() {
        SMSSDK.initSDK(this, "11cc5d753865c", "3c6cdfb8371e181a03f8a27f217e2043", true);
        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);
        flag = getIntent().getStringExtra("from");
        mTextInputLayoutPhone = (TextInputLayout) findViewById(R.id.textInputLayout_phone);
        findViewById(R.id.button_send_verify_code).setOnClickListener(this);
        iv_head = (TextView) findViewById(R.id.iv_head);
        if (flag.equals("r")) {
            iv_head.setText("注册账号");
        } else {
            iv_head.setText("忘记密码");
        }
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_phone;
    }

    public boolean valid(String phone) {
        if (!FormValidation.isMobile(phone)) {
            WidgetUtils.requestFocus(mTextInputLayoutPhone.getEditText());
            FrameManager.getInstance().toastPrompt(getResources().getString(R.string.msg_error_phone));
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        KeyboardUtils.hide(this);
        String phone = mTextInputLayoutPhone.getEditText().getText().toString();
        if (TextUtils.isEmpty(phone)) {
            FrameManager.getInstance().toastPrompt("手机号不能为空");
            return;
        }
        if (valid(phone)) {
            FrameManager.getInstance().toastPrompt("手机号格式不正确");
            return;
        }
        switch (v.getId()) {
            case R.id.button_send_verify_code:
                showDialog();
                SMSSDK.getVerificationCode("86", phone);
                break;
        }
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    FrameManager.getInstance().toastPrompt("获取验证码成功");
                    Intent intent = new Intent(PhoneActivity.this, CodeActivity.class);
                    intent.putExtra("from", flag);
                    intent.putExtra("phone", mTextInputLayoutPhone.getEditText().getText().toString());
                    startActivity(intent);
                    finish();


                }
            } else {
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(PhoneActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            closeDialog();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //SMSSDK.unregisterAllEventHandler();
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private void showDialog() {
        mDialog = new SimpleArcDialog(this);
        ArcConfiguration arcConfiguration = new ArcConfiguration(this);
        arcConfiguration.setText("正在发送验证码...");
        mDialog.setConfiguration(arcConfiguration);
        mDialog.show();
    }
}
