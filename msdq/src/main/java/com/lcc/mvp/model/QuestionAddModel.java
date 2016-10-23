package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.entity.CompanyDescription;
import com.lcc.entity.QuestionAdd;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

import java.io.File;
import java.util.List;

public class QuestionAddModel {

    /**
     * 增加发布新的问题
     */
    public OkHttpRequest QuestionAdd(QuestionAdd questionAdd, List<File>files, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("type", questionAdd.getType());
        paramsMap.put("option", questionAdd.getOptions());
        paramsMap.put("title", questionAdd.getTitle());
        paramsMap.put("summary", questionAdd.getSummary());
        paramsMap.put("source", "");
        paramsMap.put("keyword", "");

        return ApiClient.createWithFile(AppConstants.RequestPath.addQuestionservice,
                paramsMap,files)
                .upload(callback);
    }
}
