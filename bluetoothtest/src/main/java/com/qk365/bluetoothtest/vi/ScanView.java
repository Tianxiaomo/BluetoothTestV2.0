package com.qk365.bluetoothtest.vi;

import com.qk365.bluetoothtest.Base.BaseViewer;
import com.qk365.bluetooth.entity.DeviceInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public interface ScanView extends BaseViewer{

    void onScanSuccess(List<DeviceInfo> allListDevices);
    void onFail();
    void onStop();
}
