package com.qk365.bluetooth.api;

import com.qk365.bluetooth.util.TransDataAlgorithm;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/25.
 */

public class BloLogParam {

    public static HashMap<String,Object> logMap(boolean isSuccess,String bki_Mac,String msg,int wireType,int wireId){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("bkimac",bki_Mac);
        if(isSuccess){
            map.put("bkwstate","1");
        }else{
            map.put("bkwstate","2");
        }
        String localMacAddress = TransDataAlgorithm.getLocalMacAddress();
        localMacAddress = localMacAddress.replaceAll(":", "");
        map.put("userdevicemac",localMacAddress);
        map.put("bkwresult",msg);
        map.put("isreg","0");
        map.put("wiretype",wireType+"");
        map.put("wireid",wireId+"");
        return map;
    }


    /**
     * 注册设备 时候日志写回参数
     * @param isSuccess
     * @param bki_Mac
     * @param msg
     * @param wireType
     * @param wireId
     * @return
     */
    public static HashMap<String,Object> logMapReg(boolean isSuccess,String bki_Mac,String msg,int wireType,int wireId){
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("bkimac",bki_Mac);
        if(isSuccess){
            map.put("bkwstate",1);
        }else{
            map.put("bkwstate",2);
        }
        String localMacAddress = TransDataAlgorithm.getLocalMacAddress();
        localMacAddress = localMacAddress.replaceAll(":", "");
        map.put("userdevicemac",localMacAddress);
        map.put("bkwresult",msg);
        map.put("isreg",1);
        map.put("wiretype",wireType+"");
        map.put("wireid",wireId);
        map.put("initbindkey",null);
        return map;
    }
}
