package com.lcc.mvp.presenter;

public interface ResetPasswordPresenter {

    void resetPassword(String phone,String pwd,String new_pwd,String code);

    void getVerifySMS(String phone, String pwd);

}
