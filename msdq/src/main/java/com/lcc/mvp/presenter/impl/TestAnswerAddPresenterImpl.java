package com.lcc.mvp.presenter.impl;

import com.lcc.entity.AnswerAdd;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.TestAnswerAddModel;
import com.lcc.mvp.presenter.TestAnswerAddPresenter;
import com.lcc.mvp.view.TestAnswerAddView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class TestAnswerAddPresenterImpl implements TestAnswerAddPresenter {
    private TestAnswerAddView view;
    private TestAnswerAddModel model;

    public TestAnswerAddPresenterImpl(TestAnswerAddView view) {
        this.view = view;
        model = new TestAnswerAddModel();
    }

    @Override
    public void TestAnswerAdd(AnswerAdd replay, List<File> files) {
        model.TestAnswerAdd(replay,files, new ResultCallback<String>() {
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

