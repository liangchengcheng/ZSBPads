package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.entity.ComTestAdd;
import com.lcc.entity.Replay;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

import java.io.File;
import java.util.List;

public class ComQuesAddModel {

    /**
     * 发送公司相关的问题到服务器
     */
    public OkHttpRequest ComQuesAdd(ComTestAdd replay, List<File> files, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("type", replay.getType());
        paramsMap.put("com", replay.getCom_id());
        paramsMap.put("title", replay.getTitle());
        paramsMap.put("summary", replay.getSummary());
        paramsMap.put("img_url", replay.getImg_url());
        return ApiClient.createWithFile(AppConstants.RequestPath.AddServiceAPI,
                paramsMap, files).upload(callback);
    }
}
