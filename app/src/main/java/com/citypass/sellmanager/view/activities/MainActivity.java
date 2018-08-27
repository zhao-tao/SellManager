package com.citypass.sellmanager.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citypass.sellmanager.R;
import com.citypass.sellmanager.model.SlotBean;
import com.citypass.sellmanager.retiofitApi.HttpDataListener;
import com.citypass.sellmanager.retiofitApi.HttpDataSubscriber;
import com.citypass.sellmanager.retiofitApi.RetrofitHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvTest;
    private ImageView ivBook;
    private Button button;
    private RecyclerView rvSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvSlot = findViewById(R.id.rv_slot);
        tvTest = findViewById(R.id.tv_book);
        ivBook = findViewById(R.id.iv_book);
        button = findViewById(R.id.button);

        GridLayoutManager LayoutManager = new GridLayoutManager(this, 10);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestSlot();
            }
        });
    }

    private void requestSlot() {
        HttpDataListener listener = new HttpDataListener<ArrayList<SlotBean>>() {
            @Override
            public void onNext(ArrayList<SlotBean> slotBeans) {
                sortSlotData(slotBeans);

                tvTest.setText(slotBeans.get(0).getCardImg());
                Glide.with(MainActivity.this).load(slotBeans.get(0).getCardImg()).into(ivBook);
            }
        };
        RetrofitHelper.getInstance().getSlotList(new HttpDataSubscriber(listener, MainActivity.this), "710033000103");

    }

    /**
     * 对货道数据进行排序和规整，（1-64货道）
     *
     * @param slotBeans
     */
    private void sortSlotData(ArrayList<SlotBean> slotBeans) {


    }


}
