package com.citypass.sellmanager.retiofitApi;

import com.citypass.sellmanager.model.SlotBean;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 赵涛 on 2018/8/17.
 * Retrofit网络请求服务接口
 */
public interface DataService {

    @GET("slot.php?Act=inform")
    Observable<HttpResult<ArrayList<SlotBean>>> getSlotList(@Query("ImeiId") String imeiId);
}
