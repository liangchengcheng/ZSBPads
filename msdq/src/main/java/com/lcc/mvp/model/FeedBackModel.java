package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class FeedBackModel {

    /**
     * 提交用户的反馈意见
     */
    public OkHttpRequest PostMessage(String word, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("content_body", word);
        return ApiClient.create(AppConstants.RequestPath.AddFeedBackService, paramsMap).tag("").
                get(callback);
    }
}
