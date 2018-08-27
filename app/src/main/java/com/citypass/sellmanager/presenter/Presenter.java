package com.citypass.sellmanager.presenter;

import android.content.Intent;

import com.citypass.sellmanager.service.View;

/**
 * Created by 赵涛 on 2018/8/21.
 * 对应Activity的生命周期
 * 主要用于网络请求和数据获取
 */
public interface Presenter {
    void onCreate();
    void onStart();
    void onStop();
    void pause();
//    用于绑定我们定义的UI
    void attachView(View view);
    void attachIncomingIntent(Intent intent);
}
