package com.citypass.sellmanager.view.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.citypass.sellmanager.R;
import com.citypass.sellmanager.model.HttpBean;
import com.citypass.sellmanager.retiofitApi.HttpDataListener;
import com.citypass.sellmanager.retiofitApi.HttpDataSubscriber;
import com.citypass.sellmanager.retiofitApi.RetrofitHelper;

/**
 * Created by 赵涛 on 2018-09-27.
 * 登陆对话框
 */
public class LoginBuilder extends AlertDialog.Builder {

    private final Context context;
    private final SharedPreferences userInfo;
    private final boolean isNotLogged;
    private final EditText edName;
    private final EditText edPass;
    private final AlertDialog alertDialog;
    private final ICallBack icallBack;

    public interface ICallBack {
        void editButton(String text);
    }


    public LoginBuilder(@NonNull final Context context, final ICallBack icallBack) {
        super(context);
        this.context = context;
        this.icallBack = icallBack;
        setCancelable(false);

        View editView = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        edName = editView.findViewById(R.id.ed_name);
        edPass = editView.findViewById(R.id.ed_pass);

        setView(editView);

        userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        isNotLogged = TextUtils.isEmpty(userInfo.getString("name", ""));

        setTitle(R.string.user_input);
        setPositiveButton(isNotLogged ? R.string.login : R.string.login_switch, null);
        setNegativeButton(R.string.cancel, null);
        if (!isNotLogged) {
            setNeutralButton(R.string.exit, null);
            setTitle("当前已登陆用户：" + userInfo.getString("name", ""));
        }

        alertDialog = create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edName.getText()) || TextUtils.isEmpty(edPass.getText())) {
                    Toast.makeText(context, R.string.login_fail, Toast.LENGTH_SHORT).show();
                } else {
                    verifyUser();
                }
            }
        });

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotLogged) {
                    Toast.makeText(context, R.string.warn1, Toast.LENGTH_SHORT).show();
                } else {
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo.edit().clear().apply();
                icallBack.editButton(null);
                alertDialog.dismiss();
            }
        });
    }

    private void verifyUser() {
        HttpDataListener<HttpBean> httpDataListener = new HttpDataListener<HttpBean>() {
            @Override
            public void onNext(HttpBean httpBean) {
                if (httpBean.getRet() == 0) {
                    SharedPreferences.Editor edit = userInfo.edit();
                    edit.putString("name", edName.getText().toString());
                    Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show();
                    edit.commit();
                    icallBack.editButton(edName.getText().toString());
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, R.string.login_fail, Toast.LENGTH_SHORT).show();
                }
            }
        };
        RetrofitHelper.getInstance().verifyUser(new HttpDataSubscriber<HttpBean>(httpDataListener, context), edName.getText().toString(), edPass.getText().toString());
    }
}
