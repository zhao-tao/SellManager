package com.citypass.sellmanager.config;

import android.app.Application;

/**
 * Created by 赵涛 on 2018/8/22.
 * Application类
 */
public class SellApp extends Application {

    public static String Imei = "710033000";
    public static String userId = "";
    public static SellApp sellApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sellApp = this;
    }
}
