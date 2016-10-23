package com.lcc.api;

import android.text.TextUtils;
import com.lcc.AppConstants;
import com.lcc.frame.data.DataManager;
import com.lcc.utils.SharePreferenceUtil;

import java.util.HashMap;

public class ParamsMap extends HashMap<String, String> {
    public ParamsMap() {
        //设备的id
        put(AppConstants.ParamKey.CLIENT_ID_KEY, AppConstants.ParamDefaultValue.CLIENT_ID);
        //token
        put(AppConstants.ParamKey.TOKEN, SharePreferenceUtil.getUserTk());
        //用户:就是他的手机号
        put(AppConstants.ParamKey.AUTHOR, DataManager.getUserName());
    }

    public void put(String key, int value) {
        super.put(key, value + "");
    }

    @Override
    public String put(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value))
            return  "";
        return super.put(key, value);
    }
}
