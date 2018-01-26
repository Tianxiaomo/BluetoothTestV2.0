package com.qk365.bluetoothtest.dialog;

/**
 * Created by YanZi on 2016/11/29.
 * describe：基类左右两个按钮的点击事件
 * modify person:
 * modify date:
 * modify desc:
 */
public interface BaseDialogInputDoubleBtnClickListener {

    /**
     * 弹窗类型 多个弹窗时候使用此type来区分
     *
     * @param type
     * @param text 输入的文字
     */
    void clickLeftBtn(int type, String text);

    /**
     * 弹窗类型 多个弹窗时候使用此type来区分
     *
     * @param type
     * @param text 输入的文字
     */
    void clickRightBtn(int type, String text);


}
