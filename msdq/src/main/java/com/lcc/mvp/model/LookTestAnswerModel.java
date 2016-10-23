package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.entity.TestEntity;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class LookTestAnswerModel {

    /**
     * 我的收藏的资料
     */
    public OkHttpRequest getTestAnswer(int page,String fid, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PAGE_KEY, page);
        paramsMap.put(AppConstants.ParamKey.FID, fid);
        return ApiClient.create(AppConstants.RequestPath.LOOK_TEST_ANSWER_LIST, paramsMap).tag("")
                .get(callback);
    }

    /**
     * 收藏的原生资料的问题
     */
    public OkHttpRequest favQuestion(TestEntity article, String type, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        paramsMap.put(AppConstants.ParamKey.TYPE_KEY, type);
        paramsMap.put("fav_title",article.getTitle());
        return ApiClient.create(AppConstants.RequestPath.UserFavAdd, paramsMap).tag("").get(callback);
    }

    /**
     * 取消收藏的原生资料的问题
     */
    public OkHttpRequest UnfavQuestion(TestEntity article, String type,ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        paramsMap.put(AppConstants.ParamKey.TYPE_KEY, type);
        return ApiClient.create(AppConstants.RequestPath.DDELETEFAV, paramsMap).tag("").get(callback);
    }


    /**
     * 收藏的原生资料的问题
     */
    public OkHttpRequest getUserList(String nid,int page, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, nid);
        paramsMap.put(AppConstants.ParamKey.PAGE_KEY, page);
        return ApiClient.create(AppConstants.RequestPath.getUserList, paramsMap).tag("").get(callback);
    }

}
