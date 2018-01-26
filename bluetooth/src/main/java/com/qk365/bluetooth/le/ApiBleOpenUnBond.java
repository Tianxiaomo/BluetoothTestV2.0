package com.qk365.bluetooth.le;

import android.util.Base64;

import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.qk365.bluetooth.api.BloLogParam;
import com.qk365.bluetooth.api.BloServerApi;
import com.qk365.bluetooth.entity.BluetoothRequestEntity;
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
import com.qk365.bluetooth.util.TransDataAlgorithm;

import java.text.ParseException;
import java.util.HashMap;
import java.util.UUID;


/**
 * 非绑定开门
 */
public class ApiBleOpenUnBond extends ApiBleCode  {

    private ApiCallOperateBack apiCallOpenDoorTempBack;
    private String macAddress;
    private OpenDoorResponse openDoorResponse;

    private String  mkIndex;//开门秘钥标识
    private static int requestCodeNoLine;

    ResponseNotify responseNotify;

    /**
     * 获取标识码
     */
    public void openTempDoorMark(String macAddress,ApiCallOperateBack apiCallOpenDoorTempBack){
//        EventCar.getDefault().register(this);
        responseNotify = new ResponseNotify();
        responseNotify.notifyData(macAddress,apiCoreNotifyBack);
        this.macAddress = macAddress;
        this.apiCallOpenDoorTempBack = apiCallOpenDoorTempBack;
          String  bkiMac = macAddress.replace(":","")+"-1-1";
          BluetoothRequestEntity entity = null;
            try {
                entity = TransDataAlgorithm.getRequestEntity(ApiBleCode.TEMPORARY_OPEN_DOOR_ORDER,
                    ApiBleCode.TEMPORARY_OPEN_DOOR_CHARACTERISTIC, bkiMac, ApiBleCode.TEMPORARY_OPEN_DOOR);
            } catch (ParseException e) {
                e.printStackTrace();
            }
          requestCodeNoLine = ApiBleCode.TEMPORARY_OPEN_DOOR;
          Lg.d("提交长度："+entity.getSendValue().length);
            Lg.e("索引提交值：");
            for (int i = 0; i < entity.getSendValue().length; i++) {
                Lg.e(DigitalTrans.bytesToHexString(entity.getSendValue()[i]) + " ");
            }
          System.out.println("");
          writeCode(macAddress,entity.getSendValue(),ApiBleCode.LOCK_CHARACTERISTIC_UUID6);

    }

    /**
     * 获取开门命令
     */
    private void getServerDataTempOpenCode(){
        HashMap<String,String> map = new HashMap();
        String  bkiMac = macAddress.replace(":","");
        map.put("bkimac", bkiMac);
        map.put("index", mkIndex);
        BloServerApi.getTemporyPassWord(map, new ApiCallback<CommonResult<OpenDoorResponse>>() {
            @Override
            public void done(int what, CommonResult<OpenDoorResponse> obj) {
                openDoorResponse =  obj.result;
                if(openDoorResponse.getWirecmd() != null && !"".equals(openDoorResponse.getWirecmd())){
                    byte[] orderCode = Base64.decode(obj.result.getWirecmd(), Base64.DEFAULT);
                    requestCodeNoLine = ApiBleCode.TEMPORARY_OPEN_DOOR_BACK;

                    Lg.d("提交值开门：");
                    for (int i = 0; i < orderCode.length; i++) {
                        Lg.e(DigitalTrans.bytesToHexString(orderCode[i]) + " ");
                    }
                    System.out.println("");

                    writeCode(macAddress,orderCode,ApiBleCode.LOCK_CHARACTERISTIC_UUID6);
                }
            }
            @Override
            public void error(ResultError msg) {
                if(msg != null){
                    if(msg.getCode() == 401){  //token过期
                        apiCallOpenDoorTempBack.onFaile(2);
                    }else{ //服务器连接失败
                        apiCallOpenDoorTempBack.onFaile(1);
                    }
                }else{  //服务器连接失败
                    apiCallOpenDoorTempBack.onFaile(1);
                }
            }
        });
    }

    private void sendSuccessLog(String msg){
        if(openDoorResponse == null){
            return;
        }
        BloServerApi.resultwirelog(
                BloLogParam.logMap(true, macAddress.replace(":",""), msg,
                        openDoorResponse.getWiretype(),
                        openDoorResponse.getWireid()), new ApiCallback<CommonResult<String>>() {
                    @Override
                    public void done(int what, CommonResult<String> obj) {
                        if(apiCallOpenDoorTempBack !=null){
                            apiCallOpenDoorTempBack.onSuccess("临时开门成功");
                        }
                    }

                    @Override
                    public void error(ResultError msg) {
                        Lg.d("日志写入错误");
                        if(apiCallOpenDoorTempBack !=null){
                            apiCallOpenDoorTempBack.onFaile(3);
                        }
                    }
                });
    }



    /**
     * 写入
     * @param mac
     * @param bytes
     * @param uuid
     */
    protected void writeCode(final String mac,byte[] bytes,UUID uuid){
        String mMac = null;
        if(!mac.contains(":")){
            mMac = Tools.insertCodeAddres(mac);
        }else{
            mMac = mac;
        }
        if(responseNotify!= null){
            responseNotify.setOnetTimeReq();
        }
        ClientManager.getClient().write(mMac,  ApiBleCode.LOCK_SERVICE_UUID, uuid,
                bytes, new BleWriteResponse(){
                    @Override
                    public void onResponse(int code) {
                        Lg.e("onResponse=============="+code);
                        if(code == -1){
                            if(apiCallOpenDoorTempBack !=null){
                                apiCallOpenDoorTempBack.onFaile(3);
//                                sendSuccessLog();
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
            if(apiCallOpenDoorTempBack !=null){
                Lg.d("校验结果========");
                apiCallOpenDoorTempBack.onFaile(3);
                sendSuccessLog("开门失败");
            }
            return false;
        }else{
            return true;
        }
    }


    private ApiCoreNotifyBack apiCoreNotifyBack = new ApiCoreNotifyBack() {
        @Override
        public void notifyBack(int chara, byte[] value, BluetoothResponseEntity object) {
                if(chara == 6){
                    if(requestCodeNoLine == ApiBleCode.TEMPORARY_OPEN_DOOR){
                        Lg.e(" 返回索引结果 ：" + "长度 " + value.length + " 具体值：");
                        for (int i = 0; i < value.length; i++) {
                            Lg.e(DigitalTrans.bytesToHexString(value[i]) + " ");
                        }
                        System.out.println("");
                        mkIndex = value[5]+"";
                        System.out.println("返回索引:"+mkIndex);
                        getServerDataTempOpenCode();
                    }else if( requestCodeNoLine == ApiBleCode.TEMPORARY_OPEN_DOOR_BACK){
                        Lg.d("临时开门返回========");
                        if(!checkMApp(object)){
                            if(apiCallOpenDoorTempBack !=null){
                                sendSuccessLog("临时开门失败");
                            }
                            return;
                        }
                        sendSuccessLog("临时开门成功");
                    }
                }

        }
    };

}
