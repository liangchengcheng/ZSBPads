package com.lcc.msdq.area;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import com.lcc.msdq.R;
import com.lcc.msdq.login.SignUpActivity;
import com.lcc.mvp.presenter.LoginPresenter;
import com.lcc.mvp.presenter.impl.LoginPresenterImpl;
import com.lcc.mvp.view.LoginView;
import com.lcc.utils.FormValidation;
import com.lcc.utils.KeyboardUtils;
import com.lcc.utils.WidgetUtils;
import zsbpj.lccpj.frame.FrameManager;

public class LoginDialogFragment extends DialogFragment implements LoginView, View.OnClickListener {
	//MVP结构的 P层的实现
	private LoginPresenter mPresenter;
	//用户名和密码
	private TextInputLayout mTextInputLayoutPhone;
	private TextInputLayout mTextInputLayoutPassword;
	//等待
	private View loading;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.login_layout, null);
		initView(view);
		builder.setView(view);
		return builder.create();
	}

	private void initView(View view){
		mPresenter = new LoginPresenterImpl(this);
		loading = view.findViewById(R.id.loading);
		mTextInputLayoutPhone = (TextInputLayout) view.findViewById(R.id.textInputLayout_phone);
		mTextInputLayoutPassword = (TextInputLayout) view.findViewById(R.id.textInputLayout_password);
		view.findViewById(R.id.btn_login).setOnClickListener(this);
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
		KeyboardUtils.hide(getActivity());
		String phone = mTextInputLayoutPhone.getEditText().getText().toString();
		String password = mTextInputLayoutPassword.getEditText().getText().toString();
		if (valid(phone, password))
			return;
		mPresenter.login(phone, password);
	}

	@Override
	public void showLoading() {
		loading.setVisibility(View.VISIBLE);
	}

	@Override
	public void showLoginFail(String msg) {
		loading.setVisibility(View.GONE);
		FrameManager.getInstance().toastPrompt("登录失败" + msg);
	}

	@Override
	public void loginSuccess() {
		loading.setVisibility(View.GONE);
		FrameManager.getInstance().toastPrompt("登录成功");
		dismiss();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.buttonSignUp:
				Intent intent = new Intent(getActivity(), SignUpActivity.class);
				intent.putExtra("fragment","flag");
				startActivity(intent);
				dismiss();
				break;

			case R.id.btn_login:
				login();
				break;
		}
	}

	@Override
	public void checkToken() {

	}
}
