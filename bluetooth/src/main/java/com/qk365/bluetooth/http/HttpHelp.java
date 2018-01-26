package com.qk365.bluetooth.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.qk365.bluetooth.AppManager;
import com.qk365.bluetooth.BlueToothSDK;
import com.qk365.bluetooth.net.response.ResultError;
import com.qk365.bluetooth.util.OkHttp3LogInterceptor;
import com.qk365.bluetooth.util.Tools;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求
 *@author wangqiang
 */

public class HttpHelp {

    private static OkHttpClient mOkHttpClient;
    private static HttpHelp mOkHttpHelperInstance;
    private Handler mDelivery;

    private HttpHelp(){
        mOkHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(getLogInterceptor(OkHttp3LogInterceptor.Level.BODY))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    OkHttp3LogInterceptor getLogInterceptor(OkHttp3LogInterceptor.Level level) {
        OkHttp3LogInterceptor logInterceptor = new OkHttp3LogInterceptor();
        if(BlueToothSDK.httPIsDebug){
            logInterceptor.setLevel(OkHttp3LogInterceptor.Level.BODY);
        }else{
            logInterceptor.setLevel(OkHttp3LogInterceptor.Level.NONE);
        }
        return logInterceptor;
    }
    /**
     * 获取实例
     * @return
     */
    public static HttpHelp getinstance() {
        if (mOkHttpHelperInstance == null) {
            synchronized (HttpHelp.class) {
                if (mOkHttpHelperInstance == null) {
                    mOkHttpHelperInstance = new HttpHelp();
                }
            }
        }
        return mOkHttpHelperInstance;
    }

    /**
     * post 请求
     * @param tokens
     * @param url
     * @param requestModel
     * @param apiCallback
     */
    public  void post(String tokens,String url,Object requestModel,ApiCallback apiCallback){
        MediaType contentType = MediaType.parse("application/json; charset=utf-8");
        String json = new Gson().toJson(requestModel);
        RequestBody body = RequestBody.create(contentType, json);
        Request request;
        if (Tools.isNull(tokens)) {
            request = new Request.Builder()
                    .url(url).header("Accept","application/json;")
                    .post(body).build();
            Log.e("HttpHelp", "接口没有传入token或token为空");
        } else {
            request = new Request.Builder()
                    .url(url).header("Authorization",tokens)
                    .post(body).build();
        }
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(okCallBack(url,apiCallback));
    }

    /**
     * get 请求
     * @param token
     * @param url
     * @param requestModel
     * @param apiCallback
     */
    public  void get(String token,String url,Object requestModel,ApiCallback apiCallback){
        MediaType contentType = MediaType.parse("application/json; charset=utf-8");
        String json = new Gson().toJson(requestModel);
        RequestBody body = RequestBody.create(contentType, json);
        Request request;
        if (Tools.isNull(token)) {
            request = new Request.Builder()
                    .url(url)
                    .get().tag(null).build();
            Log.e("HttpHelp", "接口没有传入token或token为空");
        } else {
            //token
            request = new Request.Builder().url(url).header("Authorization",token).get().tag(null).build();
        }
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(okCallBack(url,apiCallback));
    }

    private   Callback okCallBack(final String logUrl, final ApiCallback apiCallback){
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("HttpHelp", "无法连接服务器" + call.request().toString());
                sendFailedStringCallback(e,apiCallback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resStr = response.body().string();
                int code = response.code();
                if(code == 500){
                    ResultError error = new ResultError();
                    error.setMessage("服务器无法连接");
                    apiCallback.error(new ResultError());
                }else{
                    sendSuccessResultCallback(resStr,apiCallback,logUrl);
                }

//                if(code==500){
//                    mDelivery.post(new Runnable()
//                    {
//                        @Override
//                        public void run()
//                        {
//                            closeActivity();
//                        }
//                    });
//                }else{
//                    sendSuccessResultCallback(resStr,apiCallback,logUrl);
//                }

            }
        };
    }

    /**
     * 回调失败异步处理
     * @param e
     * @param callback
     */
    private void sendFailedStringCallback( final Exception e, final ApiCallback callback)
    {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null){
                    if( e!= null){
                    }
                    ResultError errors = new ResultError();
                    errors.setMessage("网络请求失败");
                    callback.error(errors);
                    BlueToothSDK.toast("网络请求失败");
                }
            }
        });
    }

    /**
     * 请求成功处理
     * @param resStr
     * @param callback
     * @param logStr
     */
    private void sendSuccessResultCallback(final String resStr, final ApiCallback callback,final String logStr)
    {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    BaseResult r = null;
                    try {
                        r = new Gson().fromJson(resStr, callback.type);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (r != null) {
                        if (r.success == BaseResult.STATUS_FAILE) {
//                            ResultError errors =  r.error;
//                            if(errors.getCode() == 401){
//                              //  BlueToothSDK.toast("用户身份过期,请重新登陆!");
//
////                                closeActivity();
//                            }
                            callback.error(r.error);
                        } else if (r.success == BaseResult.STATUS_SUCCESS) {
                            callback.done(0, r);
                        } else {
                            ResultError errors = new ResultError();
                            Log.e("HttpHelp", "无法连接服务器11" + errors.getMessage());
                            errors.setMessage("网络数据错误");
                            callback.error(errors);
                        }
                    } else {
                        ResultError errors = new ResultError();
                        Log.e("HttpHelp", "无法连接服务器22" + errors.getMessage());
                        errors.setMessage("网络数据错误");
                        callback.error(errors);
                    }
                }
            }
        });
    }


    private void closeActivity(){
        BlueToothSDK.toast("用户身份过期,请重新登陆!");
        mDelivery.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppManager.getAppManager().finishAllActivity();
            }
        },2000);

    }

}
