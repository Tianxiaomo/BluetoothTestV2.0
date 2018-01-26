package com.qk365.bluetooth.le.data;

import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.qk365.bluetooth.entity.BluetoothResponseEntity;
import com.qk365.bluetooth.le.ApiBleCode;
import com.qk365.bluetooth.le.ClientManager;
import com.qk365.bluetooth.le.call.ApiCoreNotifyBack;
import com.qk365.bluetooth.util.DigitalTrans;
import com.qk365.bluetooth.util.Lg;
import com.qk365.bluetooth.util.TransDataAlgorithm;

import java.util.UUID;

/**
 * Created by Administrator on 2017/11/7.
 */

public class ResponseNotify {


    public  String macAddress;
    private ApiCoreNotifyBack apiCoreNotifyBack;
    private boolean isOneTime = false;

    public  void notifyData(String macAddress,ApiCoreNotifyBack apiCoreNotifyBack){
        this.apiCoreNotifyBack = apiCoreNotifyBack;
        this.macAddress = macAddress;

        if(macAddress == null){
            return;
        }
        //监听4 电量
        ClientManager.getClient().notify(macAddress, ApiBleCode.LOCK_SERVICE_UUID, ApiBleCode.LOCK_CHARACTERISTIC_UUID4, bleNotifyResponse4);
        //监听3校验
        ClientManager.getClient().notify(macAddress, ApiBleCode.LOCK_SERVICE_UUID, ApiBleCode.LOCK_CHARACTERISTIC_UUID3, bleNotifyResponse3);

        ClientManager.getClient().notify(macAddress, ApiBleCode.LOCK_SERVICE_UUID, ApiBleCode.LOCK_CHARACTERISTIC_UUID6, bleNotifyResponse6);
    }

    public void setOnetTimeReq(){
        isOneTime = true;
    }


    public  void closeNotify(){
        ClientManager.getClient().unnotify(macAddress, ApiBleCode.LOCK_SERVICE_UUID, ApiBleCode.LOCK_CHARACTERISTIC_UUID3, new BleUnnotifyResponse() {
            @Override
            public void onResponse(int code) {

            }
        });
    }
    public  void closeNotifySix(){
        ClientManager.getClient().unnotify(macAddress, ApiBleCode.LOCK_SERVICE_UUID, ApiBleCode.LOCK_CHARACTERISTIC_UUID6, new BleUnnotifyResponse() {
            @Override
            public void onResponse(int code) {

            }
        });
    }


    private  BleNotifyResponse bleNotifyResponse3 = new BleNotifyResponse() {
        @Override
        public void onNotify(UUID service, UUID character, byte[] value) {
            if(isOneTime){
                Lg.e(" 3返回结果 ：" + "长度 " + value.length + " 具体值：");
                for (int i = 0; i < value.length; i++) {
                    Lg.e(DigitalTrans.bytesToHexString(value[i]) + " ");
                }
                System.out.println("");
                apiCoreNotifyBack.notifyBack(3, value,getParseData(value));
                isOneTime = false;
            }
        }
        @Override
        public void onResponse(int code) {
            if (code == Constants.REQUEST_SUCCESS) {

            }
        }
    };

    private  BleNotifyResponse bleNotifyResponse6 = new BleNotifyResponse() {
        @Override
        public void onNotify(UUID service, UUID character, byte[] value) {
            if(isOneTime){
                Lg.e(" 6返回结果 ：" + "长度 " + value.length + " 具体值：");
                for (int i = 0; i < value.length; i++) {
                    Lg.e(DigitalTrans.bytesToHexString(value[i]) + " ");
                }
                System.out.println("");
                apiCoreNotifyBack.notifyBack(6, value,getParseData(value));
                isOneTime = false;
            }

        }
        @Override
        public void onResponse(int code) {
            if (code == Constants.REQUEST_SUCCESS) {

            }
        }
    };


    private  BleNotifyResponse bleNotifyResponse4 = new BleNotifyResponse() {
        @Override
        public void onNotify(UUID service, UUID character, byte[] value) {
            Lg.e("电量"," 电量结果 ：" + "长度 " + value.length + " 具体值：");
            for (int i = 0; i < value.length; i++) {
                Lg.e(DigitalTrans.bytesToHexString(value[i]) + " ");
            }
            System.out.println("");
            //if(bleDataInterface !=null){

            BluetoothResponseEntity bluetoothResponseEntity = getParseEle(value);
            byte[] version = new byte[4];
            System.arraycopy(bluetoothResponseEntity.getMacAddressByte(), 0, version, 0, 4);
            float eleres = bluetoothResponseEntity.getFlagByte()[0];
            //保存电量- 版本
            ApiBleCode.savaEle(eleres / 10);
            ApiBleCode.savaVersion(DigitalTrans.bytesToHexString(version));
            apiCoreNotifyBack.notifyBack(4, value,getParseData(value));
        }

        @Override
        public void onResponse(int code) {
            if (code == Constants.REQUEST_SUCCESS) {

            }
        }
    };

    private   BluetoothResponseEntity getParseData(byte[] value){
        String response = DigitalTrans.binaryToHexString(value);
        response = response.replaceAll(" ", "");
        byte[] responseByte = DigitalTrans.hexStrToBytes(response);
        BluetoothResponseEntity bluetoothResponseEntity  = null;
        if(responseByte.length== 16){  //非绑定开门用
            Lg.e("非绑定数据解析");
            bluetoothResponseEntity = TransDataAlgorithm.getBluetoothResponseEntitySix(responseByte);
        }else{
            bluetoothResponseEntity = TransDataAlgorithm.getBluetoothResponseEntity(responseByte);
        }
        return bluetoothResponseEntity;
    }

    private   BluetoothResponseEntity getParseEle(byte[] value){
        String response = DigitalTrans.binaryToHexString(value);
        response = response.replaceAll(" ", "");
        byte[] responseByte = DigitalTrans.hexStrToBytes(response);
        BluetoothResponseEntity bluetoothResponseEntity  = null;
        Lg.e("电量数据解析");
        bluetoothResponseEntity = TransDataAlgorithm.getBluetoothResponseEntity(responseByte);
        return bluetoothResponseEntity;
    }

}
