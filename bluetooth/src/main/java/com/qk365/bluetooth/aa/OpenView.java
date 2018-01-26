package com.qk365.bluetooth.aa;



/**
 * Created by Administrator on 2017/11/6.
 */

public interface OpenView {

    void onOpenSuccess(String msg);

    /**
     *public static final  int CODE_NOTFOND = 10001;//未扫描到设备
     *public static final  int CODE_OPEN = 10002; //指令失败
     *public static final  int CODE_AUTH = 10003; //token已过期
     *public static final  int CODE_CONNECT = 10004;//连接失败
     * 错误处理
     * @param code
     * @param msg
     */
    void onFail(int code,String msg);
}
