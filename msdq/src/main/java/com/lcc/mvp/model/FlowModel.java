package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class FlowModel {

    /**
     * 获取文章的列表
     */
    public OkHttpRequest getWhoLikeMe(int page,String type, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PAGE_KEY, page);
        paramsMap.put("you", type);
        return ApiClient.create(AppConstants.RequestPath.getWhoLikeMeList, paramsMap).tag("").get(callback);
    }


    /**
     * 获取文章的列表
     */
    public OkHttpRequest getILikeWhoList(int page,String type, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PAGE_KEY, page);
        paramsMap.put("me", type);
        return ApiClient.create(AppConstants.RequestPath.getILikeWhoList, paramsMap).tag("").get(callback);
    }



}
