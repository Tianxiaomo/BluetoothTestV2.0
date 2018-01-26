package com.qk365.bluetoothtest.litepal;

import com.qk365.bluetooth.entity.DeviceInfo;
import com.qk365.bluetooth.util.CommonUtil;
import com.qk365.bluetooth.util.LogUtil;
import com.qk365.bluetoothtest.Lock;
import com.qk365.bluetoothtest.Openlock;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by qkz on 2018/1/26.
 */

public class Save {
    private static String apiLogFileDirectory = CommonUtil.getSDCardPath() + "/qkbluetoothsdk/log/";
    private static String apiLogFileName = "qkbluetooth.txt";
    private static String numFileName = "qkbluetooth_Number.txt";

    public static void saveLock(DeviceInfo deviceInfo){
        String df = new SimpleDateFormat("yyyy-MM-dd").format(new Date());//设置日期格式
        //保存device到数据库
        Lock lock = new Lock();
        lock.setName(deviceInfo.name);
        lock.setMac(deviceInfo.device.getAddress());
        lock.setEle(deviceInfo.electricQuantity);
        lock.setRealele(deviceInfo.realEle);
        lock.setRssi(deviceInfo.rssi);
        lock.setResult(deviceInfo.resultOpne);
        lock.setTime(deviceInfo.timeOpen);
        lock.setDay(df);
        lock.save();
        //保存device到文件
        LogUtil.log("{\"name\":\"" + deviceInfo.name
                + "\",\"mac\":\"" + deviceInfo.device
                + "\",\"rssi\":\"" + deviceInfo.rssi
                + "\",\"ele\":\"" + deviceInfo.electricQuantity
                + "\",\"realele\":\"" + deviceInfo.realEle
                + "\",\"ruselt\":\"" + deviceInfo.resultOpne
                + "\",\"time\":\"" + deviceInfo.timeOpen + "\"}\n", apiLogFileDirectory, apiLogFileName);
        //保存数量数据到数据库

        LockNum lockNum = new LockNum();
        if(deviceInfo.resultOpne == Openlock.openSuccess){
            List<LockNum> numList = DataSupport.where("day = ?",df).find(LockNum.class);
            if(numList.size() == 0){    //没有说明是新的一天
                lockNum.setTotal(1);
                lockNum.setSuccess(1);
                lockNum.setFail(0);
                lockNum.setDay(df);
                lockNum.save();
            }else{                      //存在的话直接更新
                LockNum temLockNum = numList.get(0);
                lockNum.setTotal(temLockNum.getTotal()+1);
                lockNum.setSuccess(temLockNum.getSuccess()+1);
                lockNum.updateAll("day = ?",df);
            }
        }else{

            List<LockNum> numList = DataSupport.where("day = ?",df).find(LockNum.class);
            if(numList.size() == 0){    //没有说明是新的一天
                lockNum.setTotal(1);
                lockNum.setSuccess(0);
                lockNum.setFail(1);
                lockNum.setDay(df);
                lockNum.save();
            }else{                      //存在的话直接更新
                LockNum temLockNum = numList.get(0);
                lockNum.setTotal(temLockNum.getTotal()+1);
                lockNum.setSuccess(temLockNum.getFail()+1);
                lockNum.updateAll("day = ?",df);
            }
        }
    }
}
