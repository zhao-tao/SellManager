package com.citypass.sellmanager.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.citypass.sellmanager.R;
import com.citypass.sellmanager.model.SlotBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by 赵涛 on 2018-09-27.
 */
public class SlotDialog extends Dialog {


    private final SlotBean slotData;
    ImageView ivImg;
    TextView tvId;
    TextView tvName;
    Button btnChangeCard;
    Button btnSetNum;
    Button btnSure;

    public SlotDialog(@NonNull Context context, SlotBean slotData) {
        super(context, R.style.MyDialog);
        this.slotData = slotData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_slot);
        ivImg = findViewById(R.id.iv_img);
        tvId = findViewById(R.id.tv_id);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(getContext()).load(slotData.getCardImg()).into(ivImg);
        tvId.setText("卡类型id：" + slotData.getLocalId() + "");
    }

}
