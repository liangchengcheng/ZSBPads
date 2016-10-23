package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class ReplayModel {

    /**
     * 获取未读的系统消息
     */
    public OkHttpRequest getSuperComments( ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        return ApiClient.create(AppConstants.RequestPath.getSuperComments, paramsMap).tag("")
                .get(callback);
    }

}
