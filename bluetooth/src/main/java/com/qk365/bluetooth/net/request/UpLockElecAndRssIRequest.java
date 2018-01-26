package com.qk365.bluetooth.net.request;

/**
 * Created by YanZi on 2017/7/4.
 * describe：
 * modify:
 * modify date:
 */
public class UpLockElecAndRssIRequest {


    /**
     * bki_Mac : sample string 1
     * bke_Elec : sample string 2
     * bke_Rssi : sample string 3
     */

    private String bkimac;
    private String bkeelec;
    private String bkerssi;

    public String getBkimac() {
        return bkimac;
    }

    public void setBkimac(String bkimac) {
        this.bkimac = bkimac;
    }

    public String getBkeelec() {
        return bkeelec;
    }

    public void setBkeelec(String bkeelec) {
        this.bkeelec = bkeelec;
    }

    public String getBkerssi() {
        return bkerssi;
    }

    public void setBkerssi(String bkerssi) {
        this.bkerssi = bkerssi;
    }
}
