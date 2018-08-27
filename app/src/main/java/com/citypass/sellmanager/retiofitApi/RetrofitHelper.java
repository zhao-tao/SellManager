package com.citypass.sellmanager.retiofitApi;

import com.citypass.sellmanager.model.SlotBean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 赵涛 on 2018/8/17.
 * Retrofit网络请求代理
 * 1 用于retrofit初始化
 * 2 调用RetrofitService中定义的接口方法
 */
public class RetrofitHelper {

    private static final int TIMEOUT = 5;
    private static final String BASE_URL = "http://sal.thecitypass.cn/api/";
    private DataService dataService;
    private Retrofit retrofit;
    private static RetrofitHelper instance = null;

    private RetrofitHelper() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

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

        dataService = retrofit.create(DataService.class);
    }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            instance = new RetrofitHelper();
        }
        return instance;
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private class DataResult<T> implements Func1<T, T> {
        @Override
        public T call(T t) {
            return t;
        }
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {

            try {
                String decode = URLDecoder.decode(httpResult.getDes(), "UTF-8");
                httpResult.setDes(decode);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (httpResult.getRet() < 0) {
                throw new RuntimeException(httpResult.getDes());
            }

            return httpResult.getData();
        }
    }

    /**
     * 获取对应售货机的所有货道数据
     *
     * @param subscriber
     * @param ImeiId
     */
    public void getSlotList(Subscriber<ArrayList<SlotBean>> subscriber, String ImeiId) {
        Observable observable = dataService.getSlotList(ImeiId).map(new HttpResultFunc<ArrayList<SlotBean>>());
        toSubscribe(observable, subscriber);
    }
}
