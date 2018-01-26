package com.qk365.bluetoothtest.multimeter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.qk365.bluetooth.BlueToothSDK;
import com.qk365.bluetooth.le.call.ApiCallConnection;
import com.qk365.bluetoothtest.dialog.ProgressDialogManager;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by qkz on 2018/1/23.
 */

public class Multimeter {

    private MultimeterConnect multimeterConnect;
    private String eleString;
    private double eleInt;
    private ProgressDialogManager mProgressDialogManager;
    private Activity activity;

    public Multimeter(Activity activity){
        this.activity = activity;
        multimeterConnect = new MultimeterConnect();
        mProgressDialogManager = new ProgressDialogManager(activity);
    }

    public void connect(final Context context) {
        final EditText editText = new EditText(context);
        SharedPreferences multimeterMac = context.getSharedPreferences("multimeter_mac",MODE_PRIVATE);
        String oldMac = multimeterMac.getString("multimeter_mac","default");
        editText.setText(oldMac);

        AlertDialog.Builder inputMacDialog = new AlertDialog.Builder(context);
        inputMacDialog.setTitle("输入万用表蓝牙MAC地址").setView(editText);
        inputMacDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String newMac = editText.getText().toString();
                SharedPreferences.Editor data = context.getSharedPreferences("multimeter_mac",MODE_PRIVATE).edit();
                data.putString("multimeter_mac",newMac);
                data.commit();


                mProgressDialogManager.showLaoding("正在连接万用表....." + newMac);
                multimeterConnect.connect(activity,newMac, new MultimeterCallConnection() {
                    @Override
                    public void onConnSuccess() {
                        mProgressDialogManager.dismiss();
                        BlueToothSDK.toast("连接成功");
                        // btnMultiCon.setBackgroundColor(0x00ff00);
//                        btnMultiCon.setBackgroundColor(getColor(R.color.green));
                    }

                    @Override
                    public void onConnFail(String mag) {
                        mProgressDialogManager.dismiss();
                        BlueToothSDK.toast("连接失败");
                    }
                    @Override
                    public void onRefresh() {
                        eleString = multimeterConnect.getEleString();
                        eleInt = multimeterConnect.getEleInt();
                    }
                });
            }
        }).show();
    }

    public String getEleString(){return eleString;}
    public double getEleInt(){return eleInt;}
}
