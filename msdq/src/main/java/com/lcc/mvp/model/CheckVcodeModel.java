package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class CheckVcodeModel {

    /**
     * 校验手机的验证码
     */
    public OkHttpRequest checkVerifySMS(String phone, String code, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("phone", phone);
        paramsMap.put("code", code);
        return ApiClient.create(AppConstants.RequestPath.checkVcode, paramsMap).tag("").post(callback);
    }

}
