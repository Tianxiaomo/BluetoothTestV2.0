package com.qk365.bluetoothtest.litepal;

import org.litepal.crud.DataSupport;

/**
 * Created by qkz on 2018/1/26.
 */

public class LockNum extends DataSupport{
    private int total;
    private int success;
    private int fail;
    private String day;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
