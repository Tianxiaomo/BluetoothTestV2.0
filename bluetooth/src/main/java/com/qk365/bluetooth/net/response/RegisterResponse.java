package com.qk365.bluetooth.net.response;

import java.util.Arrays;

/**
 * Created by YanZi on 2017/6/21.
 * describe：
 * modify:
 * modify date:
 */
public class RegisterResponse {


    /**
     * bki_Mac : sample string 1
     * pass_Key : sample string 2
     * state : 1
     * wireId : 1
     * wireType : 1
     */

    private String bkimac;
    private String passkey;
    private String initcmd;
    private int state;  //0 未注册  1 注册成功未绑定房间 2注册失败   3已注册已绑定房间地址
    private int wireid;
    private int wiretype;

    private int cucid;
    private int roomid;
    private String cucaddress;

    private byte registerbyte[];

    public String getBkimac() {
        return bkimac;
    }

    public void setBkimac(String bkimac) {
        this.bkimac = bkimac;
    }

    public String getPasskey() {
        return passkey;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }

    public String getInitcmd() {
        return initcmd;
    }

    public void setInitcmd(String initcmd) {
        this.initcmd = initcmd;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

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

    public int getCucid() {
        return cucid;
    }

    public void setCucid(int cucid) {
        this.cucid = cucid;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public String getCucaddress() {
        return cucaddress;
    }

    public void setCucaddress(String cucaddress) {
        this.cucaddress = cucaddress;
    }

    public byte[] getRegisterbyte() {
        return registerbyte;
    }

    public void setRegisterbyte(byte[] registerbyte) {
        this.registerbyte = registerbyte;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "bkimac='" + bkimac + '\'' +
                ", passkey='" + passkey + '\'' +
                ", initcmd='" + initcmd + '\'' +
                ", state=" + state +
                ", wireid=" + wireid +
                ", wiretype=" + wiretype +
                ", cucid=" + cucid +
                ", roomid=" + roomid +
                ", cucaddress='" + cucaddress + '\'' +
                ", registerbyte=" + Arrays.toString(registerbyte) +
                '}';
    }
}
