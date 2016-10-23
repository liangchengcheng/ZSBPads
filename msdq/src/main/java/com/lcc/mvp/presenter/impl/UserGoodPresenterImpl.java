package com.lcc.mvp.presenter.impl;

import com.lcc.entity.UserGood;
import com.lcc.entity.XtNewsEntity;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.UserGoodModel;
import com.lcc.mvp.model.XtNewsModel;
import com.lcc.mvp.presenter.UserGoodPresenter;
import com.lcc.mvp.presenter.XtNewsPresenter;
import com.lcc.mvp.view.UserGoodView;
import com.lcc.mvp.view.XtNewsView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;

public class UserGoodPresenterImpl implements UserGoodPresenter {

    private UserGoodView view;
    private UserGoodModel model;

    public UserGoodPresenterImpl(UserGoodView view) {
        this.view = view;
        model = new UserGoodModel();
    }

    @Override
    public void getData() {
        view.getLoading();
        model.getSuperUserGood(new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.getDataFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {
                        String result = jsonObject.getString("result");
                        List<UserGood> weekDatas = GsonUtils.fromJsonArray(result, UserGood.class);
                        view.getDataSuccess(weekDatas);
                    }else if (status == 2) {
                        view.getDataFail(message);
                        view.checkToken();
                    }  else {
                        view.getDataFail(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

