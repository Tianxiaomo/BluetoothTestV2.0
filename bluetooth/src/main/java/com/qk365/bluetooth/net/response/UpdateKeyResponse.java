package com.qk365.bluetooth.net.response;

/**
 * Created by YanZi on 2017/6/23.
 * describe：
 * modify:
 * modify date:
 */
public class UpdateKeyResponse {


    /**
     * wireId : 1
     * wiretype : 2
     * wirecmd : sample string 3
     */

    private int wireid;
    private int wiretype;
    private String wirecmd;
    private String initcmd;

    public int getWireid() {
        return wireid;
    }

    public void setWireid(int wireid) {
        this.wireid = wireid;
    }

    public int getWiretype() {
        return wiretype;
    }

    public void setWiretype(int wiretype) {
        this.wiretype = wiretype;
    }

    public String getWirecmd() {
        return wirecmd;
    }

    public void setWirecmd(String wirecmd) {
        this.wirecmd = wirecmd;
    }

    public String getInitcmd() {
        return initcmd;
    }

    public void setInitcmd(String initcmd) {
        this.initcmd = initcmd;
    }
}
