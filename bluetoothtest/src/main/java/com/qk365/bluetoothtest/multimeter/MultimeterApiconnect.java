package com.qk365.bluetoothtest.multimeter;

/**
 * Created by qkz on 2018/1/10.
 */

public interface MultimeterApiconnect {
    void onConnSuccess();

    void onConnFail(String mag);

    void onRefresh();
}
