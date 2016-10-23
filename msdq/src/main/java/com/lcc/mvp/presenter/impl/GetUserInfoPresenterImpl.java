package com.lcc.mvp.presenter.impl;

import android.os.Handler;

import com.lcc.entity.FavEntity;
import com.lcc.entity.GzBean;
import com.lcc.entity.NewsInfo;
import com.lcc.entity.otherUserInfo;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.FavModel;
import com.lcc.mvp.model.GetUserInfoModel;
import com.lcc.mvp.presenter.FavPresenter;
import com.lcc.mvp.presenter.GetUserInfoPresenter;
import com.lcc.mvp.view.FavView;
import com.lcc.mvp.view.GetUserInfoView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class GetUserInfoPresenterImpl implements GetUserInfoPresenter {

    private GetUserInfoView view;
    private GetUserInfoModel model;

    public GetUserInfoPresenterImpl(GetUserInfoView view) {
        this.view = view;
        model = new GetUserInfoModel();
    }

    @Override
    public void getData(String phone) {
        view.getLoading();
        model.getUserInfo(phone, new ResultCallback<String>() {
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
                        otherUserInfo userInfo = GsonUtils.changeGsonToBean(result, otherUserInfo.class);
                        view.getDataSuccess(userInfo);
                    } else if (status == 2) {
                        view.checkToken();
                    } else {
                        view.getDataFail(message);
                    }

                    String fav = jsonObject.getString("fav");
                    GzBean gzBean = GsonUtils.changeGsonToBean(fav, GzBean.class);
                    view.HaveGz(gzBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void Gz(String you) {
        model.GzUser(you, new ResultCallback<String>() {
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
                        view.GzSuccess();
                    } else if (status == 2) {
                        view.checkToken();
                    } else {
                        view.GzFail(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void UnGz(String you) {
        model.unGzUser(you, new ResultCallback<String>() {
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
                        view.unGzSuccess();
                    }else if (status == 2) {
                        view.checkToken();
                    }  else {
                        view.unGzFail(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

