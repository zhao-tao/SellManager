package com.citypass.sellmanager.retiofitApi;

import android.text.TextUtils;

import com.citypass.sellmanager.config.Utils;
import com.citypass.sellmanager.model.CardBean;
import com.citypass.sellmanager.model.HttpBean;
import com.citypass.sellmanager.model.HttpResult;
import com.citypass.sellmanager.model.SlotBean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
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

import static com.citypass.sellmanager.config.SellApp.userId;

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
                throw new HttpException(httpResult.getDes());
            }

            return httpResult.getData();
        }
    }

    public void verifyUser(Subscriber<HttpBean> subscriber, String userName, String passWord) {
        Observable observable = dataService.verifyUser(userName, passWord, Utils.getMd5("login" + userName + passWord)).map(new DataResult<HttpBean>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取对应售货机的所有货道数据
     *
     * @param subscriber
     * @param ImeiId
     */
    public void getSlotList(Subscriber<ArrayList<SlotBean>> subscriber, String ImeiId) {
        Observable observable = dataService.getSlotList(ImeiId, Utils.getMd5("inform" + ImeiId)).map(new HttpResultFunc<ArrayList<SlotBean>>());
        toSubscribe(observable, subscriber);
    }

    public void chooseCard(Subscriber<ArrayList<CardBean>> subscriber) {
        Observable observable = dataService.chooseCard(Utils.getMd5("cardinfo")).map(new HttpResultFunc<ArrayList<CardBean>>());
        toSubscribe(observable, subscriber);
    }

    public void confirmSlot(Subscriber<HttpBean> subscriber, String slotId, String cardId, int balance) {
        HashMap<String, String> params = new HashMap<>();
        params.put("SlotId", slotId);
        if (!TextUtils.isEmpty(cardId)) {
            params.put("CardId", cardId);
        }
        params.put("Balance", balance + "");
        params.put("UserId", userId);
        params.put("Md5Code", Utils.getMd5("supply" + "jiangnan"));
        Observable observable = dataService.confirmSlot(params).map(new DataResult<HttpBean>());
        toSubscribe(observable, subscriber);

    }
}
