package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class ChoiceTypeModel {

    /**
     * 获取一级目录的列表
     */
    public OkHttpRequest getType1( ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        return ApiClient.create(AppConstants.RequestPath.GET_TYPE1, paramsMap).tag("").get(callback);
    }


    /**
     * 获取二级目录列表
     */
    public OkHttpRequest getType2(String type, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("nid", type);
        return ApiClient.create(AppConstants.RequestPath.GET_TYPE2, paramsMap).tag("").get(callback);
    }


    /**
     * 编辑职业
     */
    public OkHttpRequest editType(String type, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("zy", type);
        return ApiClient.create(AppConstants.RequestPath.EditTypeServiceAPI, paramsMap).tag("").get(callback);
    }
}
