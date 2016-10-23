package com.lcc.mvp.view;

public interface CheckVcodeView {

    void CheckVerifyCode(String phone,String code);

    void CheckVerifyCodeSuccess();

    void CheckVerifyCodeError(String msg);

}
