package com.lcc.mvp.presenter.impl;

import android.os.Handler;
import android.text.TextUtils;

import com.lcc.entity.Answer;
import com.lcc.entity.CompanyDescription;
import com.lcc.entity.FavAndGoodState;
import com.lcc.entity.FavEntity;
import com.lcc.entity.TestEntity;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.ComStateModel;
import com.lcc.mvp.model.TestAnswerModel;
import com.lcc.mvp.presenter.ComStatePresenter;
import com.lcc.mvp.presenter.TestAnswerPresenter;
import com.lcc.mvp.view.ComStateView;
import com.lcc.mvp.view.TestAnswerView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class ComStatePresenterImpl implements ComStatePresenter {
    private ComStateModel model;
    private ComStateView view;

    public ComStatePresenterImpl(ComStateView view) {
        this.view = view;
        model = new ComStateModel();
    }

    private void loadData(String fid) {
        model.isfavAnswer(fid, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.isHaveFav(false);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String fav = jsonObject.getString("result");
                    List<FavAndGoodState> favAndGoodStates = GsonUtils.fromJsonArray(fav, FavAndGoodState.class);
                    if (favAndGoodStates != null && favAndGoodStates.size() > 0) {
                        view.isHaveFav(true);
                    } else {
                        view.isHaveFav(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getData(String fid) {
        loadData(fid);
    }

    @Override
    public void Fav(CompanyDescription article, String type) {
        model.favCom(article, type, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.FavFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {
                        view.FavSuccess();
                    } else if (status == 2) {
                        view.FavFail(message);
                        view.checkToken();
                    } else {
                        view.FavFail(message);
                    }
                } catch (Exception e) {
                    view.FavFail(ApiException.getApiExceptionMessage(e.getMessage()));
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void UnFav(CompanyDescription article, String type) {
        model.UnfavCom(article, type, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.UnFavFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {
                        view.UnFavSuccess();
                    }else if (status == 2) {
                        view.UnFavFail(message);
                        view.checkToken();
                    } else {
                        view.UnFavFail(message);
                    }
                } catch (Exception e) {
                    view.UnFavFail(ApiException.getApiExceptionMessage(e.getMessage()));
                    e.printStackTrace();
                }
            }
        });
    }
}
