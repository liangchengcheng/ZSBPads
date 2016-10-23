package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.entity.Answer;
import com.lcc.entity.CompanyDescription;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class ComStateModel {

    public OkHttpRequest isfavAnswer(String nid, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, nid);
        return ApiClient.create(AppConstants.RequestPath.ISFAV, paramsMap).tag("").get(callback);
    }

    /**
     * 收藏的原生资料的问题
     */
    public OkHttpRequest favCom(CompanyDescription article, String type, ResultCallback<String>
            callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        paramsMap.put(AppConstants.ParamKey.TYPE_KEY, type);
        paramsMap.put("fav_title", article.getCompany_name());
        return ApiClient.create(AppConstants.RequestPath.UserFavAdd, paramsMap).tag("").get(callback);
    }

    /**
     * 取消收藏的原生资料的问题
     */
    public OkHttpRequest UnfavCom(CompanyDescription article,String type ,ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.NID, article.getMid());
        paramsMap.put("type", type);
        return ApiClient.create(AppConstants.RequestPath.DDELETEFAV, paramsMap).tag("").get(callback);
    }

}
