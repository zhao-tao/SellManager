package com.citypass.sellmanager.retiofitApi;

import com.citypass.sellmanager.model.CardBean;
import com.citypass.sellmanager.model.HttpBean;
import com.citypass.sellmanager.model.HttpResult;
import com.citypass.sellmanager.model.SlotBean;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by 赵涛 on 2018/8/17.
 * Retrofit网络请求服务接口
 */
public interface DataService {

    @GET("slot.php?Act=inform")
    Observable<HttpResult<ArrayList<SlotBean>>> getSlotList(@Query("ImeiId") String imeiId, @Query("Md5Code") String md5);

    @GET("slot.php?Act=login")
    Observable<HttpBean> verifyUser(@Query("UserName") String name, @Query("PassWord") String passWord, @Query("Md5Code") String md5);

    @GET("slot.php?Act=cardinfo")
    Observable<HttpResult<ArrayList<CardBean>>> chooseCard(@Query("cardinfo") String cardinfo);

    @GET("slot.php?Act=supply")
    Observable<HttpBean> confirmSlot(@QueryMap Map<String, String> params);
}
