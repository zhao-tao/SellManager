package com.citypass.sellmanager.retiofitApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 赵涛 on 2018/8/17.
 * Retrofit网络请求代理(主要用于retrofit初始化)
 */
public class RetrofitHelper {

    private static final int TIMEOUT = 5;
    private static final String BASE_URL = "https://api.douban.com/v2/";
    private Retrofit retrofit;
    private final RetrofitService retrofitService;

    private static RetrofitHelper instance = null;

    private RetrofitHelper() {
        retrofit = init();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    private Retrofit init() {
        Retrofit retrofit;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        优化网络请求Log，设置请求和读写的超时时间
        builder.addInterceptor(new LogInterceptor())
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            instance = new RetrofitHelper();
        }
        return instance;
    }


}
