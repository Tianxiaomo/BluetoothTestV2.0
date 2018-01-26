package com.qk365.bluetooth.aa;

import android.app.Activity;
import android.os.Handler;

import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.search.SearchResult;
import com.qk365.bluetooth.le.ApiBleCode;
import com.qk365.bluetooth.le.ApiBleConnect;
import com.qk365.bluetooth.le.ApiBleOpenUnBond;
import com.qk365.bluetooth.le.ApiSearchSingle;
import com.qk365.bluetooth.le.ClientManager;
import com.qk365.bluetooth.le.MsgCode;
import com.qk365.bluetooth.le.call.ApiCallConnection;
import com.qk365.bluetooth.le.call.ApiCallOperateBack;
import com.qk365.bluetooth.le.call.ApiCallSearchSingle;
import com.qk365.bluetooth.util.Lg;
import com.qk365.bluetooth.util.Tools;

/**
 * 非绑定开门
 */
public class OpenTempPt {

    ApiBleConnect smartAccept;
    String macAddress;
    Activity activity;
    OpenView openView;
    private Handler handler = new Handler();
    private boolean isRegisterStatus = false;  //判断是否在操作过程中连接断开
    public OpenTempPt(Activity activity, OpenView openView){
        smartAccept = new ApiBleConnect();
        this.activity = activity;
        this.openView = openView;
    }


    //开始开门
    public void searchDeviece(final String macdd){
        if(!Tools.isLocationOpen(activity)){
            return;
        }
        this.macAddress = macdd;
        ApiSearchSingle searchSingle = new ApiSearchSingle();
        searchSingle.searchBle(macAddress, new ApiCallSearchSingle() {
            @Override
            public void onSearchSuccess(SearchResult devices) {
                connectBle(macdd);
            }

            @Override
            public void onSearchStop() {
                openView.onFail(MsgCode.CODE_NOTFOND,MsgCode.MSG_NOTFOND);;
            }
        });
    }

    private void connectBle(String macAddress){
        registerBle(macAddress);
        isRegisterStatus = true;
        smartAccept.connect(activity, macAddress,new ApiCallConnection(){
            @Override
            public void onConnSuccess() {
                openTemp();
            }

            @Override
            public void onConnFail(String mag) {
                openView.onFail(MsgCode.CODE_CONNECT,MsgCode.MSG_CONNECT);
                destory();
            }
        });
    }

    /**
     * 临时开门
     */
    private void openTemp(){
        ApiBleOpenUnBond tempOpen = new ApiBleOpenUnBond();
        tempOpen.openTempDoorMark(macAddress, new ApiCallOperateBack() {
            @Override
            public void onSuccess(String message) {
                destory();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        openView.onOpenSuccess("开门成功");
                    }
                },1000);
            }

            @Override
            public void onFaile(int code) {
                //code 1.服务器连接失败  2  token过期   3开门失败
                if(code == 1){
                    openView.onFail(MsgCode.CODE_CONNECT,MsgCode.MSG_CONNECT);
                }else if(code == 2){
                    openView.onFail(MsgCode.CODE_AUTH,MsgCode.MSG_TOKEN);
                }else if(code == 3){
                    openView.onFail(MsgCode.CODE_FAILE,MsgCode.MSG_OPERATING_FAIL);
                }
                destory();
            }
        });
    }


    /**
     * 获得电量
     */
    private void getEle(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            float ele =  ApiBleCode.getEle();
                //viewer.onCmdRefush("查询成功 电量："+ele+"V");
            }
        },1000);
    }


    public void destory(){
        handler.removeCallbacksAndMessages(null);
        isRegisterStatus =false;
        smartAccept.onDestroy(macAddress);
    }

    /**
     * 注册连接蓝牙
     */
    private  void registerBle(String macAddress){
        ClientManager.getClient().registerConnectStatusListener(macAddress, new BleConnectStatusListener() {
            @Override
            public void onConnectStatusChanged(String mac, int status) {
                if(isRegisterStatus){
                    if(status == 32){
                        Lg.i("000000开门失败1"+"连接状态---mac:"+mac+"   status:"+status);
                        openView.onFail(MsgCode.CODE_CONNECT,MsgCode.MSG_CONNECT);
                        isRegisterStatus = false;
                    }
                }
            }
        });

    }



}
