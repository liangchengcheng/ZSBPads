package com.lcc.mvp.presenter.impl;

import android.os.Handler;

import com.lcc.entity.CompanyTest;
import com.lcc.entity.TestEntity;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.FabuModel;
import com.lcc.mvp.presenter.FabuPresenter;
import com.lcc.mvp.presenter.FavPresenter;
import com.lcc.mvp.view.FabuComView;
import com.lcc.mvp.view.FabuTestView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class FabuComPresenterImpl implements FabuPresenter {
    private FabuComView view;
    private FabuModel model;
    private static final int DEF_DELAY = (int) (1 * 1000);

    public FabuComPresenterImpl(FabuComView view) {
        this.view = view;
        model = new FabuModel();
    }

    private void loadData(final int page, String type, final boolean get_data) {
        if (get_data) {
            view.getLoading();
        }
        final long current_time = TimeUtils.getCurrentTime();
        model.getComList(page, type, new ResultCallback<String>() {
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
                    if (status == 1) {
                        String result = jsonObject.getString("result");
                        List<CompanyTest> weekDatas = GsonUtils.fromJsonArray(result, CompanyTest.class);
                        if (page == 1) {
                            if (weekDatas != null && weekDatas.size() > 0) {
                                view.refreshDataSuccess(weekDatas);
                            } else {
                                view.getDataEmpty();
                            }
                        } else {
                            view.loadMoreWeekDataSuccess(weekDatas);
                        }
                    } else if (status == 2) {
                        view.getDataFail(message);
                        view.checkToken();
                    } else {
                        if (get_data) {
                            view.getDataFail(message);
                        } else {
                            view.refreshOrLoadFail(message);
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
    public void getData(int page, String type) {
        loadData(page, type, true);
    }

    @Override
    public void loadMore(int page, String type) {
        loadData(page, type, false);
    }

    @Override
    public void refresh(int page, String type) {
        loadData(page, type, false);
    }
}

