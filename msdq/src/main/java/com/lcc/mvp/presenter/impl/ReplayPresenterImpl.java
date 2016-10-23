package com.lcc.mvp.presenter.impl;

import com.lcc.entity.Comments;
import com.lcc.entity.UserGood;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.ReplayModel;
import com.lcc.mvp.model.UserGoodModel;
import com.lcc.mvp.presenter.ReplayPresenter;
import com.lcc.mvp.presenter.UserGoodPresenter;
import com.lcc.mvp.view.ReplayView;
import com.lcc.mvp.view.UserGoodView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;

public class ReplayPresenterImpl implements ReplayPresenter {

    private ReplayView view;
    private ReplayModel model;

    public ReplayPresenterImpl(ReplayView view) {
        this.view = view;
        model = new ReplayModel();
    }

    @Override
    public void getData() {
        view.getLoading();
        model.getSuperComments(new ResultCallback<String>() {
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
                        List<Comments> weekDatas = GsonUtils.fromJsonArray(result, Comments.class);
                        view.getDataSuccess(weekDatas);
                    } else if (status == 2) {
                        view.getDataFail(message);
                        view.checkToken();
                    } else {
                        view.getDataFail(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

