package com.qk365.bluetooth.le;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;

import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.qk365.bluetooth.entity.BluetoothRequestEntity;
import com.qk365.bluetooth.entity.BluetoothResponseEntity;
import com.qk365.bluetooth.le.call.ApiCallConnection;
import com.qk365.bluetooth.le.call.ApiCoreNotifyBack;
import com.qk365.bluetooth.le.data.ResponseNotify;
import com.qk365.bluetooth.util.Lg;
import com.qk365.bluetooth.util.Tools;
import com.qk365.bluetooth.util.TransDataAlgorithm;

import java.text.ParseException;
import java.util.UUID;

import static com.qk365.bluetooth.le.ClientManager.getClient;

/**
 * 非绑定连接
 * Created by Administrator on 2017/11/3.
 */

public class ApiBleConnect extends ApiBleCode  {

        private Handler mDelivery;
        private Activity mActivity;
        private String macAddress;

        BluetoothDevice mUnbondDevice;
        ResponseNotify responseNotify;


        public ApiBleConnect(){
            mDelivery = new Handler(Looper.getMainLooper());
            getClient().clearRequest(macAddress, Constants.REQUEST_NOTIFY);

            responseNotify = new ResponseNotify();
        }

        public void onDestroy(String macAddress){
            ClientManager.getClient().disconnect(macAddress);
            ClientManager.getClient().clearRequest(macAddress, Constants.REQUEST_NOTIFY);
            ClientManager.getClient().refreshCache(macAddress);
            responseNotify.closeNotifySix();
            if(mActivity!= null){
                try {
                } catch (Exception e) {
                    // the receiver must have been not registered or unregistered before
                }
            }
        }


        private ApiCallConnection mApiCallConnection;
        public void connect(Activity activity,final String macAddress, ApiCallConnection apiCallConnection){
            this.macAddress = macAddress;
            this.mActivity = activity;
            this.mApiCallConnection = apiCallConnection;
            BleConnectOptions options = new BleConnectOptions.Builder()
                    .setConnectRetry(0)
                    .setConnectTimeout(20000)
                    .setServiceDiscoverRetry(1)
                    .setServiceDiscoverTimeout(20000)
                    .build();

            getClient().connect(macAddress, options, new BleConnectResponse() {
                @Override
                public void onResponse(int code, BleGattProfile profile) {
                    BluetoothLog.v(String.format("profile:\n%s", profile));

                    responseNotify.notifyData(macAddress,apiCoreNotifyBack);
                    Lg.d("connecting................");
                    if (code == Constants.REQUEST_SUCCESS) {
                        Lg.d("connecting.....success...........");
                        mUnbondDevice  = BluetoothUtils.getRemoteDevice(macAddress);
                        ApiBleCode.saveRssi(macAddress);
                        sendCheck(macAddress);
                        // mAdapter.setGattProfile(profile);
                    }
                }
            });
        }


        /**
         * 校验连接
         * @param mac
         */
        private void sendCheck(String mac){
//        BluetoothDevice mDevice  = BluetoothUtils.getRemoteDevice(mac);
            String macDress = mac.replace(":","");
            BluetoothRequestEntity entity = null;
            try {
                entity = TransDataAlgorithm.getRequestEntity(ApiBleCode.CHECK_APP_ORDER, ApiBleCode.CHECK_APP_CHARACTERISTIC,  macDress, ApiBleCode.CHECK_APP);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            requestCode =ApiBleCode.CHECK_APP;
            //setRequestMap(ApiBleCode.CHECK_APP_ORDER,ApiBleCode.CHECK_APP_CHARACTERISTIC,macDress,ApiBleCode.CHECK_APP);
            write(mac,entity.getSendValue(),ApiBleCode.LOCK_CHARACTERISTIC_UUID5);
        }


        /**
         * 写入
         * @param mac
         * @param bytes
         * @param uuid
         */
        protected void write(final String mac,byte[] bytes,UUID uuid){
            String mMac = null;
            if(!mac.contains(":")){
                mMac = Tools.insertCodeAddres(mac);
            }else{
                mMac = mac;
            }
            if(responseNotify!= null){
                responseNotify.setOnetTimeReq();
            }
            getClient().write(mMac,  ApiBleCode.LOCK_SERVICE_UUID, uuid,
                    bytes, new BleWriteResponse(){
                        @Override
                        public void onResponse(int code) {
                            System.out.println("onResponse=============="+code);
                              if(requestCode == ApiBleCode.CHECK_APP  && code==-1){
                                //BluetoothDevice mDevice  = BluetoothUtils.getRemoteDevice(mac);
                                //Tools.removeCompair(mDevice);
                                if(mApiCallConnection != null){
                                    mApiCallConnection.onConnFail("校验失败");
                                }
                            }
                        }
                    } );

        }


        /**
         * 返回错误值处理
         * @param object
         */
        private boolean checkMApp(BluetoothResponseEntity object){
            int code = callBackCode(object);
            if(code != 0){
                String errorMsg = errorCodeMsg(code);
                if(mApiCallConnection != null){
                    mApiCallConnection.onConnFail(errorMsg);
                }
                return false;
            }else{
                return true;
            }
        }



        private ApiCoreNotifyBack apiCoreNotifyBack = new ApiCoreNotifyBack() {
            @Override
            public void notifyBack(int chara, byte[] value, BluetoothResponseEntity object) {
//                Lg.d("返回1。。。。。。："+value);
                if(chara == 3){
                    if(requestCode == ApiBleCode.CHECK_APP){
                        Lg.d("连接校验成功");
                        if(!checkMApp(object)){
                            return;
                        }
                        if(mApiCallConnection != null){
                            mApiCallConnection.onConnSuccess();
                        }
                    }
                }
            }
        };

}

