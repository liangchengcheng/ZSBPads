package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.entity.AnswerAdd;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

import java.io.File;
import java.util.List;

public class ComAnswerAddModel {

    /**
     * 发送公司相关的问题到服务器
     */
    public OkHttpRequest ComAnswerAdd(AnswerAdd replay, List<File> files, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("answer", replay.getAnswer());
        paramsMap.put("fid", replay.getFid());
        if (files!=null){
            paramsMap.put("num",files.size());
        }else {
            paramsMap.put("num", 0);
        }
        return ApiClient.createWithFiles(AppConstants.RequestPath.ComAnswerAddservice,
                paramsMap, files)
                .upload(callback);
    }
}
