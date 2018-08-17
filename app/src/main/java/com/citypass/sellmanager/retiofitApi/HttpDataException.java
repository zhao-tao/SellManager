package com.citypass.sellmanager.retiofitApi;

/**
 * Created by 赵涛 on 2018/8/17.
 * Http请求异常
 */
class HttpDataException extends RuntimeException {

    private final int resultCode;

    public HttpDataException(int resultCode, String detailMessage) {
        super(detailMessage);
        this.resultCode = resultCode;
    }


}
