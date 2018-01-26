package com.qk365.bluetoothtest.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qk365.bluetoothtest.R;
import com.qk365.bluetooth.util.Tools;

/**
 * Created by YanZi on 2017/4/18.
 * describe：两个按钮的弹窗，中间包含文字
 * modify:
 * modify date:
 */
public class BaseDoubleBtnDialog extends BaseDialog {


    public BaseDoubleBtnDialog(Context context) {
        super(context);
    }

    public BaseDoubleBtnDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDoubleBtnDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder extends BaseDialog.Builder {
        //左侧按钮文字
        protected String leftBtnString;
        //右侧按钮文字
        protected String rightBtnString;


        protected BaseDialogDoubleBtnClickListener baseDialogDoubleBtnClickListener;


        public Builder setLeftBtnString(String leftBtnString) {
            this.leftBtnString = leftBtnString;
            return this;
        }

        public Builder setRightBtnString(String rightBtnString) {
            this.rightBtnString = rightBtnString;
            return this;
        }


        public Builder setBaseDialogDoubleBtnClickListener(BaseDialogDoubleBtnClickListener baseDialogDoubleBtnClickListener) {
            this.baseDialogDoubleBtnClickListener = baseDialogDoubleBtnClickListener;
            return this;
        }

        public Builder(Activity context, LayoutInflater mLayoutInflater) {
            super(context, mLayoutInflater);
        }

        @Override
        void initContent(View dialogView) {
            if (!Tools.isNull(content)) {
                ((TextView) dialogView.findViewById(R.id.tv_dg_content)).setText(content);
            } else {
                dialogView.findViewById(R.id.tv_dg_content).setVisibility(View.GONE);
                if (v_dg_divider_10.getVisibility() == View.VISIBLE) {
                    v_dg_divider_10.setVisibility(View.GONE);
                }
            }
        }


        @Override
        void initBtn(View dialogView, final Dialog dialog) {
            TextView tv_dg_single = (TextView) dialogView
                    .findViewById(R.id.tv_dg_single);
            TextView tv_dg_double_left = (TextView) dialogView
                    .findViewById(R.id.tv_dg_double_left);
            TextView tv_dg_double_right = (TextView) dialogView
                    .findViewById(R.id.tv_dg_double_right);

            tv_dg_single.setVisibility(View.GONE);

            if (!Tools.isNull(leftBtnString)) {
                tv_dg_double_left.setText(leftBtnString);
            }
            if (!Tools.isNull(rightBtnString)) {
                tv_dg_double_right.setText(rightBtnString);
            }
            tv_dg_double_left.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (baseDialogDoubleBtnClickListener != null) {
                        baseDialogDoubleBtnClickListener.clickLeftBtn(type);
                    }
                }
            });

            tv_dg_double_right.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (baseDialogDoubleBtnClickListener != null) {
                        baseDialogDoubleBtnClickListener.clickRightBtn(type);
                    }
                }
            });

        }

        @Override
        int setContentLayout() {
            return R.layout.dg_base_content;
        }


    }

}
