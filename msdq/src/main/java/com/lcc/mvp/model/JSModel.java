package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class JSModel {

    /**
     * 获取公司下面的面试资料的列表
     */
    public OkHttpRequest getTestByType(int page,String fid,String type, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PAGE_KEY, page);
        paramsMap.put(AppConstants.ParamKey.TYPE_KEY, type);
        paramsMap.put(AppConstants.ParamKey.FID , fid);
        return ApiClient.create(AppConstants.RequestPath.JS_TEST, paramsMap).tag("").get(callback);
    }
}
