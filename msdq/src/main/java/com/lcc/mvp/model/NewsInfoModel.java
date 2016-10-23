package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class NewsInfoModel {

    /**
     * 获取用户的未读消息等
     */
    public OkHttpRequest getInfoCount( ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        return ApiClient.create(AppConstants.RequestPath.getInfoCount, paramsMap).tag("").get(callback);
    }
}
