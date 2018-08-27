package com.citypass.sellmanager.service;

import com.citypass.sellmanager.model.SlotBean;

import java.util.ArrayList;

/**
 * Created by 赵涛 on 2018/8/21.
 */
public interface SlotView extends View {

    void onSuccess(ArrayList<SlotBean> mSlot);

    void onError(String result);
}
