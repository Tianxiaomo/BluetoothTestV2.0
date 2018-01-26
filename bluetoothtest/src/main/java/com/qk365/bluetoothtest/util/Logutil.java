package com.qk365.bluetoothtest.util;

import android.util.Log;

import com.qk365.bluetooth.entity.DeviceInfo;
import com.qk365.bluetooth.util.CommonUtil;
import com.qk365.bluetooth.util.LogUtil;

import java.nio.charset.Charset;

/**
 * Created by qkz on 2018/1/11.
 */

public class Logutil {
    public static int LOG_LEVEL = 6;
    public static int ERROR = 1;
    public static int WARN = 2;
    public static int INFO = 3;
    public static int DEBUG = 4;
    public static int VERBOS = 5;

    public static String apiLogFileDirectory = CommonUtil.getSDCardPath() + "/qkbluetooth/log/";
    public static String apiLogFileName = "bluetoothtest.txt";
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static String Tag = null;

    public static void setTag(String mtag){
        Tag = mtag;
    }

    public static void e(String msg){
        if(LOG_LEVEL>ERROR)
            Log.e(Tag, msg);
    }

    public static void w(String msg){
        if(LOG_LEVEL>WARN)
            Log.w(Tag, msg);
    }
    public static void i(String msg){
        if(LOG_LEVEL>INFO)
            Log.i(Tag, msg);
    }
    public static void d(String msg){
        if(LOG_LEVEL>DEBUG)
            Log.d(Tag, msg);
    }
    public static void v(String msg){
        if(LOG_LEVEL>VERBOS)
            Log.v(Tag, msg);
    }

    public static void e(String tag,String msg){
        if(LOG_LEVEL>ERROR)
            Log.e(tag, msg);
    }

    public static void w(String tag,String msg){
        if(LOG_LEVEL>WARN)
            Log.w(tag, msg);
    }
    public static void i(String tag,String msg){
        if(LOG_LEVEL>INFO)
            Log.i(tag, msg);
    }
    public static void d(String tag,String msg){
        if(LOG_LEVEL>DEBUG)
            Log.d(tag, msg);
    }
    public static void v(String tag,String msg){
        if(LOG_LEVEL>VERBOS)
            Log.v(tag, msg);
    }

    public static void log(DeviceInfo deviceInfo){
        LogUtil.log("{\"name\":\"" + deviceInfo.name + "\",\"mac\":\"" + deviceInfo.device + "\",\"rssi\":\""
                + deviceInfo.rssi + "\",\"ele\":\"" + deviceInfo.electricQuantity + "\",\"time\":\"" + deviceInfo.timeOpen + "\"}\n", apiLogFileDirectory, apiLogFileName);
    }
}
