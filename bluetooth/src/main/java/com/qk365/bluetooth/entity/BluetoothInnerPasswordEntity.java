package com.qk365.bluetooth.entity;


/**
 * Created by YanZi on 2017/6/1.
 * describe：
 * modify:
 * modify date:
 */
public class BluetoothInnerPasswordEntity {


    private byte endFlag = (byte) 0xfe;
    private byte[] orderNumByte;
    private byte[] innerPasswordByte;


    private byte[] sendValue;

    public BluetoothInnerPasswordEntity() {
    }

    public BluetoothInnerPasswordEntity(int orderNum, byte[] innerPasswordByte) {
        orderNumByte = new byte[1];
        orderNumByte[0] = (byte) orderNum;
        this.innerPasswordByte = innerPasswordByte;
    }

    public void setOrderNumByte(int orderNum) {
        orderNumByte = new byte[1];
        orderNumByte[0] = (byte) orderNum;
    }

    public void setInnerPasswordByte(byte[] innerPasswordByte) {
        this.innerPasswordByte = innerPasswordByte;
    }

    public byte[] getSendValue() {
        int orderNumByteLength = orderNumByte.length;
        int innerPasswordByteLength = innerPasswordByte.length;
        sendValue = new byte[orderNumByteLength + innerPasswordByteLength + 1];
        System.arraycopy(orderNumByte, 0, sendValue, 0, orderNumByteLength);
        System.arraycopy(innerPasswordByte, 0, sendValue, orderNumByteLength, innerPasswordByteLength);
        sendValue[orderNumByteLength + innerPasswordByteLength] = endFlag;

        return sendValue;
    }

    public void setEndFlag(byte endFlag) {
        this.endFlag = endFlag;
    }
}
