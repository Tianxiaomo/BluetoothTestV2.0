package com.qk365.bluetooth.le.call;

/**
 * 对外接口访问
 * 操作
 */
public interface ApiCallOperateBack {

    void onSuccess(String message);

    /**
     * code 1.服务器连接失败  2  token过期   3开门失败
     */
    void onFaile(int code);
}
