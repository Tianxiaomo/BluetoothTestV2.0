package com.qk365.bluetooth.net.response;

/**
 * Created by YanZi on 2017/7/3.
 * describe：
 * modify:
 * modify date:
 */
public class OpenDoorResponse {


    /**
     * cmdType : 1
     * wireId : 2
     * wireCmd : sample string 3
     */

    private int cmdtype;
    private int wireid;
    private String wirecmd;
    private int wiretype;

    public int getWiretype() {
        return wiretype;
    }

    public void setWiretype(int wiretype) {
        this.wiretype = wiretype;
    }

    public int getCmdtype() {
        return cmdtype;
    }

    public void setCmdtype(int cmdtype) {
        this.cmdtype = cmdtype;
    }

    public int getWireid() {
        return wireid;
    }

    public void setWireid(int wireid) {
        this.wireid = wireid;
    }

    public String getWirecmd() {
        return wirecmd;
    }

    public void setWirecmd(String wirecmd) {
        this.wirecmd = wirecmd;
    }


}
