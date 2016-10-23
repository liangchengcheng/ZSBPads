package com.lcc.mvp.presenter.impl;

import android.text.TextUtils;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.ChoiceTypeModel;
import com.lcc.mvp.presenter.ChoiceTypePresenter;
import com.lcc.mvp.view.ChoiceTypeView;
import com.squareup.okhttp.Request;
import org.json.JSONObject;
import zsbpj.lccpj.frame.ApiException;

public class ChoicePresenterImpl implements ChoiceTypePresenter {
    private ChoiceTypeView view;
    private ChoiceTypeModel model;

    public ChoicePresenterImpl(ChoiceTypeView view) {
        this.view = view;
        model = new ChoiceTypeModel();
    }

    private void loadData1() {
        view.getLoading();
        model.getType1(new ResultCallback<String>() {
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
                        if (!TextUtils.isEmpty(result)) {
                            view.getDataSuccess(result);
                        } else {
                            view.getDataEmpty();
                        }
                    } else {
                        view.getDataFail(message);
                    }
                } catch (Exception e) {
                    view.getDataFail(ApiException.getApiExceptionMessage(e.getMessage()));
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadData2(String nid) {
        view.getLoading();
        model.getType2(nid,new ResultCallback<String>() {
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
                        if (!TextUtils.isEmpty(result)) {
                            view.getDataSuccess(result);
                        } else {
                            view.getDataEmpty();
                        }
                    } else {
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
    public void getType1() {
        loadData1();
    }

    @Override
    public void getType2(String nid) {
        loadData2(nid);
    }

    @Override
    public void setDataType(String type) {
        view.setLoading();
        model.editType(type,new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.setDataFail(ApiException.getApiExceptionMessage(e.getMessage()));
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {
                        view.setDataSuccess();
                    } else {
                        view.setDataFail(message);
                    }
                } catch (Exception e) {
                    view.setDataFail(ApiException.getApiExceptionMessage(e.getMessage()));
                    e.printStackTrace();
                }
            }
        });
    }
}

