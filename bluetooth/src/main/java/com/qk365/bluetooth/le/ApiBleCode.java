package com.qk365.bluetooth.le;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.inuker.bluetooth.library.connect.response.BleReadRssiResponse;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.qk365.bluetooth.entity.BluetoothRequestEntity;
import com.qk365.bluetooth.entity.BluetoothResponseEntity;
import com.qk365.bluetooth.util.ClsUtils;
import com.qk365.bluetooth.util.DigitalTrans;
import com.qk365.bluetooth.util.SPHelper;
import com.qk365.bluetooth.util.TransDataAlgorithm;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/11/3.
 */

public class ApiBleCode {


    public static String ApiBleKey ="123456";
    public static int requestCode;
    //服务和特征值
    public final static UUID LOCK_SERVICE_UUID = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");

    public static final UUID LOCK_CHARACTERISTIC_UUID3 = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID LOCK_CHARACTERISTIC_UUID4 = UUID.fromString("6e400004-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID LOCK_CHARACTERISTIC_UUID5 = UUID.fromString("6e400005-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID LOCK_CHARACTERISTIC_UUID6 = UUID.fromString("6e400006-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID LOCK_CHARACTERISTIC_UUID7 = UUID.fromString("6e400007-b5a3-f393-e0a9-e50e24dcca9e");



    //账号
    public static final String ACCOUNT = "account";
    //token
    public static final String TOKEN = "token";
    //UserId
    public static final String USER_ID = "user_id";
    //密码？？？
    public static final String PASSWORD = "password";

    /**
     * 最小绑定数量
     */
    public static int MAX_BOND_NUM = 2;

    /**
     * 最小电压
     */
    public static double MIN_ELE = 0;

    /**
     * 最小信号
     */
    public static int MIN_RSSI = -100;

    public static final String MAC_ADDRESS = "FA:DD:3C:4F:82:7B";
    public static final String MAC_ADDRESS1 = "F6:1D:F9:C0:02:ED";

    //================================以下是用户主动发出的，用来区别返回时的校验======================================//
    /**
     * 校验app
     */
    public static final int CHECK_APP = 1;
    public static final byte CHECK_APP_ORDER = 0X0d;
    public static final int CHECK_APP_CHARACTERISTIC = 5;


    /**
     * 测试连接
     */
    public static final int TESTLINE = 15;
    public static final int TESTLINE_CHARACTERISTIC = 3;
    /**
     * 正常开门（蓝牙绑定开门）
     */
    public static final int OPEN_DOOR = 2;
    public static final byte OPEN_DOOR_ORDER = 0x00;
    public static final int OPEN_DOOR_CHARACTERISTIC = 3;

    /**
     * 临时开门
     */
    public static final int TEMPORARY_OPEN_DOOR = 3;
    public static final byte TEMPORARY_OPEN_DOOR_ORDER = 0X01;
    public static final int TEMPORARY_OPEN_DOOR_CHARACTERISTIC = 6;

    public static final int TEMPORARY_OPEN_DOOR_BACK = 13;
    public static final byte TEMPORARY_OPEN_DOOR_ORDER_BACK = 0x0e;


    /**
     * 读取绑定用户的数量
     */
    public static final int BOND_USER_COUNT = 4;
    public static final byte BOND_USER_COUNT_ORDER = 0x06;
    public static final int BOND_USER_COUNT_CHARACTERISTIC = 3;

    /**
     * 密钥更新 （更新绑定密钥）
     */
    public static final int UPDATE_BOND_PASSWORD = 5;
    public static final int UPDATE_BOND_PASSWORD_REGISTER = 15;
    public static final byte UPDATE_BOND_PASSWORD_ORDER = 0x0c;
    public static final int UPDATE_BOND_PASSWORD_CHARACTERISTIC = 3;

    /**
     * 密钥更新 （更新内置密钥）
     */
    public static final int UPDATE_INNER_PASSWORD = 6;
    public static final int UPDATE_INNER_PASSWORD_SECOND = 66;
    public static final byte UPDATE_INNER_PASSWORD_ORDER = 0x21;
    public static final int UPDATE_INNER_PASSWORD_CHARACTERISTIC = 3;

    /**
     * 更新开门密码
     */
    public static final int UPDATE_OPEN_PASSWORD = 7;
    public static final byte UPDATE_OPEN_PASSWORD_ORDER = 0x02;
    public static final int UPDATE_OPEN_PASSWORD_CHARACTERISTIC = 3;

    /**
     * 远程重启 (恢复出厂)
     */
    public static final int REMOTE_REBOOT = 8;
    public static final byte REMOTE_REBOOT_CODE = 0x04;
    public static final int REMOTE_REBOOT_CHARACTERISTIC = 3;

    /**
     * 解除单个用户绑定
     */
    public static final int REMOVE_SINGLE_BOND = 9;
    public static final byte REMOVE_SINGLE_BOND_CODE = 0x08;
    public static final int REMOVE_SINGLE_BOND_CHARACTERISTIC = 3;

    /**
     * 解除所有用户绑定
     */
    public static final int REMOVE_ALL_BOND = 10;
    public static final byte REMOVE_ALL_BOND_CODE = 0x09;
    public static final int REMOVE_ALL_BOND_CHARACTERISTIC = 3;


    public static final int RESTORE_SETTING = 11;
    public static final int RESTORE_SETTING_CODE = 0x22;
    public static final int RESTORE_SETTING_CHARACTERISTIC = 3;

    /**
     * OTA 升级
     */
    public static final int UPDATE_OTA = 12;
    public static final byte OTA_SETTING_CODE = 0x0a;
    public static final int OTA_SETTING_CHARACTERISTIC = 3;




    //----------------电量  版本  Rssi 保存获取---------------
    public static void savaEle(float eleres){
        SPHelper.saveFloat("eleres",eleres);
    }
    public static void savaVersion(String versionName){
        SPHelper.saveString("versionName",versionName);
    }

    public static float getEle(){
       return SPHelper.getFloat("eleres");
    }
    public static String getBleVersion(){
        return SPHelper.getString("versionName");
    }
    public static int getRssi(){
       return SPHelper.getInt("rssi");
    }
    public static void saveRssi(String MAC){
        ClientManager.getClient().readRssi(MAC, new BleReadRssiResponse() {
            @Override
            public void onResponse(int code, Integer rssi) {
                if (code == 0) {
                    SPHelper.saveInt("rssi",rssi);
                }
            }
        });
    }




    //=======================绑定相关方法========================================
    public void bandDevice(String macAddress){
        BluetoothDevice mDevice  = BluetoothUtils.getRemoteDevice(macAddress);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.d("BleManager", "device.createBond()");
            mDevice.createBond();
        } else {
            try {
                final Method createBond = mDevice.getClass().getMethod("createBond");
                if (createBond != null) {
                    Log.d("BleManager", "device.createBond() (hidden)");
                    createBond.invoke(mDevice);
                }
            } catch (final Exception e) {
                Log.w("ApiBle", "An exception occurred while creating bond", e);
            }
        }
    }

    public final BroadcastReceiver mPairingRequestBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            //设置后可以不用弹出框输入密码//http://www.jianshu.com/p/8ca12235c97d
            if(ApiBleKey!=null){
                abortBroadcast();
                try {
//                    bluetoothKey="123456";
                    ClsUtils.setPin(device.getClass(), device, ApiBleKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ApiBleKey = null;
            }
        }
    };
    //===============================================================


    //==========================返回值校验=============================
    /**
     *0XF0  失败
     *0XFF  流水号错误
     *0XFB  校验失败
     *0XFD  没有这个设备
     *0XFC  命令错误
     *0XFA  mac 地址错误
     *0XFE  内置秘钥开门错误
     *0XF8  秘钥格式错误
     *0XF9  重复开门
     *
     * @param object
     * @return
     */
    public int callBackCode(BluetoothResponseEntity object){
        BluetoothResponseEntity responseEntity =object;
        if (((byte) 0XF0) == (responseEntity.getFlagByte()[0])) {
            return 1;  //失败
        } else if(((byte) 0XFF) == (responseEntity.getFlagByte()[0])) {
            return  2; //流水号错误
        }else if(((byte) 0XFB) == (responseEntity.getFlagByte()[0])) {
            return  3; // 校验失败
        }else if(((byte) 0XFD) == (responseEntity.getFlagByte()[0])) {
            return  4;  //没有这个设备
        }else if(((byte) 0XFC) == (responseEntity.getFlagByte()[0])) {
            return  5;  //命令错误
        }else if(((byte) 0XFA) == (responseEntity.getFlagByte()[0])) {
            return  6;  // mac 地址错误
        }else if(((byte) 0XFE) == (responseEntity.getFlagByte()[0])) {
            return  7; // 内置秘钥开门错误
        }else if(((byte) 0XF8) == (responseEntity.getFlagByte()[0])) {
            return  8; // 秘钥格式错误
        }else if(((byte) 0XF9) == (responseEntity.getFlagByte()[0])) {
            return  9; // 重复开门
        }else{
            return  0;
        }
    }


    public String errorCodeMsg(int errorCode){
        String errorMsg = null;
        switch (errorCode){
            case 1:
                errorMsg = "操作失败";
                break;
            case 2:
                errorMsg = "流水号错误";
                break;
            case 3:
                errorMsg = "校验失败";
                break;
            case 4:
                errorMsg = "没有这个设备";
                break;
            case 5:
                errorMsg = "命令错误";
                break;
            case 6:
                errorMsg = "mac地址错误";
                break;
            case 7:
                errorMsg = "内置秘钥开门错误";
                break;
            case 8:
                errorMsg = "秘钥格式错误";
                break;
            case 9:
                errorMsg = "重复开门";
                break;
        }
        return  errorMsg;
    }

    public Map<String, BluetoothRequestEntity> requestMap;
    public void setRequestMap(byte orderCode, int characteristic, String macAddressStr, int type){
        if (requestMap == null) {
            requestMap = new HashMap<>();
        }
        BluetoothRequestEntity entity;
        try {
            entity = TransDataAlgorithm.getRequestEntity(orderCode, characteristic, macAddressStr, type);
            requestMap.put(DigitalTrans.bytesToHexString(orderCode), entity);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setRequestMap(byte[] bytes, int characteristic, int type){
        if (requestMap == null) {
            requestMap = new HashMap<>();
        }
        BluetoothRequestEntity bluetoothRequestEntity;
        try {
             bluetoothRequestEntity = TransDataAlgorithm.getBluetoothRequestEntity(bytes, type, characteristic);
             requestMap.put(DigitalTrans.bytesToHexString(bluetoothRequestEntity.getOrderCodeByte()), bluetoothRequestEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValueTrue(BluetoothResponseEntity eventMsg){
        BluetoothResponseEntity responseEntity = eventMsg;
        BluetoothRequestEntity bluetoothRequestEntity  = TransDataAlgorithm.checkResponseInMap(responseEntity, requestMap);
        if (bluetoothRequestEntity != null) {
            if (!TransDataAlgorithm.checkResponseAndRequestMatch(responseEntity, bluetoothRequestEntity)) {
                Log.e("yan", "返回的和发送的不匹配");
                return false;
            }else{
                return  true;
            }
        }
        return false;
    }


}
