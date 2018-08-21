package com.citypass.sellmanager.presenter.impl;

import android.content.Context;
import android.content.Intent;

import com.citypass.sellmanager.bean.Book;
import com.citypass.sellmanager.presenter.interfaces.Presenter;
import com.citypass.sellmanager.retiofitApi.DataManager;
import com.citypass.sellmanager.service.BookView;
import com.citypass.sellmanager.service.View;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 赵涛 on 2018/8/21.
 */
public class BookPresenter implements Presenter {


    private Context context;
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private BookView mBookView;
    private Book mBook;

    public BookPresenter(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate() {
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void attachView(View view) {
        mBookView = (BookView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getSearchBooks(String name, String tag, int start, int count) {
        mCompositeSubscription.add(manager.getSearchBooks(name, tag, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onCompleted() {
                        if (mBook != null) {
                            mBookView.onSuccess(mBook);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mBookView.onError("请求失败");
                    }

                    @Override
                    public void onNext(Book book) {
                        mBook = book;
                    }
                }));
    }
}
