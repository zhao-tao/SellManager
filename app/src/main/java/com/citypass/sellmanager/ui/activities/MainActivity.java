package com.citypass.sellmanager.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.citypass.sellmanager.R;
import com.citypass.sellmanager.bean.Book;
import com.citypass.sellmanager.retiofitApi.RetrofitService;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTest = findViewById(R.id.tv_test);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.douban.com/v2/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);
        Observable<Book> observable = service.getSearchBook("金瓶梅", null, 0, 1);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Book book) {

                    }
                });


//        call.enqueue(new Callback<Book>() {
//            @Override
//            public void onResponse(Call<Book> call, Response<Book> response) {
//                tvTest.setText(response.body().getBooks().toString());
//            }
//
//            @Override
//            public void onFailure(Call<Book> call, Throwable t) {
//
//            }
//        });
    }


}
