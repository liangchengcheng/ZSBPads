package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.entity.CompanyTest;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class LookCompanyAnswerModel {
    public OkHttpRequest getCompanyAnswer(int page,String fid, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PAGE_KEY, page);
        paramsMap.put(AppConstants.ParamKey.FID, fid);
        return ApiClient.create(AppConstants.RequestPath.getTestanswerforLook, paramsMap).tag("").get(callback);
    }

    /**
     * 收藏
     */
    public OkHttpRequest favQuestion(CompanyTest article, String type, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        paramsMap.put(AppConstants.ParamKey.TYPE_KEY, type);
        paramsMap.put("fav_title",article.getTitle());
        return ApiClient.create(AppConstants.RequestPath.UserFavAdd, paramsMap).tag("").get(callback);
    }

    /**
     * 取消收藏
     */
    public OkHttpRequest UnfavQuestion(CompanyTest article, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        return ApiClient.create(AppConstants.RequestPath.DDELETEFAV, paramsMap).tag("").get(callback);
    }
}
