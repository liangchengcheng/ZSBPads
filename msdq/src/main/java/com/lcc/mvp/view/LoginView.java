package com.lcc.mvp.view;

public interface LoginView extends BaseView{

    /**
     * 等待
     */
    void showLoading();

    /**
     * 登录失败：错误信息
     */
    void showLoginFail(String msg);

    /**
     * 登录成功
     */
    void loginSuccess();
}
