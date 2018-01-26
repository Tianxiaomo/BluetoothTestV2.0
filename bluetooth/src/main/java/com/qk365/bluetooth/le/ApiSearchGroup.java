package com.qk365.bluetooth.le;

import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.qk365.bluetooth.le.call.ApiCallSearchCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * Created by Administrator on 2017/11/3.
 */

public class ApiSearchGroup {


    private  List<SearchResult> mDevices = new ArrayList<>();
    private  ApiCallSearchCallBack apiCallSearchCallBack;
    /**
     * 注册蓝牙
     */
    public  void registerBle(BluetoothStateListener bluetoothStateListener){
        ClientManager.getClient().registerBluetoothStateListener(bluetoothStateListener);
//        ClientManager.getClient().registerBluetoothStateListener(new BluetoothStateListener() {
//            @Override
//            public void onBluetoothStateChanged(boolean openOrClosed) {
//                BluetoothLog.v(String.format("onBluetoothStateChanged %b", openOrClosed));
//            }
//        });
    }

    /**
     * 搜索蓝牙
     */
    public  void searchBle(ApiCallSearchCallBack apiCallSearchCallBack){
        this.apiCallSearchCallBack = apiCallSearchCallBack;
        mDevices.clear();
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(1500, 2).build();

        ClientManager.getClient().search(request, mSearchResponse);
    }
    private  final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {

        }
        @Override
        public void onDeviceFounded(final SearchResult device) {
                    BluetoothLog.w("MainActivity.onDeviceFounded " + device.device.getAddress());
//                    BluetoothLog.w("MainActivity.rssi " + device.rssi);
                    BluetoothLog.w("MainActivity.onDeviceFounded " + device.device.getName());


                    if(device.rssi<0){
                        if (!mDevices.contains(device)) {
                            String deviceName = device.getName();
                            if (deviceName!=null) {
                                deviceName = deviceName.trim();
//                            BluetoothLog.w("MainActivity.rssi---: " +device.rssi);
                                if((deviceName.startsWith("QK")
                                        || deviceName.startsWith("qk"))){
                                    BluetoothLog.e("name:"+device.getName()+"rssi: " +device.rssi+"  mac:"+device.getAddress());
                                    // BluetoothLog.w("MainActivity.00rssi---: " +device.rssi+"  mac:"+device.getAddress());
                                    mDevices.add(device);
                                    if(apiCallSearchCallBack != null){
                                        apiCallSearchCallBack.onSearchSuccess(mDevices);

                                    }
                                }
                            }
                        }
                        else{
                            if(mDevices.get(mDevices.indexOf(device)).rssi < device.rssi ){
                                mDevices.remove(device);
                                mDevices.add(device);
                            }
                            BluetoothLog.e( "2222name:"+device.getName()+"rssi: "+device.rssi+"  mac:"+device.getAddress()+ mDevices.indexOf(device)+" ");
                        }
//                        else{
//                            BluetoothLog.e( "2222name:"+device.getName()+"rssi: "+device.rssi+"  mac:"+device.getAddress());
//                            mDevices.get(0).getName();
//                            device.getName();
//                            mDevices.remove(device);
//                            mDevices.getClass();
//                        }
                    }

                    if (mDevices.size() > 0) {

                    }
        }

        @Override
        public void onSearchStopped() {
            if(apiCallSearchCallBack != null){
                apiCallSearchCallBack.onSearchStop();
            }
        }

        @Override
        public void onSearchCanceled() {
            BluetoothLog.w("MainActivity.onSearchCanceled");

        }
    };


    public  void stopSearch(){
        ClientManager.getClient().stopSearch();
    }


}
