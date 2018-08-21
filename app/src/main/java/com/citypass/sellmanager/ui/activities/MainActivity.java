package com.citypass.sellmanager.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.citypass.sellmanager.R;
import com.citypass.sellmanager.bean.Book;
import com.citypass.sellmanager.presenter.impl.BookPresenter;
import com.citypass.sellmanager.service.BookView;

public class MainActivity extends AppCompatActivity {

    private TextView tvTest;
    private ImageView ivBook;
    private Button button;
    private BookPresenter mBookPresenter = new BookPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTest = findViewById(R.id.tv_book);
        ivBook = findViewById(R.id.iv_book);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBookPresenter.getSearchBooks("金瓶梅", null, 0, 1);
            }
        });

        mBookPresenter.onCreate();
        mBookPresenter.attachView(mBookView);


//        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.douban.com/v2/")
//                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//
//        RetrofitService service = retrofit.create(RetrofitService.class);
//        Observable<Book> observable = service.getSearchBook("金瓶梅", null, 0, 1);
//
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Book>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Book book) {
//
//                    }
//                });


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


    private BookView mBookView = new BookView() {
        @Override
        public void onSuccess(Book mbook) {
            tvTest.setText(mbook.getBooks().get(0).toString());
            Glide.with(MainActivity.this).load(mbook.getBooks().get(0).getImages().getLarge()).into(ivBook);
        }

        @Override
        public void onError(String result) {
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBookPresenter.onStop();
    }
}
