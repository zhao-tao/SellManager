package com.citypass.sellmanager.retiofitApi;

import android.content.Context;

import com.citypass.sellmanager.bean.Book;

import rx.Observable;

/**
 * Created by 赵涛 on 2018/8/17.
 * 更方便的调用RetrofitService定义的方法
 */
public class DataManager {

    private final RetrofitService service;

    public DataManager(Context context) {
        service = RetrofitHelper.getInstance().getService();
    }

    public Observable<Book> getSearchBooks(String name, String tag, int start, int count) {
        return service.getSearchBook(name, tag, start, count);
    }
}
