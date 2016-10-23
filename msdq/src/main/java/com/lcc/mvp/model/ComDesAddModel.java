package com.lcc.mvp.model;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.entity.ComTestAdd;
import com.lcc.entity.CompanyDescription;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

import java.io.File;
import java.util.List;

public class ComDesAddModel {

    /**
     * 增加新的公司的服务
     */
    public OkHttpRequest ComDesAdd(CompanyDescription replay, List<File>files, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("company_name", replay.getCompany_name());
        paramsMap.put("company_phone", replay.getCompany_phone());
        paramsMap.put("description", replay.getCompany_description());
        paramsMap.put("location", replay.getLocation());

        paramsMap.put("provinces", replay.getProvince());
        paramsMap.put("citys", replay.getCity());
        paramsMap.put("areas", replay.getArea());

        paramsMap.put("imagepath", replay.getCompany_image());
        paramsMap.put("areaid", replay.getAreaid());

        return ApiClient.createWithFile(AppConstants.RequestPath.saveCompanyForAndroid,
                paramsMap,files)
                .upload(callback);
    }
}
