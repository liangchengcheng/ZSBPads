package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.entity.Answer;
import com.lcc.entity.CompanyAnswer;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class ComAnswerContentModel {

    public OkHttpRequest isfavAnswer(String nid, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, nid);
        return ApiClient.create(AppConstants.RequestPath.ISFAV, paramsMap).tag("").get(callback);
    }
    /**
     * 收藏的公司资料的问题
     */
    public OkHttpRequest favAnswer(CompanyAnswer article, String type, String title,
                                   ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        paramsMap.put(AppConstants.ParamKey.TYPE_KEY, type);
        paramsMap.put("fav_title",title);
        return ApiClient.create(AppConstants.RequestPath.UserFavAdd, paramsMap).tag("").get(callback);
    }

    /**
     * 取消收藏的公司资料的问题
     */
    public OkHttpRequest UnfavAnswer(CompanyAnswer article, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        return ApiClient.create(AppConstants.RequestPath.DDELETEFAV, paramsMap).tag("").get(callback);
    }

    /**
     * 获取公司资料的答案的具体内容
     */
    public OkHttpRequest getContent(String mid, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("mid", mid);
        return ApiClient.create(AppConstants.RequestPath.getComAnswerContent, paramsMap).tag("")
                .get(callback);
    }

    /**
     * 给资料的答案点赞
     */
    public OkHttpRequest GoodAnswer(CompanyAnswer article, String type, String title, ResultCallback<String>
            callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("true_author", article.getPhone());
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        paramsMap.put(AppConstants.ParamKey.TYPE_KEY, type);
        paramsMap.put("good_title", title);
        return ApiClient.create(AppConstants.RequestPath.UserGoodAdd, paramsMap).tag("").get(callback);
    }

    public OkHttpRequest UnGoodAnswer(CompanyAnswer article,String type, ResultCallback<String>
            callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        paramsMap.put("type",type);
        return ApiClient.create(AppConstants.RequestPath.deleteUserGood, paramsMap).tag("").get(callback);
    }
}
