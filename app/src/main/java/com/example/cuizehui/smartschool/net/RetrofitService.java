package com.example.cuizehui.smartschool.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by cuizehui on 2016/12/24at ${time}.
 */
public interface RetrofitService {
    @GET("categories.json")
    Call<String> getnewsCenterData();

    @GET
    Call<String> getLunboData(@Url String url);

}
