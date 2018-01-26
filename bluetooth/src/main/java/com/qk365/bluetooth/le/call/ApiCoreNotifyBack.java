package com.qk365.bluetooth.le.call;

import com.qk365.bluetooth.entity.BluetoothResponseEntity;

/**
 * Created by Administrator on 2018/1/11.
 */

public abstract class ApiCoreNotifyBack {

    public abstract void  notifyBack(int chara, byte[] value, BluetoothResponseEntity object);
}
