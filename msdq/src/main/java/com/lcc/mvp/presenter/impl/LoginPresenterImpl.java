package com.lcc.mvp.presenter.impl;

import android.util.Log;

import com.lcc.App;
import com.lcc.db.test.UserInfo;
import com.lcc.entity.Result;
import com.lcc.frame.data.DataManager;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.LoginModel;
import com.lcc.mvp.presenter.LoginPresenter;
import com.lcc.mvp.view.LoginView;
import com.lcc.utils.SharePreferenceUtil;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView view;
    private LoginModel model;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        model = new LoginModel();
    }

    @Override
    public void login(String phone, String pwd) {
        view.showLoading();
        model.login(phone, pwd, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.showLoginFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");

                    if (status == 1) {
                        String result = jsonObject.getString("result");
                        JSONObject json_result = new JSONObject(result);
                        SharePreferenceUtil.setUserTk(json_result.getString("tk"));
                        String user_info=json_result.getString("userinfo");

                        UserInfo userInfo = GsonUtils.changeGsonToBean(user_info, UserInfo.class);
                        DataManager.saveUserInfo(userInfo);
                        view.loginSuccess();
                    }else if (status == 2) {
                        view.showLoginFail(message);
                        view.checkToken();
                    }  else {
                        view.showLoginFail(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

