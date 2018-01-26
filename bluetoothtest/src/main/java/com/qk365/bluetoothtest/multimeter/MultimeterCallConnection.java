package com.qk365.bluetoothtest.multimeter;

/**
 *
 *
 * 连接
 * Created by Administrator on 2017/11/3.
 */

public interface MultimeterCallConnection {

    void onConnSuccess();
    void onConnFail(String mag);
    void onRefresh();
}
