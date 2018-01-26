package com.qk365.bluetooth;

import android.content.Context;
import android.widget.Toast;

import com.inuker.bluetooth.library.BluetoothContext;
import com.qk365.bluetooth.util.Lg;
import com.qk365.bluetooth.util.TransDataAlgorithm;

/**
 * Created by Administrator on 2017/9/20.
 */

public class BlueToothSDK {

    private static Context sContext;
    public static Boolean httPIsDebug;
    public static int dialogType = 1; //1  默认使用白色  2.其他
    public static Context getContext() {
        return sContext;
    }

    /**
     *
     * @param context
     * @param isDebug
     */
    public static void init(Context context,boolean isDebug,int mDialogType) {
        sContext = context;
        httPIsDebug = isDebug;
        dialogType = mDialogType;
        Lg.isDebug = isDebug;
        BluetoothContext.set(context);
    }


    /**
     * 获取本机的Mac地址
     * @return
     */
    public static String getLocalMacAddress(){
        String  localMacAddress = TransDataAlgorithm.getLocalMacAddress().replaceAll(":", "");
        return localMacAddress;
    }

    /**
     * -----------------------------toast 处理-------------------------------------
     * @param s
     */
    public static void toast(String s) {
        toast(s, Toast.LENGTH_SHORT);
    }

    private static Toast toast;
    private static void toast(String s, int length) {
        try {
            if (toast != null) {
                toast.setText(s);
            } else {
                toast = Toast.makeText(getContext(), s, length);
            }
            toast.show();
        } catch (Exception e) {
        }
    }


}
