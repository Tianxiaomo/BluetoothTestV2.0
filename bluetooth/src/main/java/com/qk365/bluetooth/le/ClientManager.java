package com.qk365.bluetooth.le;

import com.inuker.bluetooth.library.BluetoothClient;
import com.qk365.bluetooth.BlueToothSDK;

/**
 * Created by dingjikerbo on 2016/8/27.
 */
public class ClientManager {


    private static BluetoothClient mClient;

    public static BluetoothClient getClient() {
        if (mClient == null) {
            synchronized (ClientManager.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(BlueToothSDK.getContext());
                }
            }
        }
        return mClient;
    }
}
