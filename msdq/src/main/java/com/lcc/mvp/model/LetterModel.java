package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class LetterModel {

    /**
     * 获取自己的私信
     */
    public OkHttpRequest getSuperUserLetter( ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        return ApiClient.create(AppConstants.RequestPath.getSuperUserLetter, paramsMap).tag("")
                .get(callback);
    }
}
