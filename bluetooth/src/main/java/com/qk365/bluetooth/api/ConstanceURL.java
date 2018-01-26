package com.qk365.bluetooth.api;

import com.qk365.bluetooth.util.SPHelper;

/**
 * 接口地址配置
 * modify date:
 */
public class ConstanceURL {

//    public static  int DEBUG = 0;//0测试，1仿真，2正式, 3开发

    public static String getHostUrl(){
//        if (DEBUG == 0){
//            return HOST = "http://192.168.1.118:8080/";
//        }else if (DEBUG == 1){
//           return HOST = "http://139.219.198.123:8388/";
//        }else if (DEBUG == 2){
//           return HOST = "http://58.215.175.245:8899/"; //正式
//        } else if (DEBUG == 3){
//           return HOST = "http://192.168.102.123:8080/";
//        }
        return SPHelper.getString("hostUrl");
    }

    public static void setHostUrl(String url){
        SPHelper.saveString("hostUrl",url);
    }

    /**
     * 注册
     */
    public static final String AUTHENTICATE = "api/Account/Authenticate";

    /**
     * 注册设备
     */
    public static final String REGISTERSERVER = "api/LockUse/RegisterServer";

    /**
     * 更新内置密码
     */
    public static final String UPDATEKEYS = "api/LockUse/UpdateKeys";

    /**
     * 成功失败日志
     */
    public static final String RESULTWIRELOG = "api/LockUse/ResultWirelog";

    /**
     * 获得地址
     */
    public static final String GETCUCS = "api/LockUse/GetCucs";

    /**
     * 获得单元房间
     */
    public static final String GETROOMS = "api/LockUse/GetRooms";

    /**
     * 房间绑定锁
     */
    public static final String ROOMS_BINDLOCKS = "api/LockUse/BindLocks";

    /**
     * 获得房间信息
     */
    public static final String GETBINDDETAILS = "api/LockUse/GetBindDetails";

    /**
     * 开门
     */
    public static final String MGTOPENDOOR = "api/LockUse/MgtOpenDoor";

    /**
     * 获取当前绑定密码
     */
    public static final String GETLOCKCURRENTBINDKEYS = "api/LockUse/GetLockCurrentBindKeys";

    /**
     * 上传电量和信号强度
     */
    public static final String UPLOCKELECANDRSSI = "api/LockUse/UpLockElecAndRssI";

    /**
     * 绑定数量查询
     */
    public static final String GETBINDCOUNTS = "api/LockUse/GetBindCounts";

    /**
     * 获取门锁信息
     */
    public static final String GETLOCKINFOS = "api/LockUse/GetLockInfos";

    /**
     * 获取历史信息
     */
    public static final String GETHISTORYLOGS = "api/LockUse/GetHistoryLogs";

    /**
     * 恢复出厂设置
     */
    public static final String RESTORESETTING = "api/LockUse/RestoreSetting";

    /**
     * 解绑所有用户
     */
    public static final String UNBINDLOCKS = "api/LockUse/UnBindLocks";

    /**
     * 解绑所有用户
     */
    public static final String CHECKACCEPT = "api/LockUse/CheckAccept";
    /**
     * 临时开门接口
     */
    public static final String GETTEMPORYPASSWORD = "api/LockUse/GetTemporyPassWord";

    /**
     * 获取用户已经绑定的门锁
     */
    public static final String GETLOCKSBYUSER = "api/LockUse/GetLocksByUser";


}
