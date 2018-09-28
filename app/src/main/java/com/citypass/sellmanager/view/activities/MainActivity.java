package com.citypass.sellmanager.view.activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.citypass.sellmanager.R;
import com.citypass.sellmanager.model.SlotBean;
import com.citypass.sellmanager.retiofitApi.HttpDataListener;
import com.citypass.sellmanager.retiofitApi.HttpDataSubscriber;
import com.citypass.sellmanager.retiofitApi.RetrofitHelper;
import com.citypass.sellmanager.view.adapter.SlotAdapter;
import com.citypass.sellmanager.view.dialog.LoginBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private TextView tvNotice;
    private RecyclerView rvSlot;
    private Button btnLogin;
    private Button btnCode;
    private SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btn_login);
        btnCode = findViewById(R.id.btn_code);
        Button btnRefresh = findViewById(R.id.btn_refresh);
        rvSlot = findViewById(R.id.rv_slot);
        tvNotice = findViewById(R.id.tv_notice);
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        checkUserInfo();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestSlot();
            }
        });

        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final EditText editText = new EditText(MainActivity.this);
                String imei = userInfo.getString("imei", "");
                builder.setTitle(TextUtils.isEmpty(imei) ? R.string.input_imei : R.string.change_imei);
                builder.setView(editText);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(editText.getText()) || editText.length() != 3) {
                            Toast.makeText(MainActivity.this, R.string.input_error, Toast.LENGTH_SHORT).show();
                        } else {
                            btnCode.setText("设备编号:" + editText.getText());
                            SharedPreferences.Editor edit = userInfo.edit();
                            edit.putString("imei", editText.getText().toString());
                            edit.apply();
                        }
                    }
                });
                builder.show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });
    }

    private void showLoginDialog() {
        new LoginBuilder(MainActivity.this, new LoginBuilder.ICallBack() {
            @Override
            public void editButton(String text) {
                btnLogin.setText(TextUtils.isEmpty(text) ? "未登陆" : text);
                if (TextUtils.isEmpty(text)) {
                    rvSlot.setVisibility(View.INVISIBLE);
                    tvNotice.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private boolean checkUserInfo() {
        if (userInfo != null && TextUtils.isEmpty(userInfo.getString("imei", ""))) {
            btnCode.setText(userInfo.getString("imei", ""));
        }
        if (userInfo == null || TextUtils.isEmpty(userInfo.getString("name", ""))) {
            showLoginDialog();
            return false;
        }
        btnLogin.setText(userInfo.getString("name", ""));
        return true;
    }

    private void requestSlot() {
        if (!checkUserInfo()) {
            return;
        }
        HttpDataListener listener = new HttpDataListener<ArrayList<SlotBean>>() {
            @Override
            public void onNext(ArrayList<SlotBean> slotBeans) {
                tvNotice.setText(getResources().getString(R.string.attention));
                Collections.sort(slotBeans, new SortById());
                initSlotList(slotBeans);
            }
        };
        RetrofitHelper.getInstance().getSlotList(new HttpDataSubscriber(listener, MainActivity.this), "710033000103");

    }

    private void initSlotList(ArrayList<SlotBean> slotBeans) {
        rvSlot.setVisibility(View.VISIBLE);
        tvNotice.setVisibility(View.VISIBLE);
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

