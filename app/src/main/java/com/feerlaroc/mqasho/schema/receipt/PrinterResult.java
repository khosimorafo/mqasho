package com.feerlaroc.mqasho.schema.receipt;

/**
 * Created by root on 2017/02/07.
 */

public class PrinterResult<T> {

    private int code = 0;
    private String message = "OK";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
