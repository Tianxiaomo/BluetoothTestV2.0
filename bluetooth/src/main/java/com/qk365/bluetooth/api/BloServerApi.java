package com.qk365.bluetooth.api;

import com.qk365.bluetooth.BlueToothSDK;
import com.qk365.bluetooth.entity.DeviceInfo;
import com.qk365.bluetooth.http.ApiCallback;
import com.qk365.bluetooth.http.CommonListResult;
import com.qk365.bluetooth.http.CommonResult;
import com.qk365.bluetooth.http.HttpHelp;
import com.qk365.bluetooth.net.request.RegisterRequest;
import com.qk365.bluetooth.net.response.GetBindCountsResponse;
import com.qk365.bluetooth.net.response.GetBindDetailResponse;
import com.qk365.bluetooth.net.response.GetGucsResponse;
import com.qk365.bluetooth.net.response.GetHistoryLogsResponse;
import com.qk365.bluetooth.net.response.GetLockCurrentBindKeysResponse;
import com.qk365.bluetooth.net.response.GetLockInfosResponse;
import com.qk365.bluetooth.net.response.GetRoomResponse;
import com.qk365.bluetooth.net.response.LockAddress;
import com.qk365.bluetooth.net.response.LockBondAddressResponse;
import com.qk365.bluetooth.net.response.LoginResponse;
import com.qk365.bluetooth.net.response.OpenDoorResponse;
import com.qk365.bluetooth.net.response.RegisterResponse;
import com.qk365.bluetooth.net.response.RestoreSettingResponse;
import com.qk365.bluetooth.net.response.UnBindLocksResponse;
import com.qk365.bluetooth.net.response.UpdateKeyResponse;
import com.qk365.bluetooth.util.SPHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.qk365.bluetooth.util.DigitalTrans.binaryToHexString;

/**
 * Created by Administrator on 2017/9/20.
 */

public class BloServerApi extends ConstanceURL{

    //public static String token = "Jh3DfXXMdO8l4jvZR5XNh8FDUSklvhaohMVvbBFE3W6U7jSqZ-k0bwNZNU3_SZpn0LN9XGhlLdMz6e76LwkjyPDu4rQwMSHCSTJsbNHtd44niWeK12z7DxR2HdtNkNmkEFzOOLLjnrG-H982lQf5JgOXSc3ad3-PI_r8B5jEPSmcspJahktt6pseg1spFCoko2UTBirbh01vwfzl0rUJpIJhP5xdGkGgYfK292d5jStJ90QAIDskTg9-V6XmWcMTiACf0oa0HKEuleAvx4GyyuEePdy6G2DXDzO3b2TepLZfTu5F6jp6YucfHIh7jF2IE0rmCR6pU7Pjo4BaLwnYFznRt42ihlFTbSODlgy6si6gqUNSXvw-4zSvhE9umPm8Rs4EuMCMbLqOkQTla6nwdVLaHlicwJphUsxXvPHr9mTmSdqkqyMRnPteNI8S7uLx";
    public static final  String  default_pwd ="123456";

    /** 注册 */
    public static  void login(HashMap<String,String> map,ApiCallback<CommonResult<LoginResponse>> apiCallback){
        HttpHelp.getinstance().post(null,getHostUrl() + ConstanceURL.AUTHENTICATE,map,apiCallback);
    }

