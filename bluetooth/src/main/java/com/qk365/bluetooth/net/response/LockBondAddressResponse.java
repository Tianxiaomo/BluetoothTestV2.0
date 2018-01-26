package com.qk365.bluetooth.net.response;

/**
 * Created by YanZi on 2017/6/29.
 * describe：
 * modify:
 * modify date:
 */
public class LockBondAddressResponse {


    /**
     * bki_Mac : sample string 1
     * msg : sample string 2
     */

    private String bkimac;
    private String msg;
    private int status;

    public String getBkimac() {
        return bkimac;
    }

    public void setBkimac(String bkimac) {
        this.bkimac = bkimac;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
