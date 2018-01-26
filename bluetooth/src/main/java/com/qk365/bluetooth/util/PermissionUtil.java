package com.qk365.bluetooth.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TangHao on 2016/12/30.
 */
public class PermissionUtil {

    public static boolean requestPermissions(Activity activity, String[] permissions) {
        return requestPermissions(activity, permissions, null, 0);
    }

    public static boolean requestPermissions(Activity activity, String[] permissions, String requestPermissionRationale) {
        return requestPermissions(activity, permissions, requestPermissionRationale, 0);
    }

    /**
     * @param activity
     * @param permissions
     * @param requestPermissionRationale
     * @param requestCode
     * @return true 表示成功进入ActivityCompat.requestPermissions()方法
     */
    public static boolean requestPermissions(Activity activity, String[] permissions, String requestPermissionRationale, int requestCode) {
        //android 6.0引入权限管理功能，当前手机版本为android6.0及以上，请求写入权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                int permissionStatus = ContextCompat.checkSelfPermission(activity, permissions[i]);
                if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                    boolean isShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]);
                    if (isShowRationale && requestPermissionRationale != null)
                        sendToast(activity, requestPermissionRationale);
//                        Snackbar.make(new View(activity), requestPermissionRationale, Snackbar.LENGTH_SHORT)
//                                .show();
                    list.add(permissions[i]);
                }
            }
            if (!list.isEmpty()) {
                ActivityCompat.requestPermissions(activity, list.toArray(new String[]{}), requestCode);
                return true;
            }
        }

        return false;
    }


    public static boolean isHavePerMissions(Activity activity,String perMissions){
        if(ContextCompat.checkSelfPermission(activity, perMissions) == PackageManager.PERMISSION_GRANTED){
            return  true;
        }else{
            PermissionUtil.requestPermissions(activity,new String[]{perMissions});
           // Toast.makeText(activity,"请在设置中打开app的"+msg+"权限",Toast.LENGTH_LONG).show();
            return  false;
        }
    }
    /**
     * toast提示
     *
     * @param mContext
     * @param text
     */
    public static void sendToast(Context mContext, String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
}
