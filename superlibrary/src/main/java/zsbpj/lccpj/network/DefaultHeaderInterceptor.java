package zsbpj.lccpj.network;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:   默认的头部
 */
// TODO: 2016/1/13 最好可以写入新的
public class DefaultHeaderInterceptor implements Interceptor {

    public DefaultHeaderInterceptor(){

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Headers.Builder builder = new Headers.Builder();
        //这里添加的是header的头部文件
        builder.add("Content-Encoding", "gzip")
                .add("platform", "android");
        //创建一个request
        Request compressedRequest = request
                .newBuilder()
                .headers(builder.build())
                .build();
        return chain.proceed(request);
    }
}
