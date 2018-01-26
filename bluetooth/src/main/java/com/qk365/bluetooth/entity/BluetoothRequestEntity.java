package com.qk365.bluetooth.entity;

import android.util.Log;

import com.qk365.bluetooth.util.BluetoothAlgorithm;
import com.qk365.bluetooth.util.DigitalTrans;

import java.util.Arrays;

/**
 * Created by YanZi on 2017/6/1.
 * describe：
 * modify:
 * modify date:
 */
public class BluetoothRequestEntity {


    private String orderNum;
    private byte orderCode;
    private String macAddress;
    private String currentTime;
    private String checkCode;
    //================================================
    private byte[] orderNumByte;
    private byte[] orderCodeByte;
    private byte[] macAddressByte;
    private byte[] currentTimeByte;
    private byte[] checkCodeByte;


    private byte[] sendValue;

    private int type;
    private int requestCharacteristic;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRequestCharacteristic() {
        return requestCharacteristic;
    }

    public void setRequestCharacteristic(int requestCharacteristic) {
        this.requestCharacteristic = requestCharacteristic;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
        byte [] orderNumTemp= DigitalTrans.hexStrToBytes(orderNum);
        int len=orderNumTemp.length;
        orderNumByte =new byte[len];
        for (int i = 0; i < len; i++) {
            orderNumByte[len-i-1]=orderNumTemp[i];
        }
    }

    public byte getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(byte orderCode) {
        this.orderCode = orderCode;
        if (orderCodeByte == null) {
            orderCodeByte = new byte[1];
        }
        orderCodeByte [0]=orderCode;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
        macAddressByte = DigitalTrans.hexStrToBytes(macAddress);
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
        byte [] temp=  DigitalTrans.hexStrToBytes(currentTime);

        if(temp.length==3){
            currentTimeByte=new byte[temp.length+1] ;
            System.arraycopy(temp, 0, currentTimeByte, 1, temp.length);
            currentTimeByte[0]=(byte)0;
        }
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }


    public byte[] getOrderNumByte() {
        return orderNumByte;
    }

    public byte[] getOrderCodeByte() {
        return orderCodeByte;
    }

    public byte[] getMacAddressByte() {
        return macAddressByte;
    }

    public byte[] getCurrentTimeByte() {
        return currentTimeByte;
    }

    public byte[] getCheckCodeByte() {
        return checkCodeByte;
    }

    public byte[] getSendValue() {
        if (orderNumByte==null) {
            Log.e("yan","请先设置 BluetoothRequestEntity 的 orderNum ");
            return null;
        }
        if (orderCodeByte==null) {
            Log.e("yan","请先设置 BluetoothRequestEntity 的 orderCode ");
            return null;
        }
        if (macAddressByte==null) {
            Log.e("yan","请先设置 BluetoothRequestEntity 的 macAddress ");
            return null;
        }
        if (currentTimeByte==null) {
            Log.e("yan","请先设置 BluetoothRequestEntity 的 currentTime ");
            return null;
        }
        int orderNumByteLength=orderNumByte.length;
        int orderCodeByteLength=orderCodeByte.length;
        int macAddressByteLength=macAddressByte.length;
        int currentTimeByteLength=currentTimeByte.length;

        byte[] total = new byte[orderNumByteLength + orderCodeByteLength + macAddressByteLength + currentTimeByteLength];


        System.arraycopy(orderNumByte, 0, total, 0, orderNumByteLength);
        System.arraycopy(orderCodeByte, 0, total, orderNumByteLength, orderCodeByteLength);
        System.arraycopy(macAddressByte, 0, total, orderNumByteLength + orderCodeByteLength, macAddressByteLength);
        System.arraycopy(currentTimeByte, 0, total,  orderNumByteLength + orderCodeByteLength + macAddressByteLength, currentTimeByteLength);

        if(checkCodeByte==null){
            checkCodeByte =new byte[2];
        }

        BluetoothAlgorithm.get_crc16(total, total.length, checkCodeByte);
        if(sendValue==null){
            sendValue = new byte[total.length + checkCodeByte.length];
        }

        System.arraycopy(total, 0, sendValue, 0, total.length);
        System.arraycopy(checkCodeByte, 0, sendValue, total.length, checkCodeByte.length);

        return sendValue;
    }

    public void setOrderNumByte(byte[] orderNumByte) {
        this.orderNumByte = orderNumByte;
    }

    public void setOrderCodeByte(byte[] orderCodeByte) {
        this.orderCodeByte = orderCodeByte;
    }

    public void setMacAddressByte(byte[] macAddressByte) {
        this.macAddressByte = macAddressByte;
    }

    public void setCurrentTimeByte(byte[] currentTimeByte) {
        this.currentTimeByte = currentTimeByte;
    }

    public void setCheckCodeByte(byte[] checkCodeByte) {
        this.checkCodeByte = checkCodeByte;
    }

    public void setSendValue(byte[] sendValue) {
        this.sendValue = sendValue;
    }

    @Override
    public String toString() {
        return "BluetoothRequestEntity{" +
                "orderNum='" + orderNum + '\'' +
                ", orderCode=" + orderCode +
                ", macAddress='" + macAddress + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", checkCode='" + checkCode + '\'' +
                ", orderNumByte=" + Arrays.toString(orderNumByte) +
                ", orderCodeByte=" + Arrays.toString(orderCodeByte) +
                ", macAddressByte=" + Arrays.toString(macAddressByte) +
                ", currentTimeByte=" + Arrays.toString(currentTimeByte) +
                ", checkCodeByte=" + Arrays.toString(checkCodeByte) +
                ", sendValue=" + Arrays.toString(sendValue) +
                ", type=" + type +
                ", requestCharacteristic=" + requestCharacteristic +
                '}';
    }
}
