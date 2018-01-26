package com.qk365.bluetoothtest.pt;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

import com.qk365.bluetoothtest.util.Logutil;
import com.qk365.bluetoothtest.vi.ScanView;
import com.qk365.bluetoothtest.Base.BasePresenter;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.beacon.BeaconItem;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.qk365.bluetooth.api.BloServerApi;
import com.qk365.bluetooth.entity.DeviceInfo;
import com.qk365.bluetooth.http.ApiCallback;
import com.qk365.bluetooth.http.CommonListResult;
import com.qk365.bluetooth.le.ApiSearchGroup;
import com.qk365.bluetooth.le.call.ApiCallSearchCallBack;
import com.qk365.bluetooth.net.response.RegisterResponse;
import com.qk365.bluetooth.net.response.ResultError;
import com.qk365.bluetooth.util.DigitalTrans;
import com.qk365.bluetooth.util.Lg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class ScanPt extends BasePresenter<ScanView> {

    ApiSearchGroup apiSearchGroup;
    List<DeviceInfo> allListDevices = new ArrayList<>();
    List<DeviceInfo> temListDevices = new ArrayList<>();
    public ScanPt(Activity activity, ScanView scanView){
        super(activity,scanView);
        apiSearchGroup = new ApiSearchGroup();
    }


    public void register(){
        apiSearchGroup.registerBle(new BluetoothStateListener() {
            @Override
            public void onBluetoothStateChanged(boolean openOrClosed) {
                BluetoothLog.v(String.format("onBluetoothStateChanged %b", openOrClosed));
            }
        });
    }

    public void stop(){
        apiSearchGroup.stopSearch();
    }

    public void search(){
        progressDialogManager.showLaoding("扫描.....");
        allListDevices.clear();
        apiSearchGroup.searchBle(new ApiCallSearchCallBack() {
            @Override
            public void onSearchSuccess(List<SearchResult> mDevices) {
                temListDevices.clear();
                if(mDevices.size() == 0){allListDevices.clear();}
                for (int i = 0; i <mDevices.size() ; i++) {
                    DeviceInfo deviceInfo = getDeviceInfo(mDevices.get(i));
                    if(deviceInfo != null){
                        temListDevices.add(deviceInfo);
                    }else {
//                        System.out.println("jjj");
                        Logutil.e(getClass().getSimpleName(),"扫描结果");
                        SearchResult searchResult = mDevices.get(i);
                    }
                }

                for(int j = 0;j < allListDevices.size();j++){
                    if(allListDevices.get(j).rssi <= temListDevices.get(temListDevices.size() - 1).rssi){
                        allListDevices.add(j,temListDevices.get(temListDevices.size()-1));
                        break;
                    }else if(j == allListDevices.size()-1){
                        allListDevices.add(temListDevices.get(temListDevices.size()-1));
                        break;
                    }

                }
                if(1 == temListDevices.size()){
                    allListDevices.add(temListDevices.get(0));}
                viewer.onScanSuccess(allListDevices);
            }


            @Override
            public void onSearchStop() {  //扫描完成后查询锁状态
                Lg.d("onSearchStop--");
                stop();
                progressDialogManager.dismiss();
                viewer.onStop();
            }
        });
    }

    private DeviceInfo getDeviceInfo(SearchResult searchResult){

        Beacon beacon = new Beacon(searchResult.scanRecord);
        List<BeaconItem> listbtm = beacon.mItems;
//        if(listbtm.size() == 0){
//            return;
//        }
//        if(listbtm.get(1).bytes.length == 0){
//            return;
//        }


        if(listbtm.size() > 0){
            byte[] all = new byte[12];
            for (int i = 2;i< listbtm.get(1).bytes.length; i++) {
                if(i<=13){
                    all[i-2] = listbtm.get(1).bytes[i];
                }
            }
            byte[] soft_version = new byte[4];
            byte[] hard = new byte[1];
            byte[] hard_version = new byte[4];

            System.arraycopy(all, 6, soft_version, 0, 4);
            System.arraycopy(all, 10, hard, 0, 1);
            System.arraycopy(all, 11, hard_version, 0, 1);
            StringBuffer sb = new StringBuffer();
            for (byte versionSingle : soft_version) {
                sb.append(versionSingle);
                sb.append(".");
            }
            sb.delete(sb.length() - 1, sb.length());

            String bkt_SoftVersion = hard_version[0] + "";
            String bkt_HardVersion = sb.toString();
            String bkt_VendorName = DigitalTrans.bytesToHexString(hard);


//            DeviceInfo deviceInfo = new DeviceInfo(searchResult.device,searchResult.device.getName()
//                    ,searchResult.rssi,bkt_HardVersion,bkt_VendorName,bkt_SoftVersion,null,
//                    null,0,0,0,null, null);
            DeviceInfo deviceInfo = new DeviceInfo(searchResult.device,searchResult.device.getName(),searchResult.rssi,
                    bkt_HardVersion,bkt_VendorName,bkt_SoftVersion, null,null,
                    0,0,0,0,null,0);
            return deviceInfo;
        }else {
            return null;
        }
    }

    private void registerServer(){
        if(allListDevices.size() == 0){
            viewer.onFail();
            progressDialogManager.dismiss();
            return;
        }
        BloServerApi.registerDevice(allListDevices, new ApiCallback<CommonListResult<RegisterResponse>>() {
            @Override
            public void done(int what, CommonListResult<RegisterResponse> obj) {
                if(obj.result.size()==0){
                    return;
                }
                for (RegisterResponse registerResponse : obj.result) {
                    for (DeviceInfo deviceInfo : allListDevices) {
                        String macAddress = deviceInfo.device.getAddress();
                        macAddress = macAddress.replaceAll(":", "");
                        if (macAddress.equalsIgnoreCase(registerResponse.getBkimac())) {//从第一台设备开始进行注册
                            deviceInfo.status = registerResponse.getState();
                            deviceInfo.cuc_Id = registerResponse.getCucid();
                            deviceInfo.room_Id = registerResponse.getRoomid();
                            deviceInfo.cuc_address = registerResponse.getCucaddress();
                        }
                        deviceInfo.pass_Key = registerResponse.getPasskey();
                        deviceInfo.initcmd = registerResponse.getInitcmd();
                        deviceInfo.wiretype = registerResponse.getWiretype();
                        deviceInfo.wireId = registerResponse.getWireid();
                    }
                }
                viewer.onScanSuccess(allListDevices);
                progressDialogManager.dismiss();
            }
            @Override
            public void error(ResultError msg) {
                viewer.onFail();
                progressDialogManager.dismiss();
            }
        });
    }
}
