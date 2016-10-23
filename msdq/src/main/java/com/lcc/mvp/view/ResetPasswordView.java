package com.lcc.mvp.view;


public interface ResetPasswordView extends BaseView{

    /**
     * 重置密码成功
     */
    void showSuccess();

    /**
     * 重置密码失败
     */
    void showResetError(String msg);

    /**
     * 发送短信成功
     */
    void showSmsSuccess();


    void showMsg(String msg);

}
