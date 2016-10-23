package com.lcc.api;

import android.util.Pair;

import com.lcc.AppConstants;
import com.lcc.frame.net.okhttp.request.OkHttpRequest;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ApiClient {

    public static OkHttpRequest.Builder create(String path, Map paramsMap, Map headerMap) {
        return new OkHttpRequest.Builder()
                .url(AppConstants.RequestPath.BASE_URL + path).params(paramsMap).headers(headerMap);
    }

    public static OkHttpRequest.Builder create(String path, Map paramsMap) {
        return create(path, paramsMap, null);
    }

    public static OkHttpRequest.Builder create(String path) {
        return create(path, new ParamsMap());
    }

    public static OkHttpRequest.Builder createWithFile(String path, Map paramsMap, List<File>files) {
        Pair[] pairs = new Pair[files.size()];
        for (int i = 0; i < files.size(); i++) {
            Pair pair = new Pair("file", files.get(i));
            pairs[i] = pair;
        }

        return new OkHttpRequest.Builder()
                .url(AppConstants.RequestPath.BASE_URL + path)
                .params(paramsMap)
                .files(pairs)
                .headers(null);
    }

    public static OkHttpRequest.Builder createWithFiles(String path, Map paramsMap, List<File>files) {
        Pair[] pairs = new Pair[files.size()];
        for (int i = 0; i < files.size(); i++) {
            Pair pair = new Pair("file"+i, files.get(i));
            pairs[i] = pair;
        }

        return new OkHttpRequest.Builder()
                .url(AppConstants.RequestPath.BASE_URL + path)
                .params(paramsMap)
                .files(pairs)
                .headers(null);
    }

}
