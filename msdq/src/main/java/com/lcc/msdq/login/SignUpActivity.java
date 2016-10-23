package com.lcc.msdq.login;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.lcc.App;
import com.lcc.base.BaseActivity;
import com.lcc.frame.data.DataManager;
import com.lcc.msdq.MainActivity;
import com.lcc.msdq.R;
import com.lcc.msdq.choice.ChoiceTypeoneActivity;
import com.lcc.mvp.presenter.SignUpPresenter;
import com.lcc.mvp.presenter.impl.SignUpPresenterImpl;
import com.lcc.mvp.view.SignUpView;
import com.lcc.utils.FormValidation;
import com.lcc.utils.KeyboardUtils;
import com.lcc.utils.SharePreferenceUtil;
import com.lcc.utils.TextWatcher;
import com.lcc.utils.WidgetUtils;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import zsbpj.lccpj.frame.FrameManager;
import zsbpj.lccpj.view.simplearcloader.ArcConfiguration;
import zsbpj.lccpj.view.simplearcloader.SimpleArcDialog;

public class SignUpActivity extends BaseActivity implements SignUpView, View.OnClickListener {
    private TextInputLayout textInputLayout_username;
    private TextInputLayout mTextInputLayoutPassword;
    private Button mButtonSignUp;
    private SimpleArcDialog mDialog;

    private SignUpPresenter mPresenter;
    private String phone, password, username;
    private String flag;
    private String code;

    @Override
    protected void initView() {
        flag = getIntent().getStringExtra("from");
        code = getIntent().getStringExtra("code");
        phone = getIntent().getStringExtra("phone");
        mPresenter = new SignUpPresenterImpl(this);

        TextView iv_head = (TextView) findViewById(R.id.iv_head);
        iv_head.setText("用户注册");
        textInputLayout_username = (TextInputLayout) findViewById(R.id.textInputLayout_username);
        mTextInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayout_password);
        mButtonSignUp = (Button) findViewById(R.id.buttonSignUp);
        mButtonSignUp.setOnClickListener(this);
        findViewById(R.id.guillotine_hamburger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean valid(String password) {
        if (!FormValidation.isSimplePassword(password)) {
            WidgetUtils.requestFocus(mTextInputLayoutPassword.getEditText());
            FrameManager.getInstance().toastPrompt(getResources().getString(R.string.msg_error_password));
            return true;
        }
        return false;
    }

    @Override
    protected boolean Open() {
        return true;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_signups;
    }

    @Override
    public void onClick(View v) {
        KeyboardUtils.hide(this);
        password = mTextInputLayoutPassword.getEditText().getText().toString();
        username = textInputLayout_username.getEditText().getText().toString();
        if (TextUtils.isEmpty(username)) {
            FrameManager.getInstance().toastPrompt("昵称不能为空");
            return;
        }
        if (valid(password))
            return;
        switch (v.getId()) {
            case R.id.buttonSignUp:
                showDialog();
                mPresenter.signUp(phone, password, code, username);
                break;
        }
    }

    @Override
    public void showVerifyError(String errorMsg) {

    }

    @Override
    public void showVerifySuccess() {

    }

    @Override
    public void showSignUpError(String msg) {
        closeDialog();
        FrameManager.getInstance().toastPrompt("账号注册失败" + msg);
    }

    @Override
    public void signUpSuccess() {
        closeDialog();
        FrameManager.getInstance().toastPrompt("账号注册成功");
        if (!TextUtils.isEmpty(flag)) {
            String type = DataManager.getUserInfo().getZy();
            Intent intent = null;
            if (TextUtils.isEmpty(type)) {
                intent = new Intent(SignUpActivity.this, ChoiceTypeoneActivity.class);
                intent.putExtra("flag", "SignUpActivity");
            } else {
                intent = new Intent(SignUpActivity.this, MainActivity.class);
            }
            App.exit();
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    @Override
    public void showMsg(String msg) {
        FrameManager.getInstance().toastPrompt(msg);
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private void showDialog() {
        mDialog = new SimpleArcDialog(this);
        ArcConfiguration arcConfiguration = new ArcConfiguration(this);
        arcConfiguration.setText("正在注册...");
        mDialog.setConfiguration(arcConfiguration);
        mDialog.show();
    }

    @Override
    public void checkToken() {
        getToken();
    }
}
