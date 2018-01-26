package com.qk365.bluetooth.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qk365.bluetooth.R;
import com.qk365.bluetooth.adapter.SelectKeyAdp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Administrator on 2017/9/18.
 */

public class DialogUtil {

    static EditText etPwd;
    public static void dialogProKeySelect(final Activity context, String keyStr, final Handler handler) {
        if(context.isFinishing()){
           return;
        }
        final Dialog dialog = new Dialog(context, R.style.base_dg);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_key_select, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        etPwd = (EditText) view.findViewById(R.id.etPwd);
        TextView tvSure = (TextView) view.findViewById(R.id.tvSure);

        RecyclerView recyclerview = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        if(TextUtils.isEmpty(keyStr)){
            return;
        }

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String keys = etPwd.getText().toString().trim();
                if(keys.length()==0){
                    return;
                }
                Message msg = new Message();
                msg.what = 1;
                msg.obj = keys;
                handler.sendMessage(msg);
                dialog.dismiss();
            }
        });
        SelectKeyAdp mAdapter = new SelectKeyAdp(context,strToArray(keyStr));
        recyclerview.setAdapter(mAdapter);
        mAdapter.setAddressSearchClick(new SelectKeyAdp.SelectKeyAdpClick() {
            @Override
            public void clickItem(String key) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = key;
                handler.sendMessage(msg);
                dialog.dismiss();
                dialog.cancel();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
                dialog.dismiss();
                dialog.cancel();
            }
        });
        dialog.setContentView(view);
        dialog.setTitle(null);
        dialog.show();
    }



    public static void dialogProKeySelectGreen(final Context context,final String keyStr, final Handler handler) {
//        if(context.isFinishing()){
//            return;
//        }
        final Dialog dialog = new Dialog(context, R.style.base_dg_otc);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_key_select_oct, null);
        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        TextView tvKey = (TextView) view.findViewById(R.id.tvKey);
        TextView tvCopy = (TextView) view.findViewById(R.id.tvCopy);
        TextView tvLine = (TextView) view.findViewById(R.id.tvLine);
        if(TextUtils.isEmpty(keyStr)){
            return;
        }
        tvKey.setText("请使用配对码进行配对操作\n蓝牙配对码："+strToArray(keyStr).get(0));
        tvLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = strToArray(keyStr).get(0);
                handler.sendMessage(msg);
                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
                dialog.dismiss();
            }
        });
        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCopy(strToArray(keyStr).get(0)+"",context);
            }
        });
        dialog.setContentView(view);
        dialog.setTitle(null);
        dialog.show();
    }

    private static void onClickCopy(String str,Context context) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(str);
        Toast.makeText(context, "复制成功", Toast.LENGTH_LONG).show();
    }

    public static List<String> strToArray(String str){
        List<String> list = new ArrayList<>();
        String[] rest = str.split(",");
        for (int i = 0; i <rest.length ; i++) {
            list.add(rest[i]);
        }
        return list;
    }
}
