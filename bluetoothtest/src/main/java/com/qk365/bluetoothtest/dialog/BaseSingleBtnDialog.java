package com.qk365.bluetoothtest.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qk365.bluetoothtest.R;
import com.qk365.bluetooth.util.Tools;

/**
 * Created by YanZi on 2017/4/18.
 * describe：底部一个按钮的弹窗
 * modify:
 * modify date:
 */
public class BaseSingleBtnDialog extends BaseDialog {


    public BaseSingleBtnDialog(Context context) {
        super(context);
    }

    public BaseSingleBtnDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseSingleBtnDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder extends BaseDialog.Builder {

        protected String btnText;


        protected BaseDialogSingleBtnClickListener baseDialogSingleBtnClickListener;

        public Builder setBaseDialogSingleBtnClickListener(BaseDialogSingleBtnClickListener baseDialogSingleBtnClickListener) {
            this.baseDialogSingleBtnClickListener = baseDialogSingleBtnClickListener;
            return this;
        }

        public void setBtnText(String btnText) {
            this.btnText = btnText;
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
            // 设置按钮
            TextView tv_dg_single = (TextView) dialogView
                    .findViewById(R.id.tv_dg_single);
            LinearLayout ll_dg_double_btn = (LinearLayout) dialogView
                    .findViewById(R.id.ll_dg_double_btn);

            ll_dg_double_btn.setVisibility(View.GONE);
            if (!Tools.isNull(btnText)) {
                tv_dg_single.setText(btnText);
            }
            tv_dg_single.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (baseDialogSingleBtnClickListener != null) {
                        baseDialogSingleBtnClickListener.clickBtn(type);
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
