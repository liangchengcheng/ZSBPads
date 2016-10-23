package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.entity.Replay;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class CommentsModel {

    /**
     * 获取评论的列表
     */
    public OkHttpRequest getComments(int page,String nid, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PAGE_KEY, page);
        paramsMap.put(AppConstants.ParamKey.NID, nid);
        return ApiClient.create(AppConstants.RequestPath.COMMENTS_URL, paramsMap).tag("").get(callback);
    }

    /**
     * 发送评论到服务器
     */
    public OkHttpRequest sendComments(Replay replay, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();

        paramsMap.put("content", replay.getContent());
        paramsMap.put("nid", replay.getNid());
        paramsMap.put("pid", replay.getPid());
        paramsMap.put("type", replay.getType());
        if (replay.getContent().contains("@")){
            paramsMap.put("replay_author", replay.getAuthor_code());
            paramsMap.put("replay_nickname",replay.getReplay_nickname());
        }else {
            paramsMap.put("replay_author", replay.getReplay_author());
            paramsMap.put("replay_nickname","");
        }
        return ApiClient.create(AppConstants.RequestPath.CommentsAdd, paramsMap).tag("").get(callback);
    }
}
