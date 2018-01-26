package com.qk365.bluetooth.net.response;

/**
 * Created by Administrator on 2017/10/31.
 */

public class LockAddress {

    private String lockmac;
    private String cucaddress;
    private String roomtag;
    private String elestr;

    public String getLockmac() {
        return lockmac;
    }

    public void setLockmac(String lockmac) {
        this.lockmac = lockmac;
    }

    public String getCucaddress() {
        return cucaddress;
    }

    public void setCucaddress(String cucaddress) {
        this.cucaddress = cucaddress;
    }

    public String getRoomtag() {
        return roomtag;
    }

    public void setRoomtag(String roomtag) {
        this.roomtag = roomtag;
    }

    public String getElestr() {
        return elestr;
    }

    public void setElestr(String elestr) {
        this.elestr = elestr;
    }
}
