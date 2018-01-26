package com.qk365.bluetoothtest.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.qk365.bluetoothtest.util.Logutil;

/**
 * Created by qkz on 2018/1/23.
 */

public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Logutil.e("bluetoothtest",getClass().getSimpleName());
//        progressDialogManager = new ProgressDialogManager(this);
    }

    public void MyToast(String string){
        Toast.makeText(this,string,Toast.LENGTH_LONG).show();
    }
}
