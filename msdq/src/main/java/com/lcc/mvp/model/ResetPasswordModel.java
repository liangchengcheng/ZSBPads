package com.lcc.mvp.model;

import com.google.gson.JsonElement;
import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.data.DataManager;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class ResetPasswordModel {

    /**
     * 获取短信验证码
     */
    public OkHttpRequest getVerifySMS(String phone, String password, ResultCallback<JsonElement> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PHONE_KEY, phone);
        paramsMap.put(AppConstants.ParamKey.PASSWORD_KEY, password);
        paramsMap.put(AppConstants.ParamKey.TYPE_KEY, AppConstants.ParamDefaultValue.TYPR_RESET_PASSWORD);
        return ApiClient.create(AppConstants.RequestPath.SEND_VERIFY_CODE, paramsMap).tag("").post(callback);
    }

    /**
     * 重置密码
     */
    public OkHttpRequest resetPassword( String phone,String pwd, String new_pwd,String code,
            ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("phone", phone);
        paramsMap.put("o_password", pwd);
        paramsMap.put(AppConstants.ParamKey.VERIFY_CODE_KEY, "");
        paramsMap.put("n_password",new_pwd);
        paramsMap.put("verify_code",code);
        return ApiClient.create(AppConstants.RequestPath.RESET_PASSWORD, paramsMap).tag("").post(callback);
    }

}
