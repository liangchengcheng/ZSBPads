package com.lcc.msdq.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lcc.App;
import com.lcc.base.BaseActivity;
import com.lcc.db.test.UserInfo;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.MainActivity;
import com.lcc.msdq.R;
import com.lcc.msdq.choice.ChoiceTypeoneActivity;
import com.lcc.mvp.presenter.LoginPresenter;
import com.lcc.mvp.presenter.impl.LoginPresenterImpl;
import com.lcc.mvp.view.LoginView;
import com.lcc.utils.FormValidation;
import com.lcc.utils.KeyboardUtils;
import com.lcc.utils.SharePreferenceUtil;
import com.lcc.utils.TextWatcher;
import com.lcc.utils.WidgetUtils;

import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.view.simplearcloader.ArcConfiguration;
import zsbpj.lccpj.view.simplearcloader.SimpleArcDialog;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {
    private LoginPresenter mPresenter;
    //用户名和密码
    private TextInputLayout mTextInputLayoutPhone;
    private TextInputLayout mTextInputLayoutPassword;
    //简单的登录时候的等待的对话框
    private SimpleArcDialog mDialog;
    private String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.addActivity(this);
    }

    @Override
    protected void initView() {
        from = getIntent().getStringExtra("from");
        Button mButtonSign = (Button) findViewById(R.id.button_sign);
        if (mButtonSign != null) {
            mButtonSign.setOnClickListener(this);
        }
        mPresenter = new LoginPresenterImpl(this);
        findViewById(R.id.textView_create_account).setOnClickListener(this);
        findViewById(R.id.textView_reset_password).setOnClickListener(this);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(this);
        mTextInputLayoutPhone = (TextInputLayout) findViewById(R.id.textInputLayout_phone);
        mTextInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayout_password);
    }

    /**
     * 对用户名和密码的基本的校验
     */
    public boolean valid(String phone, String password) {
        if (!FormValidation.isMobile(phone)) {
            WidgetUtils.requestFocus(mTextInputLayoutPhone.getEditText());
            FrameManager.getInstance().toastPrompt(getResources().getString( R.string.msg_error_phone));
            return true;
        }
        if (!FormValidation.isSimplePassword(password)) {
            WidgetUtils.requestFocus(mTextInputLayoutPassword.getEditText());
            FrameManager.getInstance().toastPrompt(getResources().getString( R.string.msg_error_password));
            return true;
        }
        return false;
    }

    /**
     * 进行基本的校验，成功的话就登录
     */
    public void login() {
        KeyboardUtils.hide(this);
        String phone = mTextInputLayoutPhone.getEditText().getText().toString();
        String password = mTextInputLayoutPassword.getEditText().getText().toString();
        if (valid(phone, password))
            return;
        mPresenter.login(phone, password);
    }

    @Override
    protected boolean Open() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    public void showLoading() {
        mDialog = new SimpleArcDialog(this);
        ArcConfiguration arcConfiguration = new ArcConfiguration(this);
        arcConfiguration.setText("正在登录...");
        mDialog.setConfiguration(arcConfiguration);
        mDialog.show();
    }

    @Override
    public void showLoginFail(String msg) {
        closeDialog();
        FrameManager.getInstance().toastPrompt("登录失败" + msg);
    }

    @Override
    public void loginSuccess() {
        closeDialog();
        FrameManager.getInstance().toastPrompt("登录成功");
        Intent intent = null;
        if (!TextUtils.isEmpty(from)) {
            UserInfo userInfo = DataManager.getUserInfo();
            if (userInfo == null || TextUtils.isEmpty(userInfo.getZy())) {
                intent = new Intent(LoginActivity.this, ChoiceTypeoneActivity.class);
                intent.putExtra("flag", "login");
            } else {
                intent = new Intent(LoginActivity.this, MainActivity.class);
            }

            startActivity(intent);
            finish();
            App.exit();
        } else {
            intent = new Intent();
            setResult(101, intent);
            finish();
        }
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.textView_create_account:
                intent = new Intent(LoginActivity.this, CodeActivity.class);
                intent.putExtra("from", "r");
                startActivity(intent);
                break;

            case R.id.button_sign:
                login();
                break;

            case R.id.textView_reset_password:
                intent = new Intent(LoginActivity.this, CodeActivity.class);
                intent.putExtra("from", "c");
                startActivity(intent);
                break;

            case R.id.guillotine_hamburger:
                finish();
                break;
        }
    }

    private void setEditTextError(TextInputLayout layout, int msgId) {
        layout.setErrorEnabled(true);
        layout.setError(getString(msgId));
    }

    @Override
    public void checkToken() {
        getToken();
    }
}
