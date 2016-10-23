package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class FavModel {

    /**
     * 获取自己的收藏的列表
     */
    public OkHttpRequest getFavList(int page,String type, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PAGE_KEY, page);
        paramsMap.put(AppConstants.ParamKey.TYPE_KEY, type);
        return ApiClient.create(AppConstants.RequestPath.getUserFavList, paramsMap).tag("").get(callback);
    }
}
