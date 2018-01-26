package com.qk365.bluetooth.net.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YanZi on 2017/6/29.
 * describe：
 * modify:
 * modify date:
 */
public class GetGucsResponse implements Parcelable{


    /**
     * cuC_Id : 1
     * cuC_Address : sample string 2
     */

    private int cucid;
    private String cucaddress;

    public int getCucid() {
        return cucid;
    }

    public void setCucid(int cucid) {
        this.cucid = cucid;
    }

    public String getCucaddress() {
        return cucaddress;
    }

    public void setCucaddress(String cucaddress) {
        this.cucaddress = cucaddress;
    }

    @Override
    public String toString() {
        return "GetGucsResponse{" +
                "cucid=" + cucid +
                ", cucaddress='" + cucaddress + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getCucid());
        dest.writeString(this.getCucaddress());
    }

    public GetGucsResponse() {
    }

    protected GetGucsResponse(Parcel in) {
        this.cucid = in.readInt();
        this.cucaddress = in.readString();
    }

    public static final Creator<GetGucsResponse> CREATOR = new Creator<GetGucsResponse>() {
        @Override
        public GetGucsResponse createFromParcel(Parcel source) {
            return new GetGucsResponse(source);
        }

        @Override
        public GetGucsResponse[] newArray(int size) {
            return new GetGucsResponse[size];
        }
    };
}
