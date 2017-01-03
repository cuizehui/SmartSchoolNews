package com.example.cuizehui.smartschool.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by cuizehui on 2016/12/24at ${time}.
 */
public class RetrofitFactory {
    private static String baseUrl = "http://192.168.0.105:8080/zhbj/";

    private static Retrofit stringRetrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)

            //转化工厂
            .addConverterFactory(ScalarsConverterFactory.create())

            .build();

    //解析返回的字符串
    public static RetrofitService getStringService() {
        RetrofitService service = stringRetrofit.create(RetrofitService.class);
        return service;
    }

}