    /** 注册设备*/
    public static void registerDevice(List<DeviceInfo> allBluetoothDevices, ApiCallback<CommonListResult<RegisterResponse>> apiCallback){
        List<RegisterRequest> registerRequestModels = new ArrayList<>();
        for (DeviceInfo deviceInfo : allBluetoothDevices) {
            RegisterRequest registerRequestModel = new RegisterRequest();
           // registerRequestModel.setIsrereg(0);
            registerRequestModel.setBkiname(deviceInfo.device.getName());
            String macAddress = deviceInfo.device.getAddress();
            macAddress = macAddress.replaceAll(":", "");
            registerRequestModel.setBkimac(macAddress);
            registerRequestModel.setBkimgtmac(BlueToothSDK.getLocalMacAddress());

            byte [] aa = {Byte.parseByte(deviceInfo.bkt_SoftVersion)};
            String version =  binaryToHexString(aa);
            registerRequestModel.setBktsoftversion(version);
            registerRequestModel.setBkthardversion(deviceInfo.bkt_HardVersion);
           // registerRequestModel.setBktvendorName(deviceInfo.bkt_VendorName);
            registerRequestModel.setInitbindkey(default_pwd);
            registerRequestModels.add(registerRequestModel);
        }
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.REGISTERSERVER,registerRequestModels,apiCallback);
    }

    /** 更新内置密码*/
    public static  void updatekeys(HashMap<String,String> map,ApiCallback<CommonResult<UpdateKeyResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.UPDATEKEYS,map,apiCallback);
    }

    /** 成功失败日志*/
    public static  void resultwirelog(HashMap<String,Object> map,ApiCallback<CommonResult<String>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.RESULTWIRELOG,map,apiCallback);
    }
    /** 获得地址*/
    public static  void getcucs(HashMap<String,String> map,ApiCallback<CommonListResult<GetGucsResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.GETCUCS,map,apiCallback);
    }

    /** 获得单元房间*/
    public static  void getrooms(Object map,ApiCallback<CommonListResult<GetRoomResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.GETROOMS,map,apiCallback);
    }
    /** 房间绑定锁*/
    public static  void roomsBindlocks(Object map,ApiCallback<CommonListResult<LockBondAddressResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.ROOMS_BINDLOCKS,map,apiCallback);
    }
    /**  获得房间信息*/
    public static  void getbindDetails(String urlappend,HashMap<String,String> map,ApiCallback<CommonListResult<GetBindDetailResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.GETBINDDETAILS+urlappend,map,apiCallback);
    }
    /**  开门*/
    public static void mgtopendoor(HashMap<String,String> map,ApiCallback<CommonResult<OpenDoorResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.MGTOPENDOOR,map,apiCallback);
    }
    /**  获取当前绑定密码*/
    public static  void getlockcurrentbindkeys(Object map,ApiCallback<CommonListResult<GetLockCurrentBindKeysResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.GETLOCKCURRENTBINDKEYS,map,apiCallback);
    }
    /**上传电量和信号强度*/
    public static  void uplockelecandrssi(Object map,ApiCallback<CommonResult<String>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.UPLOCKELECANDRSSI,map,apiCallback);
    }
    /**绑定数量查询*/
    public static  void getbindcounts(HashMap<String,String> map,ApiCallback<CommonResult<GetBindCountsResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.GETBINDCOUNTS,map,apiCallback);
    }
    /**获取门锁信息*/
    public static  void getlockinfos(HashMap<String,String> map,ApiCallback<CommonResult<GetLockInfosResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.GETLOCKINFOS,map,apiCallback);
    }
    /**获取历史信息*/
    public static  void gethistorylogs(HashMap<String,String> map,ApiCallback<CommonListResult<GetHistoryLogsResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.GETHISTORYLOGS,map,apiCallback);
    }
    /**恢复出厂设置*/
    public static  void restoresetting(HashMap<String,String> map,ApiCallback<CommonResult<RestoreSettingResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.RESTORESETTING,map,apiCallback);
    }
    /**解绑所有用户*/
    public static  void unbindlocks(HashMap<String,String> map,ApiCallback<CommonResult<UnBindLocksResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.UNBINDLOCKS,map,apiCallback);
    }
    /**解绑所有用户*/
    public static  void checkaccept(HashMap<String,String> map,ApiCallback<CommonResult<LoginResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.CHECKACCEPT,map,apiCallback);
    }
    /**临时开门接口*/
    public static  void getTemporyPassWord(HashMap<String,String> map,ApiCallback<CommonResult<OpenDoorResponse>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.GETTEMPORYPASSWORD,map,apiCallback);
    }

    /**临时开门接口*/
    public static  void getLocksByuser(HashMap<String,String> map,ApiCallback<CommonListResult<LockAddress>> apiCallback){
        HttpHelp.getinstance().post(getToken(),getHostUrl() + ConstanceURL.GETLOCKSBYUSER,map,apiCallback);
    }

    private static String getToken(){
       return SPHelper.getString("token");
    }
}
