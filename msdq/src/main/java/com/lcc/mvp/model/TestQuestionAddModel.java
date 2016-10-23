package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.entity.ComTestAdd;
import com.lcc.entity.TestEntity;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

import java.io.File;
import java.util.List;

public class TestQuestionAddModel {

    /**
     * 发送公司相关的问题到服务器
     */
    public OkHttpRequest TestQuestionAdd(TestEntity replay, List<File> files, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("type", replay.getType());
        paramsMap.put("options", replay.getOptions());
        paramsMap.put("title", replay.getTitle());
        paramsMap.put("summary", replay.getSummary());
        return ApiClient.createWithFile(AppConstants.RequestPath.AddServiceAPI,
                paramsMap, files)
                .upload(callback);
    }
}
