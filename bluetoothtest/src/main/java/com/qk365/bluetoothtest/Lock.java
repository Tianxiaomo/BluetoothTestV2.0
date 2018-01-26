package com.qk365.bluetoothtest;

import org.litepal.crud.DataSupport;

/**
 * Created by qkz on 2018/1/26.
 */

public class Lock extends DataSupport {
    private String name;
    private String mac;
    private int rssi;
    private float ele;
    private double realele;
    private String time;
    private int result;
    private String day;

    public double getRealele() {
        return realele;
    }

    public void setRealele(double realele) {
        this.realele = realele;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public float getEle() {
        return ele;
    }

    public void setEle(float ele) {
        this.ele = ele;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
