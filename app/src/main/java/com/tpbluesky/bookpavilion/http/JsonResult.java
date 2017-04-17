package com.tpbluesky.bookpavilion.http;

/**
 * Created by tpbluesky on 2017/3/1.
 */

public class JsonResult<T> {

    private int code;
    private String msg;
    private T info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}
