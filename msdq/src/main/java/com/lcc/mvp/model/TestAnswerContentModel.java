package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.entity.Answer;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class TestAnswerContentModel {

    public OkHttpRequest isfavAnswer(String nid, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, nid);
        return ApiClient.create(AppConstants.RequestPath.ISFAV, paramsMap).tag("").get(callback);
    }

    /**
     * 收藏的原生资料的问题
     */
    public OkHttpRequest favAnswer(Answer article, String type, String title, ResultCallback<String>
            callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        paramsMap.put(AppConstants.ParamKey.TYPE_KEY, type);
        paramsMap.put("fav_title", title);
        return ApiClient.create(AppConstants.RequestPath.UserFavAdd, paramsMap).tag("").get(callback);
    }

    /**
     * 取消收藏的原生资料的问题
     */
    public OkHttpRequest UnfavAnswer(Answer article,String type ,ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        paramsMap.put("type", type);
        return ApiClient.create(AppConstants.RequestPath.DDELETEFAV, paramsMap).tag("").get(callback);
    }

    /**
     * 获取新闻的具体的内容的详情
     */
    public OkHttpRequest getContent(String mid, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("mid", mid);
        return ApiClient.create(AppConstants.RequestPath.getAnswerContent, paramsMap).tag("").get(callback);
    }

    /**
     * 给资料的答案点赞
     */
    public OkHttpRequest GoodAnswer(Answer article, String type, String title, ResultCallback<String>
            callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("true_author", article.getPhone());
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        paramsMap.put(AppConstants.ParamKey.TYPE_KEY, type);
        paramsMap.put("good_title", title);
        return ApiClient.create(AppConstants.RequestPath.UserGoodAdd, paramsMap).tag("").get(callback);
    }

    public OkHttpRequest UnGoodAnswer(Answer article,String type, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        paramsMap.put("type",type);
        return ApiClient.create(AppConstants.RequestPath.deleteUserGood, paramsMap).tag("").get(callback);
    }
}
