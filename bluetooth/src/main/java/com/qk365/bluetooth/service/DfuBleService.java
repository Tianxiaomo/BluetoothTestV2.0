package com.qk365.bluetooth.service;

import android.app.Activity;

import com.qk365.bluetooth.NotificationActivity;

import no.nordicsemi.android.dfu.DfuBaseService;

public class DfuBleService extends DfuBaseService {

    @Override
    protected Class<? extends Activity> getNotificationTarget() {
        return NotificationActivity.class;
    }

    @Override
    protected boolean isDebug() {
        // Here return true if you want the service to print more logs in LogCat.
        // Library's BuildConfig in current version of Android Studio is always set to DEBUG=false, so
        // make sure you return true or your.app.BuildConfig.DEBUG here.
        return true;
    }
}