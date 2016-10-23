package com.lcc.mvp.presenter.impl;

import android.os.Handler;
import android.view.View;

import com.lcc.db.test.UserInfo;
import com.lcc.entity.ActivityEntity;
import com.lcc.entity.Answer;
import com.lcc.entity.WeekData;
import com.lcc.frame.data.DataManager;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.IndexModel;
import com.lcc.mvp.model.LoginModel;
import com.lcc.mvp.presenter.IndexPresenter;
import com.lcc.mvp.presenter.LoginPresenter;
import com.lcc.mvp.view.IndexView;
import com.lcc.mvp.view.LoginView;
import com.lcc.utils.SharePreferenceUtil;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class IndexPresenterImpl implements IndexPresenter {
    private IndexView view;
    private IndexModel model;
    private static final int DEF_DELAY = (int) (1 * 1000);

    public IndexPresenterImpl(IndexView view) {
        this.view = view;
        model = new IndexModel();
    }

    @Override
    public void getActivity() {
        model.getActivity(new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.getFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    String result = jsonObject.getString("result");
                    List<ActivityEntity> userInfo = GsonUtils.fromJsonArray(result, ActivityEntity.class);
                    if (status == 1) {
                        view.getSuccess(userInfo);
                    } else if (status == 2) {
                        view.getFail(message);
                        view.checkToken();
                    } else {
                        view.getFail(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void loadMore(int page) {
        loadData(page);
    }

    @Override
    public void refresh(int page) {
        loadData(page);
    }

    private void loadData(final int page) {
        view.getWeekDataLoading();
        final long current_time = TimeUtils.getCurrentTime();
        model.getWeekData(page, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.getWeekDataFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                int delay = 0;
                if (TimeUtils.getCurrentTime() - current_time < DEF_DELAY) {
                    delay = DEF_DELAY;
                }
                updateView(response, delay, page);
            }
        });
    }

    private void updateView(final String entities, int delay, final int page) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(entities);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    String result = jsonObject.getString("result");
                    JSONObject resultObject = new JSONObject(result);
                    String data=resultObject.getString("data");
                    List<WeekData> weekDatas = GsonUtils.fromJsonArray(data, WeekData.class);
                    if (status == 1) {
                        if (page == 1) {
                            if (weekDatas != null && weekDatas.size() > 0) {
                                view.refreshWeekDataSuccess(weekDatas);
                            } else {
                                view.getWeekDataEmpty();
                            }
                        } else {
                            view.loadMoreWeekDataSuccess(weekDatas);
                        }
                    } else if (status == 2) {
                        view.checkToken();
                    } else {
                        view.getWeekDataFail(message);
                    }
                } catch (Exception e) {
                    view.getWeekDataFail(ApiException.getApiExceptionMessage(e.getMessage()));
                    e.printStackTrace();
                }
            }
        }, delay);
    }
}

