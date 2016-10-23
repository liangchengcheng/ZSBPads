package com.lcc.mvp.presenter.impl;

import com.lcc.db.test.UserInfo;
import com.lcc.entity.NewsInfo;
import com.lcc.frame.data.DataManager;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.LoginModel;
import com.lcc.mvp.model.NewsInfoModel;
import com.lcc.mvp.presenter.LoginPresenter;
import com.lcc.mvp.presenter.NewsInfoPresenter;
import com.lcc.mvp.view.LoginView;
import com.lcc.mvp.view.NewsInfoView;
import com.lcc.utils.SharePreferenceUtil;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;

public class NewsInfoPresenterImpl implements NewsInfoPresenter {

    private NewsInfoView view;
    private NewsInfoModel model;

    public NewsInfoPresenterImpl(NewsInfoView view) {
        this.view = view;
        model = new NewsInfoModel();
    }

    @Override
    public void getNewsInfo() {
        view.showLoading();
        model.getInfoCount(new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.NewsInfoFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {
                        String result = jsonObject.getString("result");
                        NewsInfo userInfo = GsonUtils.changeGsonToBean(result, NewsInfo.class);
                        view.NewsInfoSuccess(userInfo);
                    }else if (status == 2) {
                        view.NewsInfoFail(message);
                        view.checkToken();
                    }  else {
                        view.NewsInfoFail(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

