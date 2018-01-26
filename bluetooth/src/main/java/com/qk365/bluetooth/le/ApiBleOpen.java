package com.qk365.bluetooth.le;

import android.util.Base64;

import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.qk365.bluetooth.api.BloLogParam;
import com.qk365.bluetooth.api.BloServerApi;
import com.qk365.bluetooth.entity.BluetoothResponseEntity;
import com.qk365.bluetooth.http.ApiCallback;
import com.qk365.bluetooth.http.CommonResult;
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
public class ApiBleOpen extends  ApiBleCode   {

    private ApiCallOperateBack apiCallOperateBack;
    private OpenDoorResponse openDoorResponse;
    private String mBkiMac;
    ResponseNotify responseNotify;

    public void openDoor(final String bki_Mac,final ApiCallOperateBack apiCallOperateBack){
        this.apiCallOperateBack = apiCallOperateBack;

        responseNotify = new ResponseNotify();
        responseNotify.notifyData(bki_Mac,apiCoreNotifyBack);
        this.mBkiMac = bki_Mac;
        HashMap<String,String> map = new HashMap();
        String macAddress = bki_Mac.replace(":","");

        byte[] pass = {19, -103, -2, 14, 0, -7, 100, -40, 118, 56, 31, 19, -103, -2, 14, 17, 20};
        Lg.e("===word==="+ pass+"");
        for (int i = 0; i < pass.length; i++) {
            Lg.e(DigitalTrans.bytesToHexString(pass[i]) + " ");
        }
        requestCode = ApiBleCode.OPEN_DOOR;
        write(bki_Mac,pass,LOCK_CHARACTERISTIC_UUID7);

//        map.put("bkimac", macAddress);
//        BloServerApi.mgtopendoor(map, new ApiCallback<CommonResult<OpenDoorResponse>>() {
//            @Override
//            public void done(int what, CommonResult<OpenDoorResponse> obj) {
//                openDoorResponse =  obj.result;
//
//                byte[] orderCode = Base64.decode(obj.result.getWirecmd(), Base64.DEFAULT);
//                requestCode = ApiBleCode.OPEN_DOOR;
//                write(bki_Mac,orderCode,LOCK_CHARACTERISTIC_UUID3);
//            }
//            @Override
//            public void error(ResultError msg) {
//                if(msg != null){
//                    if(msg.getCode() == 401){  //token过期
//                        apiCallOperateBack.onFaile(2);
//                    }else{  //服务器连接失败
//                        apiCallOperateBack.onFaile(1);
//                    }
//                }else{  //服务器连接失败
//                    apiCallOperateBack.onFaile(1);
//                }
//            }
//        });
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
//                sendSuccessLog("开门失败");
            }
            return false;
        }else{
            return true;
        }
    }


    private void sendSuccessLog(String msg){
        BloServerApi.resultwirelog(
                BloLogParam.logMap(true, mBkiMac.replace(":",""), msg,
                        openDoorResponse.getCmdtype(),
                        openDoorResponse.getWireid()), new ApiCallback<CommonResult<String>>() {
                    @Override
                    public void done(int what, CommonResult<String> obj) {

                    }

                    @Override
                    public void error(ResultError msg) {

                    }
                });
    }



    private ApiCoreNotifyBack apiCoreNotifyBack = new ApiCoreNotifyBack() {
        @Override
        public void notifyBack(int chara, byte[] value, BluetoothResponseEntity object) {
            if(chara == 3){
                if(!checkMApp(object)){
                    if(apiCallOperateBack !=null){  //开门失败
                        apiCallOperateBack.onFaile(3);
//                        sendSuccessLog("开门失败");
                    }
                    return;
                }
                if(requestCode == ApiBleCode.OPEN_DOOR){
                    Lg.e(" 开门返回结果 ：" + "长度 " + value + " 具体值：");
                    for (int i = 0; i < value.length; i++) {
                        Lg.e(DigitalTrans.bytesToHexString(value[i]) + " ");
                    }
                    System.out.println("");
//                    sendSuccessLog("开门成功");
                    if(apiCallOperateBack!= null){
                        apiCallOperateBack.onSuccess("开门成功");
                    }
                }
            }
        }
    };
}
