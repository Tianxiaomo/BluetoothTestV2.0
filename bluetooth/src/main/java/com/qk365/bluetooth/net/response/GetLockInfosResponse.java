package com.qk365.bluetooth.net.response;

/**
 * Created by YanZi on 2017/7/4.
 * describe：
 * modify:
 * modify date:
 */
public class GetLockInfosResponse {




    private String bkthardversion;  //硬件版本号
    private String bktsoftversion; //软件版本号
    private String bktvendor;   //供应名称

    public String getBkthardversion() {
        return bkthardversion;
    }

    public void setBkthardversion(String bkthardversion) {
        this.bkthardversion = bkthardversion;
    }

    public String getBktsoftversion() {
        return bktsoftversion;
    }

    public void setBktsoftversion(String bktsoftversion) {
        this.bktsoftversion = bktsoftversion;
    }

    public String getBktvendor() {
        return bktvendor;
    }

    public void setBktvendor(String bktvendor) {
        this.bktvendor = bktvendor;
    }
}
