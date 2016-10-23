package zsbpj.lccpj.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import java.util.ArrayList;
import java.util.List;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import zsbpj.lccpj.utils.ObjectUtils;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    {  }
 */
public class RetrofitBuilder {

    private String baseUrl;
    private Retrofit mRetrofit;
    private OkHttpClient client;
    private RetrofitBuilder(){}

    /**
     * 单例模式 唯一对象
     */
    private static class SingleHolder{
        private static final RetrofitBuilder INSTANCE=new RetrofitBuilder();
    }

    /**
     * 获取单例对象
     */
    public static synchronized RetrofitBuilder get(){
        return SingleHolder.INSTANCE;
    }

    protected Retrofit.Builder newRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    /**
     * 创建一个retrofit对象
     */
    public Retrofit retrofit(){
        if (baseUrl==null){
            ObjectUtils.checkNotNull(baseUrl,"base url is empty");
        }

        if (mRetrofit==null){
            Retrofit.Builder builder=newRetrofitBuilder();

            mRetrofit=builder.baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return mRetrofit;
    }

    public <T> T create(Class<T> clazz) {
        return get().retrofit().create(clazz);
    }

    public static class Builder{
        private String baseUrl;
//        private Interceptor interceptor;
        private OkHttpClient client=new OkHttpClient();
        private final List<Interceptor> interceptors = new ArrayList<>();

        /**
         * 创建一个RetrofitBuilder
         */
        public RetrofitBuilder build(){
            if (baseUrl==null){
                ObjectUtils.checkNotNull(baseUrl,"baseurl is empty");
            }
            RetrofitBuilder retrofitBuilder = get();
            retrofitBuilder.baseUrl = baseUrl;
//            interceptors.add(interceptor);
            client.interceptors().addAll(interceptors);
            retrofitBuilder.client=client;
            return  retrofitBuilder;
        }

        public void ensureSaneDefaults() {
//
//            if (interceptor == null) {
//                interceptor = new DefaultHeaderInterceptor();
//            }

            if (client == null) {
                client = new OkHttpClient();
            }
        }

        public Builder client(OkHttpClient client) {
            client = client;
            return this;
        }

//        public Builder headerInterceptor(Interceptor headerInterceptor) {
//            interceptor = headerInterceptor;
//            return this;
//        }

        public Builder addInterceptors(Interceptor interceptor) {
            interceptors.add(interceptor);
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            ObjectUtils.checkNotNull(baseUrl, "baseUrl == null");
            this.baseUrl = baseUrl;
            return this;
        }
    }
}
