package com.lcc.mvp.presenter.impl;

import com.lcc.entity.ComTestAdd;
import com.lcc.entity.CompanyDescription;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.ComDesAddModel;
import com.lcc.mvp.model.ComQuesAddModel;
import com.lcc.mvp.presenter.ComDesAddPresenter;
import com.lcc.mvp.presenter.ComQuesAddPresenter;
import com.lcc.mvp.view.ComDesAddView;
import com.lcc.mvp.view.ComQuesAddView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class ComDesAddPresenterImpl implements ComDesAddPresenter {

    private ComDesAddView view;
    private ComDesAddModel model;

    public ComDesAddPresenterImpl(ComDesAddView view) {
        this.view = view;
        model = new ComDesAddModel();
    }

    @Override
    public void ComDesAdd(CompanyDescription replay, List<File> files) {
        model.ComDesAdd(replay,files, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.addFail();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        view.addSuccess();
                    } else if (status == 2) {
                        view.addFail();
                        view.checkToken();
                    } else {
                        view.addFail();
                    }
                } catch (Exception e) {
                    view.addFail();
                    e.printStackTrace();
                }
            }

            @Override
            public void inProgress(float progress) {

            }
        });
    }
}

