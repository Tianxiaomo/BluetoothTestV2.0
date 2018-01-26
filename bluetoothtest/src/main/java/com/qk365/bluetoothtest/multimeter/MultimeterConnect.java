package com.qk365.bluetoothtest.multimeter;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.qk365.bluetooth.entity.BluetoothResponseEntity;
import com.qk365.bluetooth.le.ApiBleCode;
import com.qk365.bluetooth.le.ClientManager;
import com.qk365.bluetooth.le.call.ApiCallConnection;
import com.qk365.bluetooth.le.call.ApiCoreNotifyBack;
import com.qk365.bluetooth.util.Lg;
import com.qk365.bluetoothtest.util.Logutil;


import static com.qk365.bluetooth.le.ClientManager.getClient;

/**
 * 万用表连接
 * Created by Administrator on 2018/1/24.
 */

public class MultimeterConnect extends ApiBleCode {

//    private Handler mDelivery;
    private Activity mActivity;
    private String macAddress;
    private double eleInt;
    private String eleString;
    private MultimeterCallConnection apiCallConnection;

    BluetoothDevice mUnbondDevice;
    MultimeterResponseNotify responseNotify;

    public MultimeterConnect(){
//        mDelivery = new Handler(Looper.getMainLooper());
        getClient().clearRequest(macAddress, Constants.REQUEST_NOTIFY);
        responseNotify = new MultimeterResponseNotify();
    }

    public void onDestroy(String macAddress){
        ClientManager.getClient().disconnect(macAddress);
        ClientManager.getClient().clearRequest(macAddress, Constants.REQUEST_NOTIFY);
        ClientManager.getClient().refreshCache(macAddress);
        responseNotify.closeNotify();
        if(mActivity!= null){
            try {
                mActivity.unregisterReceiver(mPairingRequestBroadcastReceiver);
            } catch (Exception e) {
                // the receiver must have been not registered or unregistered before
            }
        }
    }


    public void connect(Activity activity, final String macAddress, final MultimeterCallConnection apiCallConnection){
        this.macAddress = macAddress;
        this.mActivity = activity;
        this.apiCallConnection = apiCallConnection;
        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(2)
                .setConnectTimeout(20000)
                .setServiceDiscoverRetry(2)
                .setServiceDiscoverTimeout(10000)
                .build();

        getClient().connect(macAddress, options, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile profile) {
                responseNotify.notifyData(macAddress,apiCoreNotifyBack);

                BluetoothLog.e(String.format("profile:\n%s", profile));
                Lg.e("connecting................");
                if (code == Constants.REQUEST_SUCCESS) {
                    mUnbondDevice  = BluetoothUtils.getRemoteDevice(macAddress);
                    ApiBleCode.saveRssi(macAddress);
                    apiCallConnection.onConnSuccess();
                }else{
                    apiCallConnection.onConnFail("失败");
                }
            }
        });
    }


    private ApiCoreNotifyBack apiCoreNotifyBack = new ApiCoreNotifyBack() {
        @Override
        public void notifyBack(int chara, byte[] value, BluetoothResponseEntity object) {
            if(chara == 10){
                MultimeterEventMsg msgMultimeter = new MultimeterEventMsg(10,value);
                eleInt = msgMultimeter.getMeasuredValue();
                eleString = msgMultimeter.getMeasured();
                Logutil.e(getClass().getSimpleName(), msgMultimeter.getMeasuredValue() + "");
                apiCallConnection.onRefresh();
                return;
            }
        }
    };

    public String getEleString(){return eleString;}
    public double getEleInt(){return eleInt;}
}


