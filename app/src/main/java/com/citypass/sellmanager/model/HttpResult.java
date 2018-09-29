package com.citypass.sellmanager.model;

/**
 * Created by 赵涛 on 2018/8/24.
 * 网络请求返回参数
 */
public class HttpResult<T> {
    private T Data;
    private String Des;
    private int Ret;
    private String RetTime;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public int getRet() {
        return Ret;
    }

    public void setRet(int ret) {
        Ret = ret;
    }

    public String getRetTime() {
        return RetTime;
    }

    public void setRetTime(String retTime) {
        RetTime = retTime;
    }
}
