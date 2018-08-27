package com.citypass.sellmanager.presenter;

import android.content.Context;
import android.content.Intent;

import com.citypass.sellmanager.model.SlotBean;
import com.citypass.sellmanager.service.SlotView;
import com.citypass.sellmanager.service.View;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 赵涛 on 2018/8/23.
 */
public class SlotPresenter implements Presenter {

    private final Context context;
    private SlotView mView;

    public SlotPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void attachView(View view) {
        mView = (SlotView) view;

    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getSlotList() {


//        compositeSubscription.add(dataManager.getSlotList("710033000103")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ArrayList<SlotBean>>() {
//
//                    private ArrayList<SlotBean> slotBeans;
//
//                    @Override
//                    public void onCompleted() {
//                        if (slotBeans != null) {
//                            mView.onSuccess(slotBeans);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(ArrayList<SlotBean> slotBeans) {
//                        this.slotBeans = slotBeans;
//                    }
//                }));
    }
}
