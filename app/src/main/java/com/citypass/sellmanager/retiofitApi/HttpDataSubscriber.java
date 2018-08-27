package com.citypass.sellmanager.retiofitApi;

import android.content.Context;

import rx.Subscriber;

/**
 * Created by 赵涛 on 2018/8/27.
 */
public class HttpDataSubscriber<T> extends Subscriber<T> {

    private HttpDataListener httpDataListener;
    private Context context;

    public HttpDataSubscriber(HttpDataListener httpDataListener, Context context) {
        this.httpDataListener = httpDataListener;
        this.context = context;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {


    }

    @Override
    public void onNext(T t) {
        if (httpDataListener != null) {
            httpDataListener.onNext(t);
        }
    }
}
