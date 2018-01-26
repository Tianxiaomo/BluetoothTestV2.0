package com.qk365.bluetooth.le;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.qk365.bluetooth.BlueToothSDK;
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
 * 绑定连接
 * Created by Administrator on 2017/11/3.
 */

public class ApiBleConnectBond extends ApiBleCode   {

        private Handler mDelivery;
        private Activity mActivity;
        private String macAddress;
        private static boolean isGetEle = false;

        BluetoothDevice mUnbondDevice;
        ResponseNotify responseNotify;

        public ApiBleConnectBond(){
            mDelivery = new Handler(Looper.getMainLooper());
            getClient().clearRequest(macAddress, Constants.REQUEST_NOTIFY);
            responseNotify = new ResponseNotify();
        }

        public void onDestroy(String macAddress){
            ClientManager.getClient().disconnect(macAddress);
            ClientManager.getClient().clearRequest(macAddress, Constants.REQUEST_NOTIFY);
            ClientManager.getClient().refreshCache(macAddress);
            responseNotify.closeNotify();
            if(mActivity!= null){
                try {
                    mActivity.unregisterReceiver(mPairingRequestBroadcastReceiver);
                    mActivity.unregisterReceiver(mBondingBroadcastReceiver);
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
                    responseNotify.notifyData(macAddress,apiCoreNotifyBack);

                    BluetoothLog.v(String.format("profile:\n%s", profile));
                    Lg.d("connecting................");
                    if (code == Constants.REQUEST_SUCCESS) {
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
         * @param eventMsg
         */
        private boolean checkMApp(BluetoothResponseEntity eventMsg){
            int code = callBackCode(eventMsg);
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


        /**
         * 从服务器获取当前连接密码 非绑定时从新绑定
         */
        public void getCurrentBindKeysFun(){
            ApiBleKey ="123456";
            mActivity.registerReceiver(mPairingRequestBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST"/*BluetoothDevice.ACTION_PAIRING_REQUEST*/));
            mActivity.registerReceiver(mBondingBroadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
            bandDevice(macAddress);
        }

        private  final BroadcastReceiver mBondingBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                final int bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);
                final int previousBondState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, -1);
                Log.e("vvvvvv=======", "之前绑定状态================================"+previousBondState);
                switch (bondState) {
                    case BluetoothDevice.BOND_BONDING:

                        break;
                    case BluetoothDevice.BOND_BONDED:
                        Lg.e("绑定成功========");
                        if(mApiCallConnection != null){
                            if(true == isGetEle){
                                mApiCallConnection.onConnSuccess();
                                isGetEle = false;
                            }
                        }
                        break;
                    case BluetoothDevice.BOND_NONE:
                        Lg.e("未绑定========");
                        if(requestCode == ApiBleCode.TESTLINE || requestCode == ApiBleCode.CHECK_APP){
                            if(mApiCallConnection != null){
                                mApiCallConnection.onConnFail("绑定失败,请重试");
                            }
                        }

                        //BluetoothBleSdkApp.toast("未绑定");
                        break;
                }
            }
        };




    private ApiCoreNotifyBack apiCoreNotifyBack = new ApiCoreNotifyBack() {
        @Override
        public void notifyBack(int chara, byte[] value, BluetoothResponseEntity object) {
            if(chara == 3){
                if(requestCode == ApiBleCode.CHECK_APP){
                    Lg.e("连接校验成功-绑定");
                    if(!checkMApp(object)){
                        return;
                    }
                    int bond = ClientManager.getClient().getBondState(macAddress);
                    if (bond == BluetoothDevice.BOND_BONDED) {
                        if(mApiCallConnection != null){
                            //mApiCallConnection.onConnSuccess();
                        }
                    } else {
                        getCurrentBindKeysFun();
                    }
                }
            }
            if(chara == 4){
                isGetEle = true;
                int bond = ClientManager.getClient().getBondState(macAddress);
                if(bond == BluetoothDevice.BOND_BONDED){
                    if(mApiCallConnection != null){
                        mApiCallConnection.onConnSuccess();
                        isGetEle = false;
                    }
                }else{
                    getCurrentBindKeysFun();
                }
            }
        }
    };
}

