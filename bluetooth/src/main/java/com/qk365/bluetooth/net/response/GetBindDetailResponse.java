package com.qk365.bluetooth.net.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YanZi on 2017/6/29.
 * describe：
 * modify:
 * modify date:
 */
public class GetBindDetailResponse implements Parcelable {

    /**
     * cucId : 1
     * roomId : 2
     * roomTag : sample string 3
     * lockState : 4
     * bkiMac mac地址
     * address 房间地址
     */

    private int cucid;
    private int roomid;
    private String roomtag;
    private int lockstate; //0未绑定锁1已绑定锁
    private String bkimac;
    private String address;
    private boolean checksuccess;
    private boolean hassearched;
    private String lockname;

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

    public String getRoomtag() {
        return roomtag;
    }

    public void setRoomtag(String roomtag) {
        this.roomtag = roomtag;
    }

    public int getLockstate() {
        return lockstate;
    }

    public void setLockstate(int lockstate) {
        this.lockstate = lockstate;
    }

    public String getBkimac() {
        return bkimac;
    }

    public void setBkimac(String bkimac) {
        this.bkimac = bkimac;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isChecksuccess() {
        return checksuccess;
    }

    public void setChecksuccess(boolean checksuccess) {
        this.checksuccess = checksuccess;
    }

    public boolean isHassearched() {
        return hassearched;
    }

    public void setHassearched(boolean hassearched) {
        this.hassearched = hassearched;
    }

    public String getLockname() {
        return lockname;
    }

    public void setLockname(String lockname) {
        this.lockname = lockname;
    }


    @Override
    public String toString() {
        return "GetBindDetailResponse{" +
                "cucid=" + cucid +
                ", roomid=" + roomid +
                ", roomtag='" + roomtag + '\'' +
                ", lockstate=" + lockstate +
                ", bkimac='" + bkimac + '\'' +
                ", address='" + address + '\'' +
                ", checksuccess=" + checksuccess +
                ", hassearched=" + hassearched +
                ", lockname='" + lockname + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cucid);
        dest.writeInt(this.roomid);
        dest.writeString(this.roomtag);
        dest.writeInt(this.lockstate);
        dest.writeString(this.bkimac);
        dest.writeString(this.address);
        dest.writeByte(this.checksuccess ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hassearched ? (byte) 1 : (byte) 0);
        dest.writeString(this.lockname);
    }

    public GetBindDetailResponse() {
    }

    protected GetBindDetailResponse(Parcel in) {
        this.cucid = in.readInt();
        this.roomid = in.readInt();
        this.roomtag = in.readString();
        this.lockstate = in.readInt();
        this.bkimac = in.readString();
        this.address = in.readString();
        this.checksuccess = in.readByte() != 0;
        this.hassearched = in.readByte() != 0;
        this.lockname = in.readString();
    }

    public static final Creator<GetBindDetailResponse> CREATOR = new Creator<GetBindDetailResponse>() {
        @Override
        public GetBindDetailResponse createFromParcel(Parcel source) {
            return new GetBindDetailResponse(source);
        }

        @Override
        public GetBindDetailResponse[] newArray(int size) {
            return new GetBindDetailResponse[size];
        }
    };
}
