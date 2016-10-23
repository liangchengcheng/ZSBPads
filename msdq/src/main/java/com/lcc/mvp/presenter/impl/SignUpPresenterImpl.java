package com.lcc.mvp.presenter.impl;

import android.util.Log;

import com.google.gson.JsonElement;
import com.lcc.db.test.UserInfo;
import com.lcc.frame.data.DataManager;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.LoginModel;
import com.lcc.mvp.model.SignUpModel;
import com.lcc.mvp.presenter.SignUpPresenter;
import com.lcc.mvp.view.SignUpView;
import com.lcc.utils.SharePreferenceUtil;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.LogUtils;

public class SignUpPresenterImpl implements SignUpPresenter {

    private SignUpView view;
    private SignUpModel model;

    public SignUpPresenterImpl(SignUpView view) {
        this.view = view;
        model = new SignUpModel();
    }

    @Override
    public void signUp(String phone, String pwd,String verify_code ,String username) {
        model.signUp(username,phone, pwd, verify_code, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.showSignUpError(e.getMessage());
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
                        view.signUpSuccess();
                    } else if (status == 2) {
                        view.showSignUpError(message);
                        view.checkToken();
                    } else {
                        view.showSignUpError(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getVerifySMS(String phone, String pwd) {

        model.getVerifySMS(phone, pwd, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.showVerifyError(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                view.showVerifySuccess();
            }
        });
    }

    public void autoLogin(String phone, String pwd) {
        new LoginModel().login(phone, pwd, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.showMsg("自动登录失败");
            }

            @Override
            public void onResponse(String response) {
                //将response进行一些列的操作 他不是String
                view.showMsg("自动登录成功");
            }
        });

    }
}
