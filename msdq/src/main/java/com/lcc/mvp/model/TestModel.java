package com.lcc.mvp.model;

import android.text.TextUtils;

import com.lcc.AppConstants;
import com.lcc.api.ApiClient;
import com.lcc.api.ParamsMap;
import com.lcc.entity.TestEntity;
import com.lcc.frame.data.DataManager;
import com.lcc.frame.net.okhttp.callback.ResultCallback;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;
import com.lcc.utils.SharePreferenceUtil;

import java.util.List;

public class TestModel {

    public OkHttpRequest getTestList(int page, String options, String startTime, String endTime,
                                     String orders, ResultCallback<String> callback) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put(AppConstants.ParamKey.PAGE_KEY, page);
        paramsMap.put("options", options);
        String zy = DataManager.getZY();
        if (TextUtils.isEmpty(zy)) {
            paramsMap.put("type", "");
        } else {
            String z = zy.substring(zy.length() - 32, zy.length());
            paramsMap.put("type", z);
        }
        paramsMap.put("start", startTime);
        paramsMap.put("end", endTime);
        paramsMap.put("orders", orders);
        return ApiClient.create(AppConstants.RequestPath.TEST_LIST, paramsMap).tag("").get(callback);
    }
}
