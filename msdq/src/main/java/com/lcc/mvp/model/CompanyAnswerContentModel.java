package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class CompanyAnswerContentModel {

    /**
     * 获取活动数据的内容
     * @param callback 回调函数 默认返回的String 其实可以是其他的javabean
     */
    public OkHttpRequest getArticleContent(String id, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        // TODO: 16/4/20 改为post
        paramsMap.put(AppConstants.ParamKey.FID, id);
        return ApiClient.create(AppConstants.RequestPath.GET_MENU_CONTENT, paramsMap).tag("").get(callback);
    }
}
