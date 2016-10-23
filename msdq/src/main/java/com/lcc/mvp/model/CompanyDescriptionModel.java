package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

public class CompanyDescriptionModel {
    public OkHttpRequest getCompanyDescriptionList(int page,String company_name,String area,
                                                   ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PAGE_KEY, page);
        paramsMap.put("company_name", company_name);
        paramsMap.put("area", area);
        return ApiClient.create(AppConstants.RequestPath.COMPANY_DES, paramsMap).tag("").get(callback);
    }
}
