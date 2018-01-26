package com.qk365.bluetoothtest.multimeter;

import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.qk365.bluetooth.le.ApiBleCode;
import com.qk365.bluetooth.le.ClientManager;
import com.qk365.bluetooth.le.call.ApiCoreNotifyBack;
import com.qk365.bluetooth.util.DigitalTrans;
import com.qk365.bluetoothtest.util.Logutil;

import java.util.UUID;


/**
 * Created by Administrator on 2017/11/7.
 */

public class MultimeterResponseNotify {

    private String macAddress;
    private ApiCoreNotifyBack apiCoreNotifyBack;
    public void notifyData(String macAddress,ApiCoreNotifyBack apiCoreNotifyBack){
        this.macAddress = macAddress;
        this.apiCoreNotifyBack = apiCoreNotifyBack;
        if(macAddress == null){
            return;
        }

        ClientManager.getClient().notify(macAddress, UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb"),UUID.fromString("0000fff4-0000-1000-8000-00805f9b34fb"), bleNotifyResponse10);
    }

    public void closeNotify(){
        ClientManager.getClient().unnotify(macAddress, ApiBleCode.LOCK_SERVICE_UUID, ApiBleCode.LOCK_CHARACTERISTIC_UUID3, new BleUnnotifyResponse() {
            @Override
            public void onResponse(int code) {

            }
        });
    }

    //万用表用
    private BleNotifyResponse bleNotifyResponse10 = new BleNotifyResponse() {
        @Override
        public void onNotify(UUID service, UUID character, byte[] value) {
            Logutil.e(" 万用表 ：" + "长度 ", value.length+"");
            for (int i = 0; i < value.length; i++) {
                Logutil.e(DigitalTrans.bytesToHexString(value[i]) + " "," ");
            }
            apiCoreNotifyBack.notifyBack(10, value,null);
        }
        @Override
        public void onResponse(int code) {
            if (code == Constants.REQUEST_SUCCESS) {

            }
        }
    };

}
