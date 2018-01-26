package com.qk365.bluetooth.le.call;

import com.inuker.bluetooth.library.search.SearchResult;

/**
 * 单个扫描回调
 * Created by Administrator on 2017/11/3.
 */

public interface ApiCallSearchSingle {

    void onSearchSuccess(SearchResult devices);
    void onSearchStop();
}
