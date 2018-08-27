package com.citypass.sellmanager.service;

import com.citypass.sellmanager.model.Book;

/**
 * Created by 赵涛 on 2018/8/21.
 * 将presenter中的数据进行展示
 */
public interface BookView extends View {
    void onSuccess(Book mbook);

    void onError(String result);
}
