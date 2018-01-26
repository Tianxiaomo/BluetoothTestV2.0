package com.qk365.bluetoothtest;

import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.qk365.bluetooth.api.BloLogParam;
import com.qk365.bluetooth.api.BloServerApi;
import com.qk365.bluetooth.entity.BluetoothResponseEntity;
import com.qk365.bluetooth.http.ApiCallback;
import com.qk365.bluetooth.http.CommonResult;
import com.qk365.bluetooth.le.ApiBleCode;
import com.qk365.bluetooth.le.call.ApiCallOperateBack;
import com.qk365.bluetooth.le.call.ApiCoreNotifyBack;
import com.qk365.bluetooth.le.data.ResponseNotify;
import com.qk365.bluetooth.net.response.OpenDoorResponse;
import com.qk365.bluetooth.net.response.ResultError;
import com.qk365.bluetooth.util.DigitalTrans;
import com.qk365.bluetooth.util.Lg;
import com.qk365.bluetooth.util.Tools;

import java.util.HashMap;
import java.util.UUID;

import static com.qk365.bluetooth.le.ClientManager.getClient;


/**
 * 开门控制
 */
public class OpenLockResult extends ApiBleCode {

    private ApiCallOperateBack apiCallOperateBack;
    private OpenDoorResponse openDoorResponse;
    private String mBkiMac;
    ResponseNotify responseNotify;

    public void openDoorResult(final String bki_Mac,final ApiCallOperateBack apiCallOperateBack){
        this.apiCallOperateBack = apiCallOperateBack;

        responseNotify = new ResponseNotify();
        responseNotify.notifyData(bki_Mac,apiCoreNotifyBack);
        this.mBkiMac = bki_Mac;
        HashMap<String,String> map = new HashMap();
        String macAddress = bki_Mac.replace(":","");

        byte[] pass = {19,-103,-2,14,36,-7,100,-40,118,56,31,19,-103,-2,14,-50,-88};
        System.out.print("===pass==="+ pass+"");
        for (int i = 0; i < pass.length; i++) {
            System.out.print(DigitalTrans.bytesToHexString(pass[i]) + " ");
        }
        write(bki_Mac,pass,LOCK_CHARACTERISTIC_UUID7);
    }

    /**
     * 写入
     * @param mac
     * @param bytes
     * @param uuid
     */
    protected void write(final String mac,byte[] bytes,UUID uuid){
        String mMac = Tools.insertCodeAddres(mac);
        if(responseNotify!= null){
            responseNotify.setOnetTimeReq();
        }
        getClient().write(mMac,  ApiBleCode.LOCK_SERVICE_UUID, uuid,
                bytes, new BleWriteResponse(){
                    @Override
                    public void onResponse(int code) {
                        if(code == -1){
                            if(apiCallOperateBack!= null){  //开门失败
                                apiCallOperateBack.onFaile(3);
                            }
                        }
                    }
                } );
    }

    /**
     * 返回错误值处理
     * @param eventMsg
     */
    private boolean checkMApp(BluetoothResponseEntity eventMsg){
        int code = callBackCode(eventMsg);
        if(code != 0){
            String errorMsg = errorCodeMsg(code);
            if(apiCallOperateBack !=null){ //开门失败
                apiCallOperateBack.onFaile(3);
            }
            return false;
        }else{
            return true;
        }
    }


    private ApiCoreNotifyBack apiCoreNotifyBack = new ApiCoreNotifyBack() {
        @Override
        public void notifyBack(int chara, byte[] value, BluetoothResponseEntity object) {
            if(chara == 3){
                if(!checkMApp(object)){
                    if(apiCallOperateBack !=null){  //开门失败
                        apiCallOperateBack.onFaile(3);
                    }
                    return;
                }
                if(requestCode == ApiBleCode.OPEN_DOOR){
                    System.out.print(" 开门返回结果 ：" + "长度 " + value + " 具体值：");
                    for (int i = 0; i < value.length; i++) {
                        System.out.print(DigitalTrans.bytesToHexString(value[i]) + " ");
                    }
                    System.out.println("");
                    if(apiCallOperateBack!= null){
                        apiCallOperateBack.onSuccess("开门成功");
                    }
                }
            }
        }
    };
}
