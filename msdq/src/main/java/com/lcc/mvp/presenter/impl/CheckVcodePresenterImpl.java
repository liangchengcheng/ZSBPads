package com.lcc.mvp.presenter.impl;

import com.lcc.mvp.model.CheckVcodeModel;
import com.lcc.mvp.presenter.CheckVcodePresenter;
import com.lcc.mvp.view.CheckVcodeView;
import com.squareup.okhttp.Request;

import com.lcc.frame.net.okhttp.callback.ResultCallback;

import org.json.JSONObject;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2016年10月18日21:34:19
 * Description:  验证码的校验
 */
public class CheckVcodePresenterImpl implements CheckVcodePresenter {
    private CheckVcodeView view;
    private CheckVcodeModel model;

    public CheckVcodePresenterImpl(CheckVcodeView view) {
        this.view = view;
        model = new CheckVcodeModel();
    }

    @Override
    public void checkVerifySMS(String phone, String verify_code) {
        model.checkVerifySMS(phone, verify_code, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.CheckVerifyCodeError(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {
                        view.CheckVerifyCodeSuccess();
                    }  else {
                        view.CheckVerifyCodeError(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

