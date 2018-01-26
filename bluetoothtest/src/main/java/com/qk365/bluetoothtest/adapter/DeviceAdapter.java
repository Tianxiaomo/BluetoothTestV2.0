package com.qk365.bluetoothtest.adapter;

import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qk365.bluetooth.BlueToothSDK;
import com.qk365.bluetooth.click.AntiShake;
import com.qk365.bluetoothtest.R;
import com.qk365.bluetooth.entity.DeviceInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.ListenerClass;

/**
 * Created by qkz on 2018/1/23.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    List<DeviceInfo> listDevideInfo;
    ClickItem mClickItem;

    public DeviceAdapter(){
        listDevideInfo = new ArrayList<>();
    }

    public void setClickItem(ClickItem clickItem){this.mClickItem = clickItem;}

    public interface ClickItem{
        void clickItem(View view,int position);
    }

    public void setDataList(List<DeviceInfo> deviceInfoList){
        listDevideInfo.clear();
        listDevideInfo.addAll(deviceInfoList);
        super.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_devices,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DeviceInfo deviceInfo = listDevideInfo.get(position);

        if (deviceInfo != null) {
            final BluetoothDevice device = deviceInfo.device;
            if (device.getName() != null) {
                holder.tvSimpleDevicesName.setText(device.getName());
            } else {
                holder.tvSimpleDevicesName.setText("");
            }
            if (device.getAddress() != null) {
                holder.tvSimpleDevicesMacAddress.setText(device.getAddress());
            } else {
                holder.tvSimpleDevicesMacAddress.setText("");
            }
        } else {
            holder.tvSimpleDevicesName.setText("");
            holder.tvSimpleDevicesMacAddress.setText("");
        }

        holder.tvOpen.setVisibility(View.GONE);
        holder.tvOpenNearBy.setVisibility(View.GONE);

        if(0 != deviceInfo.rssi) {
            if(deviceInfo.rssi >= -80){
                holder.tvSimpleDevicesSignalStrength.setText(deviceInfo.rssi+"");
                holder.tvSimpleDevicesSignalStrength.setTextColor(Color.BLACK);
            }else{
                holder.tvSimpleDevicesSignalStrength.setText(deviceInfo.rssi+"");
                holder.tvSimpleDevicesSignalStrength.setTextColor(android.graphics.Color.RED);
            }
        }

        if(-1 !=deviceInfo.electricQuantity){
            holder.tvSimpleDevicesElectricQauntity.setText(deviceInfo.electricQuantity+"" + "V");
        }else{
            holder.tvSimpleDevicesElectricQauntity.setText("0.0V");
        }

        if(0 !=deviceInfo.realEle){
            holder.tvSimpleDevicesRealElectric.setText(Double.toString(deviceInfo.realEle) + "V");
        }else{
            holder.tvSimpleDevicesRealElectric.setText("0.0V");
        }


        if(null != deviceInfo.timeOpen){
            holder.tvSimpleDevicesTimeOpneLock.setText(deviceInfo.timeOpen);
        }else{
            holder.tvSimpleDevicesTimeOpneLock.setText(R.string.time_open_door);
        }

        if(0 != deviceInfo.resultOpne){
            holder.tvSimpleDevicesResultOpenLock.setText(deviceInfo.resultOpne + "");
        }else{
            holder.tvSimpleDevicesResultOpenLock.setText("N/A");
        }

        holder.llSimpleDevicesRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShake.check(v.getId())) {
                    //BlueToothSDK.toast(getResources.getStringArray(R.string.hold_on));
                    BlueToothSDK.toast("请稍后继续操作");
                    return;
                }

                if (mClickItem != null) {
                    mClickItem.clickItem(v,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {return listDevideInfo == null ? 0 : listDevideInfo.size();}

    static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_simple_devices_name)
        TextView tvSimpleDevicesName;
        @BindView(R.id.tv_simple_devices_mac_address)
        TextView tvSimpleDevicesMacAddress;
        @BindView(R.id.tv_simple_devices_electric_qauntity)
        TextView tvSimpleDevicesElectricQauntity;
        @BindView(R.id.tv_simple_devices_signal_strength)
        TextView tvSimpleDevicesSignalStrength;
        @BindView(R.id.tv_simple_devices_time_open_lock)
        TextView tvSimpleDevicesTimeOpneLock;
        @BindView(R.id.tv_simple_devices_result_open_lock)
        TextView tvSimpleDevicesResultOpenLock;
        @BindView(R.id.ll_simple_devices_root)
        LinearLayout llSimpleDevicesRoot;
        @BindView(R.id.tvOpen)
        TextView tvOpen;
        @BindView(R.id.tvOpenNearBy)
        TextView tvOpenNearBy;
        @BindView(R.id.tv_simple_devices_real_electric)
        TextView tvSimpleDevicesRealElectric;

        public ViewHolder(View itemview){
            super(itemview);
            ButterKnife.bind(this,itemview);
        }
    }
}
