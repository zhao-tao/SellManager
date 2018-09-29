package com.citypass.sellmanager.retiofitApi;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 赵涛 on 2018/8/27.
 */
public abstract class HttpDataListener<T> {
    public abstract void onNext(T t);

    public void onError(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
