package com.qk365.bluetooth.net.request;

import java.io.Serializable;

/**
 * Created by YanZi on 2017/6/20.
 * describe：
 * modify:
 * modify date:
 */
public class RegisterRequest implements Serializable{


    /**
     * bki_Name : sample string 1
     * bki_Alias : sample string 2
     * bki_Mac : sample string 3
     * bki_LocalTime : 2017-07-05 19:09:05
     * bki_Tag : sample string 4
     * bkt_SoftVersion : sample string 5
     * bkt_HardVersion : sample string 6
     * bkt_VendorName : sample string 7
     * bki_MgtMac : sample string 8
     * initBind_Key : sample string 9
     * isRereg : 10
     * id : sample string 11
     */

    private String bkiname;
    private String bkialias;
    private String bkimac;
    private String bkilocaltime;
    private String bkitag;
    private String bktsoftversion;
    private String bkthardversion;
    private String bktvendor;
    private String bkimgtmac;
    private String initbindkey;
    private int isrestore;
    private String id;


    public String getBkiname() {
        return bkiname;
    }

    public void setBkiname(String bkiname) {
        this.bkiname = bkiname;
    }

    public String getBkialias() {
        return bkialias;
    }

    public void setBkialias(String bkialias) {
        this.bkialias = bkialias;
    }

    public String getBkimac() {
        return bkimac;
    }

    public void setBkimac(String bkimac) {
        this.bkimac = bkimac;
    }

    public String getBkilocaltime() {
        return bkilocaltime;
    }

    public void setBkilocaltime(String bkilocaltime) {
        this.bkilocaltime = bkilocaltime;
    }

    public String getBkitag() {
        return bkitag;
    }

    public void setBkitag(String bkitag) {
        this.bkitag = bkitag;
    }

    public String getBktsoftversion() {
        return bktsoftversion;
    }

    public void setBktsoftversion(String bktsoftversion) {
        this.bktsoftversion = bktsoftversion;
    }

    public String getBkthardversion() {
        return bkthardversion;
    }

    public void setBkthardversion(String bkthardversion) {
        this.bkthardversion = bkthardversion;
    }


    public String getBktvendor() {
        return bktvendor;
    }

    public void setBktvendor(String bktvendor) {
        this.bktvendor = bktvendor;
    }

    public int getIsrestore() {
        return isrestore;
    }

    public void setIsrestore(int isrestore) {
        this.isrestore = isrestore;
    }

    public String getBkimgtmac() {
        return bkimgtmac;
    }

    public void setBkimgtmac(String bkimgtmac) {
        this.bkimgtmac = bkimgtmac;
    }

    public String getInitbindkey() {
        return initbindkey;
    }

    public void setInitbindkey(String initbindkey) {
        this.initbindkey = initbindkey;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
