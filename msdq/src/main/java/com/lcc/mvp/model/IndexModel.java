package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class IndexModel {

    /**
     * 获取活动数据
     * @param callback 回调函数 默认返回的String 其实可以是其他的javabean
     */
    public OkHttpRequest getActivity( ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        // TODO: 16/4/20 改为post
        return ApiClient.create(AppConstants.RequestPath.GET_ACTIVITY, paramsMap).tag("").get(callback);
    }

    /**
     * 获取活动数据
     */
    public OkHttpRequest getWeekData(int page, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PAGE_KEY, page);
        return ApiClient.create(AppConstants.RequestPath.GET_WEEKDATA, paramsMap).tag("").get(callback);
    }
}
