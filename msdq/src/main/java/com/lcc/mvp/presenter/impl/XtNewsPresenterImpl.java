package com.lcc.mvp.presenter.impl;

import android.os.Handler;

import com.lcc.db.test.UserInfo;
import com.lcc.entity.Article;
import com.lcc.entity.TestEntity;
import com.lcc.entity.XtNewsEntity;
import com.lcc.frame.data.DataManager;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.IndexMenuModel;
import com.lcc.mvp.model.XtNewsModel;
import com.lcc.mvp.presenter.IndexMenuPresenter;
import com.lcc.mvp.presenter.XtNewsPresenter;
import com.lcc.mvp.view.IndexMenuView;
import com.lcc.mvp.view.XtNewsView;
import com.lcc.utils.SharePreferenceUtil;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class XtNewsPresenterImpl implements XtNewsPresenter {
    private XtNewsView view;
    private XtNewsModel model;

    public XtNewsPresenterImpl(XtNewsView view) {
        this.view = view;
        model = new XtNewsModel();
    }

    @Override
    public void getData() {
        view.getLoading();
        model.getXtNews(new ResultCallback<String>() {
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
                        List<XtNewsEntity> weekDatas = GsonUtils.fromJsonArray(result, XtNewsEntity.class);
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

