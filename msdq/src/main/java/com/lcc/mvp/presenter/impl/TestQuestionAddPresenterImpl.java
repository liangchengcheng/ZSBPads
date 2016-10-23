package com.lcc.mvp.presenter.impl;

import com.lcc.entity.CompanyDescription;
import com.lcc.entity.TestEntity;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.ComDesAddModel;
import com.lcc.mvp.model.TestQuestionAddModel;
import com.lcc.mvp.presenter.ComDesAddPresenter;
import com.lcc.mvp.presenter.TestQuestionAddPresenter;
import com.lcc.mvp.view.ComDesAddView;
import com.lcc.mvp.view.TestQuestionAddView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class TestQuestionAddPresenterImpl implements TestQuestionAddPresenter {

    private TestQuestionAddView view;
    private TestQuestionAddModel model;

    public TestQuestionAddPresenterImpl(TestQuestionAddView view) {
        this.view = view;
        model = new TestQuestionAddModel();
    }

    @Override
    public void TestQuestionAdd(TestEntity replay, List<File> files) {
        model.TestQuestionAdd(replay,files, new ResultCallback<String>() {
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

