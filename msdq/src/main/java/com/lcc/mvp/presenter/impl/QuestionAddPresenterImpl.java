package com.lcc.mvp.presenter.impl;

import com.lcc.entity.CompanyDescription;
import com.lcc.entity.QuestionAdd;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.ComDesAddModel;
import com.lcc.mvp.model.QuestionAddModel;
import com.lcc.mvp.presenter.ComDesAddPresenter;
import com.lcc.mvp.presenter.QuestionAddPresenter;
import com.lcc.mvp.view.ComDesAddView;
import com.lcc.mvp.view.QuestionAddView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class QuestionAddPresenterImpl implements QuestionAddPresenter {

    private QuestionAddView view;
    private QuestionAddModel model;

    public QuestionAddPresenterImpl(QuestionAddView view) {
        this.view = view;
        model = new QuestionAddModel();
    }

    @Override
    public void QuestionAdd(QuestionAdd replay, List<File> files) {
        model.QuestionAdd(replay,files, new ResultCallback<String>() {
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
                    }else if (status == 2) {
                        view.addFail();
                        view.checkToken();
                    }  else {
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

