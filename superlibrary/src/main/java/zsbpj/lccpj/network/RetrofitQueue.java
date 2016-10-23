package zsbpj.lccpj.network;

import com.squareup.okhttp.ResponseBody;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;
import zsbpj.lccpj.network.callback.ErrorModel;
import zsbpj.lccpj.network.callback.GenericCallback;
import zsbpj.lccpj.utils.ObjectUtils;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    {  }
 */
public class RetrofitQueue<T> implements Callback<T> {

    private final GenericCallback<T> callback;
    private Call<T> delegate;

    /**
     * 构造函数
     */
    public RetrofitQueue(GenericCallback<T> callback) {
        this.callback = callback;
    }

    /**
     * 请求并且回调
     */
    public void enqueue(Call<T> delegate) {
        ObjectUtils.checkNotNull(delegate, "delegate == null");
        this.delegate = delegate;
        //开始请求的标记
        callback.startRequest();
        //直接开始请求
        delegate.enqueue(this);
    }

    /**
     * 退出
     */
    public void cancel() {
        if (delegate != null) {
            delegate.cancel();
        }
    }

    @Override
    public void onResponse(retrofit.Response<T> response, Retrofit retrofit) {
        if (response.isSuccess()) {
            //返回请求成的结果
            callback.respondSuccess(response.body());
        } else {
            final int statusCode = response.code();
            final ResponseBody errorBody = response.errorBody();
            ErrorModel errorModel = null;
            if (errorBody != null) {
                try {
                    errorModel = new ErrorModel(statusCode, errorBody.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            processError(statusCode, errorModel);
        }
        //结束请求
        callback.endRequest();
    }

    @Override
    public void onFailure(Throwable t) {
             // 无网络
        if (t instanceof ConnectException) {
            callback.eNetUnreach(t);
            // 链接超时
        } else if (t instanceof SocketTimeoutException) {
            callback.errorSocketTimeout(t);
        } else {
            callback.respondWithError(t);
        }
        callback.endRequest();
    }

    private void processError(final int statusCode, ErrorModel errorModel) {
        switch (statusCode) {
            case 401:
                callback.errorUnauthorized(errorModel);
                break;
            case 403:
                callback.errorForbidden(errorModel);
                break;
            case 404:
                callback.errorNotFound(errorModel);
                break;
            case 422:
                callback.errorUnprocessable(errorModel);
                break;
        }
        callback.error(errorModel);
    }
}

