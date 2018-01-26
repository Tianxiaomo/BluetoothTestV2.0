package com.qk365.bluetooth.util;

import android.util.Log;

import com.qk365.bluetooth.entity.BluetoothRequestEntity;
import com.qk365.bluetooth.entity.BluetoothResponseEntity;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by YanZi on 2017/6/12.
 * describe：
 * modify:
 * modify date:
 */
public class TransDataAlgorithm {

    /**
     *
     * Response
     * 根据返回的字符串转换为实体类
     *
     * @param responseByte
     * @return
     */
    public static BluetoothResponseEntity getBluetoothResponseEntity(byte[] responseByte) {
        BluetoothResponseEntity responseEntity = new BluetoothResponseEntity();
        byte[] orderNumByte = new byte[4];
        byte[] orderCodeByte = new byte[1];
        byte[] flagByte = new byte[1];
        byte[] macAddressByte = new byte[6];
        byte[] checkCodeByte = new byte[2];

        System.arraycopy(responseByte, 0, orderNumByte, 0, orderNumByte.length);
        System.arraycopy(responseByte, 4, orderCodeByte, 0, orderCodeByte.length);
        System.arraycopy(responseByte, 5, flagByte, 0, flagByte.length);
        System.arraycopy(responseByte, 6, macAddressByte, 0, macAddressByte.length);
        System.arraycopy(responseByte, 12, checkCodeByte, 0, checkCodeByte.length);

        responseEntity.setOrderNumByte(orderNumByte);
        responseEntity.setOrderCodeByte(orderCodeByte);
        responseEntity.setFlagByte(flagByte);
        responseEntity.setMacAddressByte(macAddressByte);
        responseEntity.setCheckCodeByte(checkCodeByte);

        return responseEntity;
    }

    /**
     *
     * 开门等操作  Request
     * @param requestByte
     * @return
     */
    public static BluetoothRequestEntity getBluetoothRequestEntity(byte[] requestByte, int type, int characterType) {
        BluetoothRequestEntity requestEntity = new BluetoothRequestEntity();
        byte[] orderNumByte = new byte[4];
        byte[] orderCodeByte = new byte[1];

        byte[] macAddressByte = new byte[6];
        byte[] currentTimeByte = new byte[4];
        byte[] checkCodeByte = new byte[2];

        System.arraycopy(requestByte, 0, orderNumByte, 0, orderNumByte.length);
        System.arraycopy(requestByte, orderNumByte.length, orderCodeByte, 0, orderCodeByte.length);
        System.arraycopy(requestByte, orderNumByte.length + orderCodeByte.length, macAddressByte, 0, macAddressByte.length);
        System.arraycopy(requestByte, orderNumByte.length + orderCodeByte.length + macAddressByte.length, currentTimeByte, 0, currentTimeByte.length);
        System.arraycopy(requestByte, orderNumByte.length + orderCodeByte.length + macAddressByte.length + currentTimeByte.length, checkCodeByte, 0, checkCodeByte.length);
        requestEntity.setSendValue(requestByte);
        requestEntity.setOrderNumByte(orderNumByte);
        requestEntity.setOrderCodeByte(orderCodeByte);
        requestEntity.setMacAddressByte(macAddressByte);
        requestEntity.setCurrentTimeByte(currentTimeByte);
        requestEntity.setCheckCodeByte(checkCodeByte);
        requestEntity.setType(type);
        requestEntity.setRequestCharacteristic(characterType);
        return requestEntity;
    }


    /**
     * 不绑定开门  Request    6
     * @param requestByte
     * @return
     */
    public static BluetoothRequestEntity getBluetoothRequestEntitySix(byte[] requestByte, int type, int characterType) {
        BluetoothRequestEntity requestEntity = new BluetoothRequestEntity();
        byte[] orderNumByte = new byte[4];
        byte[] orderCodeByte = new byte[1];

        byte[] macAddressByte = new byte[8];
        byte[] currentTimeByte = new byte[4];
        byte[] checkCodeByte = new byte[2];

        System.arraycopy(requestByte, 0, orderNumByte, 0, orderNumByte.length);
        System.arraycopy(requestByte, orderNumByte.length, orderCodeByte, 0, orderCodeByte.length);
        System.arraycopy(requestByte, orderNumByte.length + orderCodeByte.length, macAddressByte, 0, macAddressByte.length);
        System.arraycopy(requestByte, orderNumByte.length + orderCodeByte.length + macAddressByte.length, currentTimeByte, 0, currentTimeByte.length);
        System.arraycopy(requestByte, orderNumByte.length + orderCodeByte.length + macAddressByte.length + currentTimeByte.length, checkCodeByte, 0, checkCodeByte.length);
        requestEntity.setSendValue(requestByte);
        requestEntity.setOrderNumByte(orderNumByte);
        requestEntity.setOrderCodeByte(orderCodeByte);
        requestEntity.setMacAddressByte(macAddressByte);
        requestEntity.setCurrentTimeByte(currentTimeByte);
        requestEntity.setCheckCodeByte(checkCodeByte);
        requestEntity.setType(type);
        requestEntity.setRequestCharacteristic(characterType);
        return requestEntity;
    }

