package com.qk365.bluetooth.le;

/**
 * Created by Administrator on 2018/1/12.
 */

public class MsgCode {

    public static final  int CODE_NOTFOND = 10001;//未扫描到设备
    public static final  int CODE_FAILE = 10002; //指令失败
    public static final  int CODE_AUTH = 10003; //token已过期
    public static final  int CODE_CONNECT = 10004;//连接失败


    public static final  String MSG_TOKEN = "token已过期";
    public static final  String MSG_OPERATING_FAIL = "指令失败";
    public static final  String MSG_OPERATING_SUCCESS = "指令成功";
    public static final  String MSG_CONNECT = "连接失败";
    public static final  String MSG_NOTFOND = "未扫描到设备";


}
