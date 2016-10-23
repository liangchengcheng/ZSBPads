package com.lcc.mvp.presenter.impl;

import android.os.Handler;
import com.lcc.entity.ComTestAdd;
import com.lcc.entity.Comments;
import com.lcc.entity.Replay;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.mvp.model.ComQuesAddModel;
import com.lcc.mvp.model.CommentsModel;
import com.lcc.mvp.presenter.ComQuesAddPresenter;
import com.lcc.mvp.presenter.CommentsPresenter;
import com.lcc.mvp.view.ComQuesAddView;
import com.lcc.mvp.view.CommentsView;
import com.squareup.okhttp.Request;
import org.json.JSONObject;
import java.io.File;
import java.util.List;
import zsbpj.lccpj.frame.ApiException;
import zsbpj.lccpj.utils.GsonUtils;
import zsbpj.lccpj.utils.TimeUtils;

public class ComQuesAddPresenterImpl implements ComQuesAddPresenter {
    private ComQuesAddView view;
    private ComQuesAddModel model;

    public ComQuesAddPresenterImpl(ComQuesAddView view) {
        this.view = view;
        model = new ComQuesAddModel();
    }

    @Override
    public void ComQuesAdd(ComTestAdd replay, List<File> files) {
        model.ComQuesAdd(replay,files, new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                view.addFail();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        view.addSuccess();
                    }else if (status == 2) {
                        view.addFail();
                        view.checkToken();
                    }  else {
                        view.addFail();
                    }
                } catch (Exception e) {
                    view.addFail();
                    e.printStackTrace();
                }
            }

            @Override
            public void inProgress(float progress) {

            }
        });
    }
}

