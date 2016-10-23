package com.lcc.mvp.view;

public interface SignUpView extends BaseView{

    void showVerifyError(String errorMsg);

    void showVerifySuccess();

    void showSignUpError(String msg);

    void signUpSuccess();

    void showMsg(String msg);
}
