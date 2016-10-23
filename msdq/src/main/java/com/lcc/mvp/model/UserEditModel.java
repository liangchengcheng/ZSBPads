package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.db.test.UserInfo;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;
import java.io.File;
import java.util.List;

public class UserEditModel {

    /**
     * 提交用户的个人信息
     */
    public OkHttpRequest userEdit(UserInfo userInfo, List<File> files, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("nickname", userInfo.getNickname());
        paramsMap.put("xb", userInfo.getXb());
        paramsMap.put("email", userInfo.getEmail());
        paramsMap.put("qm", userInfo.getQm());
        paramsMap.put("user_image", userInfo.getUser_image());
        return ApiClient.createWithFile(AppConstants.RequestPath.EditServiceAPI,
                paramsMap,files)
                .upload(callback);
    }
}
