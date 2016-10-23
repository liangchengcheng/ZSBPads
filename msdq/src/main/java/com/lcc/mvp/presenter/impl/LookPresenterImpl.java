package com.lcc.mvp.presenter.impl;

import com.lcc.entity.Answer;
import com.lcc.entity.AnswerContent;
import com.lcc.entity.FavAndGoodState;
import com.lcc.entity.UserGood;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.LookModel;
import com.lcc.mvp.model.TestAnswerContentModel;
import com.lcc.mvp.presenter.LookPresenter;
import com.lcc.mvp.presenter.TestAnswerContentPresenter;
import com.lcc.mvp.view.LookView;
import com.lcc.mvp.view.TestAnswerContentView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;

public class LookPresenterImpl implements LookPresenter {

    private LookModel model;
    private LookView view;

    public LookPresenterImpl(LookView view) {
        this.view = view;
        model = new LookModel();
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
    public void Fav(String nid, String type, String title) {
        model.favAnswer(nid, type, title, new ResultCallback<String>() {
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
    public void UnFav(String nid) {
        model.UnfavAnswer(nid, new ResultCallback<String>() {
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
                    if (status == 1) {
                        String result = jsonObject.getString("result");
                        AnswerContent answerContent = GsonUtils
                                .changeGsonToBean(result, AnswerContent.class);
                        view.getDataSuccess(answerContent);
                    }else if (status == 2) {
                        view.getDataFail(message);
                        view.checkToken();
                    }  else {
                        view.getDataFail(message);
                    }
                } catch (Exception e) {
                    view.getDataFail(ApiException.getApiExceptionMessage(e.getMessage()));
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void Good(UserGood nid, String type, String title) {
        model.GoodAnswer(nid, type, title, new ResultCallback<String>() {
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
                    } else if (status == 2) {
                        view.GoodFail(message);
                        view.checkToken();
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
    public void UnGood(String article) {
        model.UnGoodAnswer(article,  new ResultCallback<String>() {
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
                    } else if (status == 2) {
                        view.UnGoodFail(message);
                        view.checkToken();
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
