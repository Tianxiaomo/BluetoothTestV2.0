package com.qk365.bluetooth.le.call;

/**
 * ota 升级
 * 解除所有用户绑定
 */
public interface ApiOtaManagerListener {


    void onProgress(String message,int pencent);
    void onSuccess(String message);
    void onFaile(String message);
//    void onTimeOut(String message);
}
