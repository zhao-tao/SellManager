package com.citypass.sellmanager.retiofitApi;

import com.citypass.sellmanager.bean.Book;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 赵涛 on 2018/8/17.
 * Retrofit网络请求服务接口
 */
public interface RetrofitService {
    @GET("book/search")
    Observable<Book> getSearchBook(@Query("q") String name,
                                   @Query("tag") String tag,
                                   @Query("start") int start,
                                   @Query("count") int count);
}
