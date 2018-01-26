package com.qk365.bluetooth.entity;

import android.util.Log;

import com.qk365.bluetooth.util.BluetoothAlgorithm;

import java.util.Arrays;

/**
 * Created by YanZi on 2017/6/1.
 * describe：
 * modify:
 * modify date:
 */
public class BluetoothResponseEntity {


    //========================与蓝牙传送需要========================
    private byte[] orderNumByte;
    private byte[] orderCodeByte;
    private byte[] flagByte;
    private byte[] macAddressByte;
    private byte[] checkCodeByte;
    private byte[] localCheckCodeByte;

    public byte[] getOrderNumByte() {
        return orderNumByte;
    }

    public void setOrderNumByte(byte[] orderNumByte) {
        this.orderNumByte = orderNumByte;
    }

    public byte[] getOrderCodeByte() {
        return orderCodeByte;
    }

    public void setOrderCodeByte(byte[] orderCodeByte) {
        this.orderCodeByte = orderCodeByte;
    }

    public byte[] getFlagByte() {
        return flagByte;
    }

    public void setFlagByte(byte[] flagByte) {
        this.flagByte = flagByte;
    }

    public byte[] getMacAddressByte() {
        return macAddressByte;
    }

    public void setMacAddressByte(byte[] macAddressByte) {
        this.macAddressByte = macAddressByte;
    }

    public byte[] getCheckCodeByte() {
        return checkCodeByte;
    }

    public void setCheckCodeByte(byte[] checkCodeByte) {
        this.checkCodeByte = checkCodeByte;
    }

    public byte[] getLocalCheckCodeByte() {
        if (orderNumByte == null) {
            Log.e("yan", "请先设置 BluetoothResponseEntity 的 orderNumByte ");
            return null;
        }
        if (orderCodeByte == null) {
            Log.e("yan", "请先设置 BluetoothResponseEntity 的 orderCodeByte ");
            return null;
        }
        if (flagByte == null) {
            Log.e("yan", "请先设置 BluetoothResponseEntity 的 flagByte ");
            return null;
        }
        if (macAddressByte == null) {
            Log.e("yan", "请先设置 BluetoothResponseEntity 的 macAddressByte ");
            return null;
        }
        int orderNumByteLength = orderNumByte.length;
        int orderCodeByteLength = orderCodeByte.length;
        int flagByteLength = flagByte.length;
        int macAddressByteLength = macAddressByte.length;
        byte[] total = new byte[orderNumByteLength + orderCodeByteLength + flagByteLength + macAddressByteLength];
        System.arraycopy(orderNumByte, 0, total, 0, orderNumByteLength);
        System.arraycopy(orderCodeByte, 0, total, orderNumByteLength, orderCodeByteLength);
        System.arraycopy(flagByte, 0, total, orderNumByteLength + orderCodeByteLength, flagByteLength);
        System.arraycopy(macAddressByte, 0, total, orderNumByteLength + orderCodeByteLength + flagByteLength, macAddressByteLength);
        if (localCheckCodeByte == null) {
            localCheckCodeByte = new byte[2];
        }
        BluetoothAlgorithm.get_crc16(total, total.length, localCheckCodeByte);
        return localCheckCodeByte;
    }

    @Override
    public String toString() {
        return "BluetoothResponseEntity{" +
                "orderNumByte=" + Arrays.toString(orderNumByte) +
                ", orderCodeByte=" + Arrays.toString(orderCodeByte) +
                ", flagByte=" + Arrays.toString(flagByte) +
                ", macAddressByte=" + Arrays.toString(macAddressByte) +
                ", checkCodeByte=" + Arrays.toString(checkCodeByte) +
                ", locakCheckCodeByte=" + Arrays.toString(localCheckCodeByte) +
                '}';
    }
}
