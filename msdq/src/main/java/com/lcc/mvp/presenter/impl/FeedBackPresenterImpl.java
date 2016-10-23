package com.lcc.mvp.presenter.impl;

import com.lcc.db.test.UserInfo;
import com.lcc.frame.data.DataManager;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.FeedBackModel;
import com.lcc.mvp.model.LoginModel;
import com.lcc.mvp.presenter.FeedBackPresenter;
import com.lcc.mvp.presenter.LoginPresenter;
import com.lcc.mvp.view.FeedBackView;
import com.lcc.mvp.view.LoginView;
import com.lcc.utils.SharePreferenceUtil;
import com.squareup.okhttp.Request;
import org.json.JSONObject;
import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;

public class FeedBackPresenterImpl implements FeedBackPresenter {

    private FeedBackView view;
    private FeedBackModel model;

    public FeedBackPresenterImpl(FeedBackView view) {
        this.view = view;
        model = new FeedBackModel();
    }

    @Override
    public void postMessage(String password) {
        view.showLoading();
        model.PostMessage(password, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.FeekFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {
                        view.FeekSuccess();
                    } else if (status == 2) {
                        view.FeekFail(message);
                        view.checkToken();
                    } else {
                        view.FeekFail(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

