package com.lcc.mvp.presenter.impl;

import android.os.Handler;

import com.lcc.entity.Article;
import com.lcc.entity.Comments;
import com.lcc.entity.Replay;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.CommentsModel;
import com.lcc.mvp.model.IndexMenuModel;
import com.lcc.mvp.presenter.CommentsPresenter;
import com.lcc.mvp.presenter.IndexMenuPresenter;
import com.lcc.mvp.view.CommentsView;
import com.lcc.mvp.view.IndexMenuView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class CommentsPresenterImpl implements CommentsPresenter {
    private CommentsView view;
    private CommentsModel model;
    private static final int DEF_DELAY = (int) (1 * 1000);

    public CommentsPresenterImpl(CommentsView view) {
        this.view = view;
        model = new CommentsModel();
    }

    private void loadData(final int page, String nid, final boolean get_data) {
        if (get_data) {
            view.getLoading();
        }
        final long current_time = TimeUtils.getCurrentTime();
        model.getComments(page, nid, new ResultCallback<String>() {
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
                        List<Comments> weekDatas = GsonUtils.fromJsonArray(result, Comments.class);
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
                        // TODO: 2016/10/18 此处需要修改
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
    public void getData(int page, String nid) {
        loadData(page, nid, true);
    }

    @Override
    public void loadMore(int page, String nid) {
        loadData(page, nid, false);
    }

    @Override
    public void refresh(int page, String nid) {
        loadData(page, nid, false);
    }

    @Override
    public void sendComments(Replay replay) {
        model.sendComments(replay, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.replayFail();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        view.replaySuccess();
                    }else if (status == 2) {
                        view.replayFail();
                        view.checkToken();
                    } else {
                        view.replayFail();
                    }
                } catch (Exception e) {
                    view.replayFail();
                    e.printStackTrace();
                }
            }
        });
    }
}

