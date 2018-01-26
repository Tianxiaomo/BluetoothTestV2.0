package com.qk365.bluetooth.le.call;

import com.inuker.bluetooth.library.search.SearchResult;

import java.util.List;

/**
 *
 *
 * 扫描回调
 * Created by Administrator on 2017/11/3.
 */

public interface ApiCallSearchCallBack {

    void onSearchSuccess(List<SearchResult> mDevices);
    void onSearchStop();
}
