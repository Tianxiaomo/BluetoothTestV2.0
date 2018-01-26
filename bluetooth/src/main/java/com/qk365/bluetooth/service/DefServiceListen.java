package com.qk365.bluetooth.service;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;

/**
 * Created by Administrator on 2017/10/12.
 */

public class DefServiceListen implements DfuProgressListener{

    private Activity activity;
    public DefServiceListen(Activity activity){
        this.activity = activity;
    }

    public void register(){
        DfuServiceListenerHelper.registerProgressListener(activity, this);
    }
    public void unregister(){
        DfuServiceListenerHelper.unregisterProgressListener(activity, this);
    }

    @Override
    public void onDeviceConnecting(String deviceAddress) {
        Log.e("dfu===========", "onDeviceConnecting");
    }

    @Override
    public void onDeviceConnected(String deviceAddress) {
        Log.e("dfu===========", "onDeviceConnected");
    }

    @Override
    public void onDfuProcessStarting(String deviceAddress) {
        Log.e("dfu===========", "onDfuProcessStarting");
    }

    @Override
    public void onDfuProcessStarted(String deviceAddress) {
        Log.e("dfu===========", "onDfuProcessStarted");
    }

    @Override
    public void onEnablingDfuMode(String deviceAddress) {
        Log.e("dfu", "onEnablingDfuMode");
    }

    @Override
    public void onProgressChanged(String deviceAddress, int percent, float speed, float avgSpeed, int currentPart, int partsTotal) {
        Log.e("dfu===========", "onProgressChanged" + percent);
    }

    @Override
    public void onFirmwareValidating(String deviceAddress) {
        Log.e("dfu===========", "onFirmwareValidating");
    }

    @Override
    public void onDeviceDisconnecting(String deviceAddress) {

        Log.e("dfu===========", "onDeviceDisconnecting");
    }

    @Override
    public void onDeviceDisconnected(String deviceAddress) {
        Log.e("dfu===========", "onDeviceDisconnected");

    }

    @Override
    public void onDfuCompleted(String deviceAddress) {
        Log.e("dfu===========", "onDfuCompleted");
        stopDfu();
        Toast.makeText(activity, "升级成功", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDfuAborted(String deviceAddress) {
        Log.e("dfu===========", "onDfuAborted"+deviceAddress);
        Toast.makeText(activity, "升级失败，请重新点击升级。", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String deviceAddress, int error, int errorType, String message) {
        Log.e("dfu===========", "onError:"+error+"  "+message+"  "+errorType);
        stopDfu();
        Toast.makeText(activity, "升级失败，请重新点击升级。", Toast.LENGTH_SHORT).show();
    }

    private void stopDfu() {
//        Intent intent = new Intent(activity, DfuService.class);
//        activity.stopService(intent);
    }

}
