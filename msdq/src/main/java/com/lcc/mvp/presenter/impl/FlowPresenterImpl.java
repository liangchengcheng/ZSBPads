package com.lcc.mvp.presenter.impl;

import android.os.Handler;

import com.lcc.entity.Article;
import com.lcc.entity.FlowIEntity;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.FlowModel;
import com.lcc.mvp.model.IndexMenuModel;
import com.lcc.mvp.presenter.FlowPresenter;
import com.lcc.mvp.presenter.IndexMenuPresenter;
import com.lcc.mvp.view.FlowView;
import com.lcc.mvp.view.IndexMenuView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class FlowPresenterImpl implements FlowPresenter {

    private FlowView view;
    private FlowModel model;
    private static final int DEF_DELAY = (int) (1 * 1000);

    public FlowPresenterImpl(FlowView view) {
        this.view = view;
        model = new FlowModel();
    }

    @Override
    public void getMyData(int page, String type) {
        loadMyData(page, type, true);
    }

    @Override
    public void loadMyMore(int page, String type) {
        loadMyData(page, type, false);
    }

    @Override
    public void refreshMy(int page, String type) {
        loadMyData(page, type, false);
    }

    @Override
    public void getYouData(int page, String type) {
        loadYouData(page, type, true);
    }

    @Override
    public void loadYouMore(int page, String type) {
        loadYouData(page, type, false);
    }

    @Override
    public void refreshYou(int page, String type) {
        loadYouData(page, type, false);
    }

    private void loadMyData(final int page, String type, final boolean get_data) {
        if (get_data) {
            view.getLoading();
        }
        final long current_time = TimeUtils.getCurrentTime();
        model.getILikeWhoList(page, type, new ResultCallback<String>() {
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
                updateMyView(response, delay, page, get_data);
            }
        });
    }

    private void updateMyView(final String entities, int delay, final int page, final boolean get_data) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(entities);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    String result = jsonObject.getString("result");
                    if (status == 1) {
                        List<FlowIEntity> weekDatas = GsonUtils.fromJsonArray(result, FlowIEntity.class);
                        if (page == 1) {
                            if (weekDatas != null && weekDatas.size() > 0) {
                                view.refreshDataSuccess(weekDatas);
                            } else {
                                view.getDataEmpty();
                            }
                        } else {
                            view.loadMoreDataSuccess(weekDatas);
                        }
                    } else if (status == 2) {
                        view.getDataFail(message);
                        view.checkToken();
                    } else {
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

    private void loadYouData(final int page, String type, final boolean get_data) {
        if (get_data) {
            view.getLoading();
        }
        final long current_time = TimeUtils.getCurrentTime();
        model.getWhoLikeMe(page, type, new ResultCallback<String>() {
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
                updateYouView(response, delay, page, get_data);
            }
        });
    }

    private void updateYouView(final String entities, int delay, final int page, final boolean get_data) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(entities);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    String result = jsonObject.getString("result");
                    if (status == 1) {
                        List<FlowIEntity> weekDatas = GsonUtils.fromJsonArray(result, FlowIEntity.class);
                        if (page == 1) {
                            if (weekDatas != null && weekDatas.size() > 0) {
                                view.refreshDataSuccess(weekDatas);
                            } else {
                                view.getDataEmpty();
                            }
                        } else {
                            view.loadMoreDataSuccess(weekDatas);
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
}

