package com.citypass.sellmanager.retiofitApi;

import rx.Subscriber;

/**
 * Created by 赵涛 on 2018/8/17.
 * 自定义Subscriber
 * 1 是否显示网络加载的动画
 */
public class DataSubscriber<T> extends Subscriber<T> {


    private void showProgress() {

    }

    private void dismissProgress(){

    }

    @Override
    public void onStart() {
        showProgress();
    }

    @Override
    public void onCompleted() {
        dismissProgress();
    }

    @Override
    public void onError(Throwable e) {


    }

    @Override
    public void onNext(T t) {

    }
}
