package com.qk365.bluetooth.net.response;

/**
 * Created by YanZi on 2017/7/3.
 * describe：
 * modify:
 * modify date:
 */
public class GetLockCurrentBindKeysResponse {


    /**
     * bki_Mac : sample string 1
     * current_Key : sample string 2
     */

    private String bkimac;
    private String currentkey;

    public String getBkimac() {
        return bkimac;
    }

    public void setBkimac(String bkimac) {
        this.bkimac = bkimac;
    }

    public String getCurrentkey() {
        return currentkey;
    }

    public void setCurrentkey(String currentkey) {
        this.currentkey = currentkey;
    }
}
