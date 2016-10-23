package com.lcc.mvp.presenter.impl;

import com.lcc.entity.ActivityEntity;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.IndexContentModel;
import com.lcc.mvp.model.IndexModel;
import com.lcc.mvp.presenter.IndexContentPresenter;
import com.lcc.mvp.presenter.IndexPresenter;
import com.lcc.mvp.view.IndexContentView;
import com.lcc.mvp.view.IndexView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;

public class IndexContentPresenterImpl implements IndexContentPresenter {

    private IndexContentView view;
    private IndexContentModel model;

    public IndexContentPresenterImpl(IndexContentView view) {
        this.view = view;
        model = new IndexContentModel();
    }

    @Override
    public void getActivityContent(String id) {
        view.loading();
        model.getActivityContent(id,new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.getLoginFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    String result = jsonObject.getString("result");
                    if (status == 1) {
                        JSONObject json_result = new JSONObject(result);
                        String content=json_result.getString("content");
                        view.getSuccess(content);
                    } else if (status == 2) {
                        view.getLoginFail(message);
                        view.checkToken();
                    } else {
                        view.getLoginFail(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

