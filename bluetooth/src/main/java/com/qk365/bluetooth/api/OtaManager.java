package com.qk365.bluetooth.api;

import android.app.Activity;
import android.content.Intent;

import com.qk365.bluetooth.R;
import com.qk365.bluetooth.le.call.ApiOtaManagerListener;
import com.qk365.bluetooth.service.DfuBleService;
import com.qk365.bluetooth.util.Lg;

import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;

/**
 * Created by Administrator on 2017/10/20.
 */
public class OtaManager {

    private Activity mActivity;
    private ApiOtaManagerListener otaManagerListener;
    public OtaManager(Activity activity,ApiOtaManagerListener otaManagerListener){
        this.mActivity = activity;
        this.otaManagerListener = otaManagerListener;
    }

    public void onResume(){
        DfuServiceListenerHelper.registerProgressListener(mActivity, mDfuProgressListener);
    }

    public void onPause(){
        DfuServiceListenerHelper.registerProgressListener(mActivity, mDfuProgressListener);
    }

    public void deviceUpdate(String bicMac){
//        String path = "/storage/emulated/0/blueTeeth/nrf51422_xxac_110w.zip";
//        Uri muri = getUri(path);
        final DfuServiceInitiator starter = new DfuServiceInitiator(bicMac)
//                .setDeviceName("QK-TH-6850")
                .setKeepBond(false)
                .setForceDfu(false)
                .setPacketsReceiptNotificationsEnabled(false)
                .setPacketsReceiptNotificationsValue(12)
                .setUnsafeExperimentalButtonlessServiceInSecureDfuEnabled(true);
                starter.setZip(R.raw.nrf51422_xxac_1123);
//                starter.setZip(mFileStreamUri, mFilePath);
                starter.start(mActivity, DfuBleService.class);

    }


    private final DfuProgressListener mDfuProgressListener = new DfuProgressListener() {
        @Override
        public void onDeviceConnecting(String deviceAddress) {
            Lg.d("onDeviceConnecting");
        }

        @Override
        public void onDeviceConnected(String deviceAddress) {
            Lg.d("dfu-onDeviceConnected");
            otaManagerListener.onProgress("连接成功",0);
        }

        @Override
        public void onDfuProcessStarting(String deviceAddress) {
            Lg.d("dfu-onDfuProcessStarting");
            otaManagerListener.onProgress("开始上传",0);
        }

        @Override
        public void onDfuProcessStarted(String deviceAddress) {
            Lg.d("onDfuProcessStarted");
        }

        @Override
        public void onEnablingDfuMode(String deviceAddress) {
            Lg.d("onEnablingDfuMode");
        }

        @Override
        public void onProgressChanged(String deviceAddress, int percent, float speed, float avgSpeed, int currentPart, int partsTotal) {
            Lg.d("dfu-onProgressChanged-上传进度："+percent);
            otaManagerListener.onProgress("上传进度",percent);
        }

        @Override
        public void onFirmwareValidating(String deviceAddress) {
            Lg.d("dfu---onFirmwareValidating");
        }

        @Override
        public void onDeviceDisconnecting(String deviceAddress) {
            Lg.d("dfu--onDeviceDisconnecting");
        }

        @Override
        public void onDeviceDisconnected(String deviceAddress) {
            Lg.d("dfu--onDeviceDisconnected");
            otaManagerListener.onFaile("连接断开");
        }

        @Override
        public void onDfuCompleted(String deviceAddress) {
            Lg.d("dfu--onDfuCompleted");
            stopDfu();
            otaManagerListener.onSuccess("升级成功");

        }

        @Override
        public void onDfuAborted(String deviceAddress) {
            Lg.d("dfu--onDfuAborted："+deviceAddress);
            otaManagerListener.onFaile("升级失败，请重新点击升级");
        }

        @Override
        public void onError(String deviceAddress, int error, int errorType, String message) {
            Lg.d("dfu--onError："+message);
            stopDfu();
            otaManagerListener.onFaile("升级失败，请重新点击升级");
        }
    };


    private void stopDfu() {
        Intent intent = new Intent(mActivity, DfuBleService.class);
        mActivity.stopService(intent);
    }

}
