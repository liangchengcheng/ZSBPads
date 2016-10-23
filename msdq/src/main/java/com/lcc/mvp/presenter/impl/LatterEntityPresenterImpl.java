package com.lcc.mvp.presenter.impl;

import com.lcc.entity.LatterEntity;
import com.lcc.entity.Letter;
import com.lcc.entity.SendLatter;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.LatterEntityModel;
import com.lcc.mvp.model.LetterModel;
import com.lcc.mvp.presenter.LatterEntityPresenter;
import com.lcc.mvp.presenter.LetterPresenter;
import com.lcc.mvp.view.LatterEntityView;
import com.lcc.mvp.view.LetterView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;

public class LatterEntityPresenterImpl implements LatterEntityPresenter {

    private LatterEntityView view;
    private LatterEntityModel model;

    public LatterEntityPresenterImpl(LatterEntityView view) {
        this.view = view;
        model = new LatterEntityModel();
    }

    @Override
    public void getData(String page,String from) {
        view.getLoading();
        model.getLetterByPhone(page,from ,new ResultCallback<String>() {
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
                        List<LatterEntity> weekDatas = GsonUtils.fromJsonArray(result, LatterEntity.class);
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

    @Override
    public void sendLatter(SendLatter replay) {
        model.sendLetterByPhone(replay ,new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.replayFail();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    // TODO: 16/7/24 具体的错误信息
                    if (status == 1) {
                        view.replaySuccess();
                    }else if (status == 2) {
                        view.replayFail();
                        view.checkToken();
                    }  else {
                        view.replayFail();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

