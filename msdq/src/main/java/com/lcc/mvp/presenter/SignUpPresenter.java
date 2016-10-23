package com.lcc.mvp.presenter;


public interface SignUpPresenter {

    void signUp(String phone, String pwd, String verify_code,String username);

    void getVerifySMS(String phone, String pwd);

    void autoLogin(String phone, String pwd);
}