    /**
     *
     * Response
     * 不绑定开门Response
     * @param responseByte
     * @return
     */
    public static BluetoothResponseEntity getBluetoothResponseEntitySix(byte[] responseByte) {
        BluetoothResponseEntity responseEntity = new BluetoothResponseEntity();
        byte[] orderNumByte = new byte[4];
        byte[] orderCodeByte = new byte[1];
        byte[] flagByte = new byte[1];
        byte[] macAddressByte = new byte[8];
        byte[] checkCodeByte = new byte[2];

        System.arraycopy(responseByte, 0, orderNumByte, 0, orderNumByte.length);
        System.arraycopy(responseByte, 4, orderCodeByte, 0, orderCodeByte.length);
        System.arraycopy(responseByte, 5, flagByte, 0, flagByte.length);
        System.arraycopy(responseByte, 6, macAddressByte, 0, macAddressByte.length);
        System.arraycopy(responseByte, 14, checkCodeByte, 0, checkCodeByte.length);

        responseEntity.setOrderNumByte(orderNumByte);
        responseEntity.setOrderCodeByte(orderCodeByte);
        responseEntity.setFlagByte(flagByte);
        responseEntity.setMacAddressByte(macAddressByte);
        responseEntity.setCheckCodeByte(checkCodeByte);

        return responseEntity;
    }



    /**
     * 通过自定发送值值获取requestData
     * @param responseEntity
     * @param mRequestMap
     * @return
     */
    public static BluetoothRequestEntity checkResponseInMap(BluetoothResponseEntity responseEntity, Map<String, BluetoothRequestEntity> mRequestMap) {
        BluetoothRequestEntity bluetoothRequestEntity = mRequestMap.get(DigitalTrans.bytesToHexString(responseEntity.getOrderCodeByte()));
        if (bluetoothRequestEntity == null) {
            Log.e("yan", "未发现发送实体类，可能是主动上传的数据");
            return null;
        } else {
            return bluetoothRequestEntity;
        }
    }

    public static boolean checkResponseAndRequestMatch(BluetoothResponseEntity responseEntity, BluetoothRequestEntity bluetoothRequestEntity) {
        if (responseEntity == null) {
            Log.e("yan", "请传入返回类");
            return false;
        }
        if (bluetoothRequestEntity == null) {
            Log.e("yan", "请传入发送类");
            return false;
        }
        byte[] orderCodeByteReq = bluetoothRequestEntity.getOrderCodeByte();
        byte[] orderCodeByteRes = responseEntity.getOrderCodeByte();
        for (int i = 0; i < orderCodeByteReq.length; i++) {
            if (orderCodeByteReq[i] != orderCodeByteRes[i]) {
                Log.e("yan", "流水号不一致");
                return false;
            }
        }
        byte[] macAddressByteReq = bluetoothRequestEntity.getMacAddressByte();
        bluetoothRequestEntity.getMacAddressByte();
        byte[] macAddressByteRes = responseEntity.getMacAddressByte();
        for (int i = 0; i < macAddressByteReq.length; i++) {
            if (macAddressByteReq[i] != macAddressByteRes[i]) {
                Log.e("yan", "mac 地址不一致");
                return false;
            }
        }
        return checkCheckCode(responseEntity);
    }

    /**
     * 检查校验码
     *
     * @param responseEntity 返回的代码
     * @return
     */
    public static boolean checkCheckCode(BluetoothResponseEntity responseEntity) {
        byte[] localCheckCodeByte = responseEntity.getLocalCheckCodeByte();
        byte[] checkCodeByte = responseEntity.getCheckCodeByte();
        for (int i = 0; i < localCheckCodeByte.length; i++) {
            if (localCheckCodeByte[i] != checkCodeByte[i]) {
                Log.e("yan", "校验码不一致");
                return false;
            }
        }
        return true;
    }

    public static BluetoothRequestEntity getRequestEntity(byte orderCode, int characteristic, String macAddressStr, int type) throws ParseException {
        BluetoothRequestEntity requestEntity = new BluetoothRequestEntity();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse("2010-01-01 00:00:00");
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        long timestamp = cal.getTimeInMillis();
        int timeDec = (int) (System.currentTimeMillis() / 1000 - timestamp / 1000);

        requestEntity.setOrderNum(Integer.toHexString(timeDec));
        requestEntity.setCurrentTime(Integer.toHexString(timeDec / 60));
        requestEntity.setMacAddress(macAddressStr);
        requestEntity.setOrderCode(orderCode);
        requestEntity.setRequestCharacteristic(characteristic);
        requestEntity.setType(type);
        return requestEntity;
    }

    public static String getLocalMacAddress() {

        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "02:00:00:00:00:02";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return "02:00:00:00:00:02";
        }
        return macAddress;
    }
}
