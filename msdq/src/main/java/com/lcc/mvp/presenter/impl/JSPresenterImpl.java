package com.lcc.mvp.presenter.impl;

import android.os.Handler;

import com.lcc.entity.Article;
import com.lcc.entity.CompanyTest;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.IndexMenuModel;
import com.lcc.mvp.model.JSModel;
import com.lcc.mvp.presenter.IndexMenuPresenter;
import com.lcc.mvp.presenter.JSPresenter;
import com.lcc.mvp.view.IndexMenuView;
import com.lcc.mvp.view.JSView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class JSPresenterImpl implements JSPresenter {

    private JSView view;
    private JSModel model;
    private static final int DEF_DELAY = (int) (1 * 1000);

    public JSPresenterImpl(JSView view) {
        this.view = view;
        model = new JSModel();
    }

    private void loadData(final int page, String fid, String type, final boolean get_data) {
        if (get_data) {
            view.getLoading();
        }
        final long current_time = TimeUtils.getCurrentTime();
        model.getTestByType(page,fid, type, new ResultCallback<String>() {
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
                    }else if (status == 2) {
                        view.getDataFail(message);
                        view.checkToken();
                    }  else {
                        if (message.equals("数据为空") && page == 1) {
                            view.getDataEmpty();
                        } else {
                            if (get_data) {
                                view.getDataFail(message);
                            } else {
                                view.refreshOrLoadFail(message);
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
    public void getData(int page, String fid, String type) {
        loadData(page, fid, type, true);
    }

    @Override
    public void loadMore(int page, String fid, String type) {
        loadData(page, fid, type, false);
    }

    @Override
    public void refresh(int page, String fid, String type) {
        loadData(page, fid, type, false);
    }
}

