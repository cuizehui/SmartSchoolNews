package com.example.cuizehui.smartschool.net;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cuizehui on 2016/12/29at ${time}.
 */
public class OkhttpFactory {


    OkHttpClient okHttpClient = new OkHttpClient();
    OkHttpClient newClient = okHttpClient.newBuilder()
            .cache(provideCache())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

//执行网络请求
    String run(String url) throws IOException {

        Request request = new Request.Builder().url(url).build();
        Response response = newClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }
        else
        {
            throw new IOException("Unexpected code " + response);
        }

    }

    public Cache provideCache() {
        return new Cache(mContext.getCacheDir(), 10240*1024);
    }



    OkHttpClient okHttpClient1 = new OkHttpClient();

    OkHttpClient newClient1 = okHttpClient1.newBuilder()
            .addNetworkInterceptor(new CacheInterceptor())
            .cache(provideCache())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

     //自己写的拦截器
    //如果服务器不支持缓存则需要重写Response头部
    public class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Response response = chain.proceed(request);

            Response response1 = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    //cache for 30 days
                    .header("Cache-Control", "max-age=" + 3600 * 24 * 30)
                    .build();
            return response1;
        }
    }
}
