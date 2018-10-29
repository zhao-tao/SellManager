package com.citypass.sellmanager.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.citypass.sellmanager.R;
import com.citypass.sellmanager.model.HttpBean;
import com.citypass.sellmanager.model.SlotBean;
import com.citypass.sellmanager.retiofitApi.HttpDataListener;
import com.citypass.sellmanager.retiofitApi.HttpDataSubscriber;
import com.citypass.sellmanager.retiofitApi.RetrofitHelper;

/**
 * Created by 赵涛 on 2018-09-27.
 */
public class SlotDialog extends Dialog {


    private final SlotBean slotData;
    private final ReFreshListener reFreshListener;
    private final Context context;
    ImageView ivImg;
    TextView tvId;
    //    private EditText edCard;
    private EditText edNum;

    public interface ReFreshListener {
        void reFreshList();
    }


    public SlotDialog(@NonNull Context context, SlotBean slotData, ReFreshListener reFreshListener) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.slotData = slotData;
        this.reFreshListener = reFreshListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_slot);
        ivImg = findViewById(R.id.iv_img);
        tvId = findViewById(R.id.tv_id);
//        edCard = findViewById(R.id.ed_card);
        edNum = findViewById(R.id.ed_num);
        Button btnSure = findViewById(R.id.btn_sure);
        Button btnCancel = findViewById(R.id.btn_cancel);
        Button btnAll = findViewById(R.id.btn_all);

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edNum.getText())) {
                    Toast.makeText(getContext(), R.string.input_null, Toast.LENGTH_SHORT).show();
                } else {
                    confirmSlot(false);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmSlot(true);
            }
        });
    }

    /**
     * 提交货道信息
     */
    private void confirmSlot(boolean isFull) {
        HttpDataListener<HttpBean> dataListener = new HttpDataListener<HttpBean>() {
            @Override
            public void onNext(HttpBean data) {
                if (data.getRet() < 0) {
                    Toast.makeText(getContext(), data.getDes(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    reFreshListener.reFreshList();
                    dismiss();
                }
            }

            @Override
            public void onError(Context context, String msg) {
                super.onError(context, msg);
            }
        };

        int i;
        if (isFull) {
            i = 20;
        } else {
            i = Integer.parseInt(edNum.getText().toString());
        }

        RetrofitHelper.getInstance().confirmSlot(new HttpDataSubscriber(dataListener, getContext()),
                slotData.getSlotId() + "", "", i);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(getContext()).load(slotData.getCardImg()).into(ivImg);
        tvId.setText("货道:" + slotData.getLocalId() + "   卡类型:" + slotData.getCardKindId() + "  余量:" + slotData.getSlotBalance());
    }

}
