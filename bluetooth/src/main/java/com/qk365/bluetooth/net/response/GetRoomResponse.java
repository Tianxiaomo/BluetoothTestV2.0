package com.qk365.bluetooth.net.response;

/**
 * Created by YanZi on 2017/6/29.
 * describe：
 * modify:
 * modify date:
 */
public class GetRoomResponse {


    /**
     * roM_Id : 1
     * roomTag : sample string 2
     */

    private int roomid;
    private String roomtag;

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public String getRoomtag() {
        return roomtag;
    }

    public void setRoomtag(String roomtag) {
        this.roomtag = roomtag;
    }
}
