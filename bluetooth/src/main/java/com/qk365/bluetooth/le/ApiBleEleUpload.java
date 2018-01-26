package com.qk365.bluetooth.le;

import com.qk365.bluetooth.api.BloServerApi;
import com.qk365.bluetooth.http.ApiCallback;
import com.qk365.bluetooth.http.CommonResult;
import com.qk365.bluetooth.net.request.UpLockElecAndRssIRequest;
import com.qk365.bluetooth.net.response.ResultError;

import java.util.ArrayList;
import java.util.List;

import static com.qk365.bluetooth.BlueToothSDK.toast;

/**
 * Created by Administrator on 2017/11/6.
 */

public class ApiBleEleUpload {

    /**
     * 上传电量到服务器
     */
    public static void uplockelecand(String bki_Mac,String ele,String rsssi){
        List<UpLockElecAndRssIRequest> upLockElecAndRssIRequestList = new ArrayList<>();
        UpLockElecAndRssIRequest upLockElecAndRssIRequest = new UpLockElecAndRssIRequest();
        upLockElecAndRssIRequest.setBkimac(bki_Mac.replace(":",""));
        upLockElecAndRssIRequest.setBkerssi(rsssi + "");
        upLockElecAndRssIRequest.setBkeelec(ele+ "");
        upLockElecAndRssIRequestList.add(upLockElecAndRssIRequest);
        BloServerApi.uplockelecandrssi(upLockElecAndRssIRequestList, new ApiCallback<CommonResult<String>>() {
            @Override
            public void done(int what, CommonResult<String> obj) {
            }

            @Override
            public void error(ResultError msg) {
                toast(msg.getMessage());
            }
        });
    }


}
