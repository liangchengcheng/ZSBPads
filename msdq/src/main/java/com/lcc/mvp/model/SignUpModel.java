package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class SignUpModel {

    /**
     * 获取短信验证码
     */
    public OkHttpRequest getVerifySMS(String phone, String password, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PHONE_KEY, phone);
        paramsMap.put(AppConstants.ParamKey.PASSWORD_KEY, password);
        return ApiClient.create(AppConstants.RequestPath.SEND_VERIFY_CODE, paramsMap).tag("").post(callback);
    }

    /**
     * 用户注册
     */
    public OkHttpRequest signUp(String username,String phone, String password, String verify_code, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PHONE_KEY, username);
        paramsMap.put(AppConstants.ParamKey.PHONE, phone);
        paramsMap.put(AppConstants.ParamKey.PASSWORD_KEY, password);
        paramsMap.put(AppConstants.ParamKey.VERIFY_CODE_KEY, verify_code);
        paramsMap.put(AppConstants.ParamKey.GRANT_TYPE_KEY, AppConstants.ParamDefaultValue.GRANT_TYPE);
        return ApiClient.create(AppConstants.RequestPath.OAUTH, paramsMap).tag("").get(callback);
    }

}
