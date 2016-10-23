package com.lcc.mvp.presenter.impl;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonElement;
import com.lcc.db.test.UserInfo;
import com.lcc.frame.data.DataManager;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.ResetPasswordModel;
import com.lcc.mvp.presenter.ResetPasswordPresenter;
import com.lcc.mvp.view.ResetPasswordView;
import com.lcc.utils.SharePreferenceUtil;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.LogUtils;

public class ResetPasswordPresenterImpl implements ResetPasswordPresenter {

    private ResetPasswordView view;
    private ResetPasswordModel model;

    public ResetPasswordPresenterImpl(ResetPasswordView view) {
        this.view = view;
        this.model = new ResetPasswordModel();
    }

    @Override
    public void resetPassword(String phone,String pwd,String new_pwd,String code) {
        model.resetPassword(phone,pwd, new_pwd,code,new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.showResetError(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {
                        String tk = SharePreferenceUtil.getUserTk();
                        if (!TextUtils.isEmpty(tk)){
                            String result = jsonObject.getString("result");
                            SharePreferenceUtil.setUserTk(result);
                        }
                        view.showSuccess();
                    } else if (status == 2) {
                        view.showResetError(message);
                        view.checkToken();
                    } else {
                        view.showResetError(message);
                    }
                } catch (Exception e) {
                    view.showResetError("重置密码失败");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getVerifySMS(String phone, String pwd) {
        model.getVerifySMS(phone, pwd, new ResultCallback<JsonElement>() {
            @Override
            public void onError(Request request, Exception e) {
                view.showMsg(e.getMessage());
            }

            @Override
            public void onResponse(JsonElement response) {
                view.showSmsSuccess();
            }
        });
    }
}
