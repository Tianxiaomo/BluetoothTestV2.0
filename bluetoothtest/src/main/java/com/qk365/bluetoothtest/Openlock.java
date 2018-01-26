package com.qk365.bluetoothtest;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.qk365.bluetooth.entity.DeviceInfo;
import com.qk365.bluetooth.le.ApiBleOpen;
import com.qk365.bluetooth.le.call.ApiCallOperateBack;
import com.qk365.bluetooth.util.Tools;
import com.qk365.bluetoothtest.dialog.BaseDialogDoubleBtnClickListener;
import com.qk365.bluetoothtest.dialog.BaseDoubleBtnDialog;
import com.qk365.bluetoothtest.dialog.ProgressDialogManager;
import com.qk365.bluetoothtest.util.Logutil;

/**
 * Created by qkz on 2018/1/26.
 */

public class Openlock {

    private DeviceInfo deviceInfo;
    public static int openSuccess = 1;
    public int openFail = -1;
    public int openFailResult = -2;
    public Openlock(){

    }

    public void open(final String mac, final Context context, final Activity activity, final ApiCallOperateBack apiCallOperateBack){
        final ProgressDialogManager progressDialogManager = new ProgressDialogManager(activity);
        progressDialogManager.showLaoding("测试中.....");
        ApiBleOpen bleOpen = new ApiBleOpen();
        bleOpen.openDoor(mac, new ApiCallOperateBack() {
            @Override
            public void onSuccess(String message) {
                progressDialogManager.dismiss();
                new BaseDoubleBtnDialog.Builder(activity, LayoutInflater.from(context)).
                        setBaseDialogDoubleBtnClickListener(new BaseDialogDoubleBtnClickListener() {
                            @Override
                            public void clickLeftBtn(int type) {
                                apiCallOperateBack.onFaile(-3);
                            }

                            @Override
                            public void clickRightBtn(int type) {
                                final OpenLockResult testOpenResult = new OpenLockResult();
                                testOpenResult.openDoorResult(mac, new ApiCallOperateBack() {
                                    @Override
                                    public void onSuccess(String message) {
                                        progressDialogManager.dismiss();
                                        BluetoothDevice mDevice = BluetoothUtils.getRemoteDevice(mac);
                                        Tools.removeCompair(mDevice);
                                        Toast.makeText(context, "==测试成功==", Toast.LENGTH_LONG).show();

                                        apiCallOperateBack.onSuccess("测试成功");
                                    }

                                    @Override
                                    public void onFaile(int code) {
                                        apiCallOperateBack.onFaile(openFailResult);
                                    }
                                });
                            }
                        }).
                        setContent("开门成功")
                        .create().show();
            }

            @Override
            public void onFaile(int msg) {
                apiCallOperateBack.onFaile(openFail);

            }

        });
    }
}
