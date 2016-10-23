package com.lcc.mvp.presenter.impl;

import android.os.Handler;

import com.lcc.entity.TestEntity;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.TestModel;
import com.lcc.mvp.presenter.TestPresenter;
import com.lcc.mvp.view.TestView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class TestPresenterImpl implements TestPresenter {
    private static final int DEF_DELAY = (int) (1 * 1000);
    private TestModel model;
    private TestView view;

    public TestPresenterImpl(TestView view) {
        this.view = view;
        model = new TestModel();
    }

    private void loadData(final int page, final String options, final String startTime,
                          final String endTime, String orders, final boolean get_data) {
        if (get_data) {
            view.getLoading();
        }

        final long current_time = TimeUtils.getCurrentTime();
        model.getTestList(page, options, startTime, endTime, orders, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                if (get_data) {
                    view.getDataFail(ApiException.getApiExceptionMessage(e.getMessage()));
                } else {
                    view.refreshOrLoadFail(ApiException.getApiExceptionMessage(e.getMessage()));
                }
            }

            @Override
            public void onResponse(String response) {
                int delay = 0;
                if (TimeUtils.getCurrentTime() - current_time < DEF_DELAY) {
                    delay = DEF_DELAY;
                }
                updateView(response, delay, page, get_data);
            }
        });
    }

    private void updateView(final String entities, int delay, final int page, final boolean get_data) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(entities);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    String result = jsonObject.getString("result");
                    if (status == 1) {
                        List<TestEntity> weekDatas = GsonUtils.fromJsonArray(result, TestEntity.class);
                        if (page == 1) {
                            view.refreshView(weekDatas);
                        } else {
                            view.loadMoreView(weekDatas);
                        }
                    } else if (status == 2) {
                        view.getDataFail(message);
                        view.checkToken();
                    } else {
                        if (message.equals("数据为空") && page == 1) {
                            view.getDataEmpty();
                        } else {
                            if (get_data) {
                                view.getDataFail(ApiException.getApiExceptionMessage(message));
                            } else {
                                view.refreshOrLoadFail(ApiException.getApiExceptionMessage(message));
                            }
                        }
                    }
                } catch (Exception e) {
                    if (get_data) {
                        view.getDataFail(ApiException.getApiExceptionMessage(e.getMessage()));
                    } else {
                        view.refreshOrLoadFail(ApiException.getApiExceptionMessage(e.getMessage()));
                    }
                    e.printStackTrace();
                }
            }
        }, delay);
    }

    @Override
    public void getData(int page, String options, String startTime, String endTime, String orders) {
        loadData(page, options, startTime, endTime, orders, true);
    }

    @Override
    public void loadMore(int page, String options, String startTime, String endTime, String orders) {
        loadData(page, options, startTime, endTime, orders, false);
    }

    @Override
    public void refresh(int page, String options, String startTime, String endTime, String orders) {
        loadData(1, options, startTime, endTime, orders, false);
    }
}
