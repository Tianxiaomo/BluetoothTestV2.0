package com.qk365.bluetooth.le;

import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.qk365.bluetooth.le.call.ApiCallSearchSingle;
import com.qk365.bluetooth.util.Lg;

/**
 * Created by Administrator on 2017/11/3.
 */

public class ApiSearchSingle {

//    private  List<SearchResult> mDevices = new ArrayList<>();
    private ApiCallSearchSingle apiCallSearchCallBack;
    private String macAddress;
    private boolean isSearch = false;

    /**
     * 搜索蓝牙
     */
    public  void searchBle(String address,ApiCallSearchSingle apiCallSearchCallBack){
        this.apiCallSearchCallBack = apiCallSearchCallBack;
        this.macAddress = address;
        isSearch = false;
//        mDevices.clear();
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(2000, 2).build();

        ClientManager.getClient().search(request, mSearchResponse);
    }
    private  final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {

        }
        @Override
        public void onDeviceFounded(SearchResult device) {
            if(macAddress!= null && device.getAddress()!= null){
                if(macAddress.equals(device.getAddress())){
                    Lg.d("rssi--------------="+device.rssi+"   mac:"+device.getAddress());
                    if(apiCallSearchCallBack != null){
                        apiCallSearchCallBack.onSearchSuccess(device);
                        isSearch =true;
                        stopSearch();
                    }
                }
            }
        }

        @Override
        public void onSearchStopped() {
            if(!isSearch){
                if(apiCallSearchCallBack != null){
                    apiCallSearchCallBack.onSearchStop();
                }
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
