package com.lcc.mvp.presenter.impl;

import android.util.Log;

import com.lcc.db.test.UserInfo;
import com.lcc.frame.data.DataManager;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.LoginModel;
import com.lcc.mvp.model.UserEditModel;
import com.lcc.mvp.presenter.LoginPresenter;
import com.lcc.mvp.presenter.UserEditPresenter;
import com.lcc.mvp.view.LoginView;
import com.lcc.mvp.view.UserEditView;
import com.lcc.utils.SharePreferenceUtil;
import com.squareup.okhttp.Request;
import org.json.JSONObject;
import java.io.File;
import java.util.List;
import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;

public class UserEditPresenterImpl implements UserEditPresenter {

    private UserEditView view;
    private UserEditModel model;

    public UserEditPresenterImpl(UserEditView view) {
        this.view = view;
        model = new UserEditModel();
    }

    @Override
    public void userEdit(UserInfo phone, List<File> files) {
        view.showLoading();
        model.userEdit(phone, files,new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.UserEditFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {
                        view.UserEditSuccess();
                    }else if (status == 2) {
                        view.UserEditFail(message);
                        view.checkToken();
                    }  else {
                        view.UserEditFail(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

