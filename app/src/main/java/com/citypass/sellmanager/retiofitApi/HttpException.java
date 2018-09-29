package com.citypass.sellmanager.retiofitApi;

/**
 * Created by 赵涛 on 2018-09-29.
 */
public class HttpException extends RuntimeException {

    private final String message;

    public HttpException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
