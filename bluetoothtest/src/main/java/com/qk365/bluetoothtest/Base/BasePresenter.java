package com.qk365.bluetoothtest.Base;

import android.app.Activity;

import com.qk365.bluetoothtest.dialog.ProgressDialogManager;

public class BasePresenter<T extends BaseViewer> {

    protected T viewer;
    protected Activity baseAty;
    public ProgressDialogManager progressDialogManager;
    public BasePresenter(Activity baseAty, T t) {
        this.baseAty = baseAty;
        this.viewer = t;
        progressDialogManager = new ProgressDialogManager(baseAty);
    }
}

