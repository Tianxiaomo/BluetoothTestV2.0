package com.qk365.bluetoothtest.dialog;

/**
 * Created by YanZi on 2016/11/29.
 * describe：基类左右两个按钮的点击事件
 * modify person:
 * modify date:
 * modify desc:
 */
public interface BaseDialogDoubleBtnClickListener {

    /**
     * 弹窗类型 多个弹窗时候使用此type来区分
     * @param type
     */
    void clickLeftBtn(int type);

    /**
     * 弹窗类型 多个弹窗时候使用此type来区分
     * @param type
     */
    void clickRightBtn(int type);


}
