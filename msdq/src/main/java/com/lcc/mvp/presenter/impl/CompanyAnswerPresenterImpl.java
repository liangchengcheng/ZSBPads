package com.lcc.mvp.presenter.impl;

import android.os.Handler;
import android.text.TextUtils;

import com.lcc.entity.Answer;
import com.lcc.entity.Article;
import com.lcc.entity.CompanyAnswer;
import com.lcc.entity.CompanyTest;
import com.lcc.entity.FavEntity;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.CompanyAnswerModel;
import com.lcc.mvp.model.TestAnswerModel;
import com.lcc.mvp.presenter.CompanyAnswerPresenter;
import com.lcc.mvp.presenter.TestAnswerPresenter;
import com.lcc.mvp.view.CompanyAnswerView;
import com.lcc.mvp.view.TestAnswerView;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.util.List;

import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class CompanyAnswerPresenterImpl implements CompanyAnswerPresenter {
    private static final int DEF_DELAY = (int) (1 * 1000);
    private CompanyAnswerModel model;
    private CompanyAnswerView view;

    public CompanyAnswerPresenterImpl(CompanyAnswerView view) {
        this.view = view;
        model = new CompanyAnswerModel();
    }

    private void loadData(final int page,final String fid,final boolean get_data) {
        if (get_data) {
            view.getLoading();
        }

        final long current_time = TimeUtils.getCurrentTime();
        model.getCompanyAnswer(page,fid, new ResultCallback<String>() {
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
                updateView(response, delay, page,get_data);
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

                       String fav = jsonObject.getString("fav");
                       FavEntity favEntityList = GsonUtils.changeGsonToBean(fav, FavEntity.class);

                       if (favEntityList != null && !TextUtils.isEmpty(favEntityList.getMid())) {
                           view.isHaveFav(true);
                       } else {
                           view.isHaveFav(false);
                       }

                       if (status == 1) {
                           List<CompanyAnswer> weekDatas = GsonUtils.fromJsonArray(result, CompanyAnswer.class);
                           if (page == 1) {
                               if (weekDatas != null && weekDatas.size() > 0) {
                                   view.refreshView(weekDatas);
                               } else {
                                   view.getDataEmpty();
                               }
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
    public void getData(int page, String fid) {
        loadData(page, fid, true);
    }

    @Override
    public void loadMore(int page,String fid) {
        loadData(page,fid,false);
    }

    @Override
    public void refresh(int page,String fid) {
        loadData(page,fid,false);
    }

    @Override
    public void Fav(CompanyTest article, String type) {
        model.favQuestion(article,type, new ResultCallback<String>() {
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
                    }else {
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
    public void UnFav(CompanyTest article) {
        model.UnfavQuestion(article, new ResultCallback<String>() {
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
}
