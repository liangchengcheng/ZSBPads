package com.lcc.msdq.login;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lcc.base.BaseActivity;
import com.lcc.msdq.R;
import com.lcc.mvp.presenter.CheckVcodePresenter;

import com.lcc.mvp.presenter.impl.CheckVcodePresenterImpl;
import com.lcc.mvp.view.CheckVcodeView;
import com.lcc.utils.FormValidation;
import com.lcc.utils.KeyboardUtils;
import com.lcc.utils.WidgetUtils;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.utils.LogUtils;
import zsbpj.lccpj.view.simplearcloader.ArcConfiguration;
import zsbpj.lccpj.view.simplearcloader.SimpleArcDialog;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2016年10月11日08:08:36
 * Description:  CodeActivity
 */
public class CodeActivity extends BaseActivity implements View.OnClickListener ,CheckVcodeView{
    private int verifyCodeCountdown = 60;
    protected Handler taskHandler = new Handler();
    private TextView mButtonSendVerifyCode;
    private static final int DELAY_MILLIS = 1 * 1000;
    private EditText mEditTextVerifyCode;
    private String flag;
    private String phone;
    private String code;
    private SimpleArcDialog mDialog;
    private TextInputLayout mTextInputLayoutPhone;
    private CheckVcodePresenter presenter;

    @Override
    protected void initView() {
        SMSSDK.initSDK(this, "11cc5d753865c", "3c6cdfb8371e181a03f8a27f217e2043", true);
        EventHandler eh2 = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler2.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh2);
        flag = getIntent().getStringExtra("from");
        presenter = new CheckVcodePresenterImpl(this);
        mTextInputLayoutPhone = (TextInputLayout) findViewById(R.id.textInputLayout_phone);
        mButtonSendVerifyCode = (TextView) findViewById(R.id.button_send_verify_code);
        mButtonSendVerifyCode.setOnClickListener(this);
        mEditTextVerifyCode = (EditText) findViewById(R.id.mEditTextVerifyCode);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.button_send_verify_code).setOnClickListener(this);
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_code;
    }


    Handler mHandler2 = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(getApplicationContext(), "获取验证码成功", Toast.LENGTH_SHORT).show();
                    showVerifySuccess();
                }

                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Intent intent = null;
                    Toast.makeText(getApplicationContext(), "验证成功", Toast.LENGTH_SHORT).show();
                    if (!TextUtils.isEmpty(flag)) {
                        if (flag.equals("r")) {
                            intent = new Intent(CodeActivity.this, SignUpActivity.class);
                        } else {
                            intent = new Intent(CodeActivity.this, ResetPasswordActivity.class);
                        }
                    }
                    intent.putExtra("phone", phone);
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
                        Toast.makeText(CodeActivity.this, des, Toast.LENGTH_SHORT).show();
                        closeDialog();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            closeDialog();
        }
    };

    public void showVerifySuccess() {
        mButtonSendVerifyCode.setClickable(false);
        taskHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (verifyCodeCountdown == 0) {
                    mButtonSendVerifyCode.setClickable(true);
                    mButtonSendVerifyCode.setText(R.string.msg_get_verify_code);
                    return;
                }
                mButtonSendVerifyCode.setText(verifyCodeCountdown + getString(R.string.msg_verify_code_point));
                verifyCodeCountdown--;
                taskHandler.postDelayed(this, DELAY_MILLIS);
            }
        }, DELAY_MILLIS);
    }

    @Override
    public void onClick(View v) {
        KeyboardUtils.hide(this);
        phone = mTextInputLayoutPhone.getEditText().getText().toString();
        if (TextUtils.isEmpty(phone)) {
            FrameManager.getInstance().toastPrompt("手机号不能为空");
            return;
        }
        switch (v.getId()) {
            case R.id.button_send_verify_code:
                showDialog("获取验证码");
                SMSSDK.getVerificationCode("86", phone);
                break;

            case R.id.buttonSignUp:
                code = mEditTextVerifyCode.getText().toString();
                if (TextUtils.isEmpty(code)||code.length()!=4){
                    FrameManager.getInstance().toastPrompt("验证码的长度不合法");
                    return;
                }
                if (FormValidation.isVerifyCode(code)) {
                    showDialog("提交验证码");
                    CheckVerifyCode(phone,code);
                } else {
                    WidgetUtils.requestFocus(mEditTextVerifyCode);
                }
                break;
        }
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private void showDialog(String msg) {
        mDialog = new SimpleArcDialog(this);
        ArcConfiguration arcConfiguration = new ArcConfiguration(this);
        arcConfiguration.setText(msg);
        mDialog.setConfiguration(arcConfiguration);
        mDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public void CheckVerifyCode(String phone, String code) {
        presenter.checkVerifySMS(phone,code);
    }

    @Override
    public void CheckVerifyCodeSuccess() {
        Intent intent = null;
        if (!TextUtils.isEmpty(flag)) {
            if (flag.equals("r")) {
                intent = new Intent(CodeActivity.this, SignUpActivity.class);
            } else {
                intent = new Intent(CodeActivity.this, ResetPasswordActivity.class);
            }
        }
        intent.putExtra("phone", phone);
        intent.putExtra("code", code);
        startActivity(intent);
        finish();
        closeDialog();
    }

    @Override
    public void CheckVerifyCodeError(String msg) {
        FrameManager.getInstance().toastPrompt("短信验证失败"+msg);
        closeDialog();
    }
}
