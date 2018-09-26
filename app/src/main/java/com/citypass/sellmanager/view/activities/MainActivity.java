package com.citypass.sellmanager.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.citypass.sellmanager.R;
import com.citypass.sellmanager.model.SlotBean;
import com.citypass.sellmanager.retiofitApi.HttpDataListener;
import com.citypass.sellmanager.retiofitApi.HttpDataSubscriber;
import com.citypass.sellmanager.retiofitApi.RetrofitHelper;
import com.citypass.sellmanager.view.adapter.SlotAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private TextView tvTest;
    private RecyclerView rvSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvSlot = findViewById(R.id.rv_slot);
        tvTest = findViewById(R.id.tv_book);
        Button button = findViewById(R.id.button);

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
                tvTest.setText(getResources().getString(R.string.attention));
                Collections.sort(slotBeans, new SortById());
                initSlotList(slotBeans);
            }
        };
        RetrofitHelper.getInstance().getSlotList(new HttpDataSubscriber(listener, MainActivity.this), "710033000103");

    }

    private void initSlotList(ArrayList<SlotBean> slotBeans) {
        GridLayoutManager LayoutManager = new GridLayoutManager(this, 10);
        rvSlot.setLayoutManager(LayoutManager);
        SlotAdapter slotAdapter = new SlotAdapter(this, slotBeans);
        rvSlot.setAdapter(slotAdapter);
        slotAdapter.setOnClickListener(new SlotAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_LONG).show();
            }
        });

    }
}

class SortById implements Comparator {
    public int compare(Object o1, Object o2) {
        SlotBean s1 = (SlotBean) o1;
        SlotBean s2 = (SlotBean) o2;
        if (s1.getSlotId() > s2.getSlotId())
            return 1;
        return -1;
    }
}

