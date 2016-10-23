package com.lcc.mvp.presenter.impl;

import com.lcc.entity.Answer;
import com.lcc.entity.AnswerContent;
import com.lcc.entity.FavAndGoodState;
import com.lcc.entity.FavEntity;
import com.lcc.entity.TestEntity;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.TestAnswerContentModel;
import com.lcc.mvp.model.TestAnswerModel;
import com.lcc.mvp.presenter.TestAnswerContentPresenter;
import com.lcc.mvp.presenter.TestAnswerPresenter;
import com.lcc.mvp.view.TestAnswerContentView;
import com.lcc.mvp.view.TestAnswerView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class TestAnswerContentPresenterImpl implements TestAnswerContentPresenter {
    private TestAnswerContentModel model;
    private TestAnswerContentView view;

    public TestAnswerContentPresenterImpl(TestAnswerContentView view) {
        this.view = view;
        model = new TestAnswerContentModel();
    }

    @Override
    public void isFav(String nid) {
        model.isfavAnswer(nid, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.getStateFail("获取状态失败");
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String result = jsonObject.getString("result");
                    // TODO: 2016/10/18 此处需要修改 
                    if (status == 1 && !result.equals("[]")) {
                        FavAndGoodState fav = GsonUtils.changeGsonToBean(result, FavAndGoodState.class);
                        view.getStateSuccess(fav);
                    } else if (status == 2) {
                        view.getStateFail("获取状态失败");
                        view.checkToken();
                    } else {
                        view.getStateFail("获取状态失败");
                    }

                } catch (Exception e) {
                    view.getStateFail("获取状态失败");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void Fav(Answer article, String type, String title) {
        model.favAnswer(article, type, title, new ResultCallback<String>() {
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
                    }else if (status == 2) {
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
    public void UnFav(Answer article, String type) {
        model.UnfavAnswer(article, type, new ResultCallback<String>() {
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
                    } else if (status == 2) {
                        view.UnFavFail(message);
                        view.checkToken();
                    }else {
                        view.UnFavFail(message);
                    }
                } catch (Exception e) {
                    view.UnFavFail(ApiException.getApiExceptionMessage(e.getMessage()));
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getContent(String mid) {
        view.getLoading();
        model.getContent(mid, new ResultCallback<String>() {
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
                    String fav_str = jsonObject.getString("fav");
                    //这个是内容
                    if (status == 1) {
                        String result = jsonObject.getString("result");
                        AnswerContent answerContent = GsonUtils.changeGsonToBean(result, AnswerContent.class);
                        view.getDataSuccess(answerContent);

                        FavAndGoodState fav = GsonUtils.changeGsonToBean(fav_str, FavAndGoodState.class);
                        view.getStateSuccess(fav);
                    } else {
                        view.getDataFail(message);
                        view.getStateFail("获取状态失败");
                    }
                } catch (Exception e) {
                    view.getDataFail(ApiException.getApiExceptionMessage(e.getMessage()));
                    view.getStateFail("获取状态失败");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void Good(Answer article, String type, String title) {
        model.GoodAnswer(article, type, title, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.GoodFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {
                        view.GoodSuccess();
                    } else {
                        view.GoodFail(message);
                    }
                } catch (Exception e) {
                    view.GoodFail(ApiException.getApiExceptionMessage(e.getMessage()));
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void UnGood(Answer article, String type) {
        model.UnGoodAnswer(article, type, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.UnGoodFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {
                        view.UnGoodSuccess();
                    } else {
                        view.UnGoodFail(message);
                    }
                } catch (Exception e) {
                    view.UnGoodFail(ApiException.getApiExceptionMessage(e.getMessage()));
                    e.printStackTrace();
                }
            }
        });
    }
}
