package com.qk365.bluetoothtest;
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.qk365.bluetooth.BlueToothSDK;
import com.qk365.bluetooth.click.AntiShake;
import com.qk365.bluetooth.entity.DeviceInfo;
import com.qk365.bluetooth.le.ApiBleCode;
import com.qk365.bluetooth.le.ApiBleConnectBond;
import com.qk365.bluetooth.le.ApiBleOpen;
import com.qk365.bluetooth.le.call.ApiCallConnection;
import com.qk365.bluetooth.le.call.ApiCallOperateBack;
import com.qk365.bluetooth.util.Tools;
import com.qk365.bluetoothtest.Base.BaseActivity;
import com.qk365.bluetoothtest.adapter.DeviceAdapter;
import com.qk365.bluetoothtest.dialog.BaseDialogDoubleBtnClickListener;
import com.qk365.bluetoothtest.dialog.BaseDoubleBtnDialog;
import com.qk365.bluetoothtest.dialog.ProgressDialogManager;
import com.qk365.bluetoothtest.litepal.Save;
import com.qk365.bluetoothtest.multimeter.Multimeter;
import com.qk365.bluetoothtest.pt.ScanPt;
import com.qk365.bluetoothtest.util.Logutil;
import com.qk365.bluetoothtest.util.PermissionUtil;
import com.qk365.bluetoothtest.vi.ScanView;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity{
    @BindView(R.id.button_multimeter_connect)
    TextView btnMultiCon;
    @BindView(R.id.button_rescan)
    TextView btnRescan;
    @BindView(R.id.lv_lock_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_today_num)
    TextView tvTodayNum;
    @BindView(R.id.tv_success_num)
    TextView tvSuccessNum;
    @BindView(R.id.tv_fail_num)
    TextView tvFailNum;

    private DeviceAdapter deviceAdapter;
    List<DeviceInfo> allListDeviceInfo = new ArrayList<>();
    ScanPt scanPt;
    private Context context;
    private Multimeter multimeter;
    private int miniMac;
    private DeviceInfo deviceInfo;
    private int Position;
    private String Tag;
    private String address;
    private ProgressDialogManager progressDialogManager;
    private ApiBleConnectBond connectBond;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BlueToothSDK.init(this, true, 1);
        initRermission();
        initView();
        initScan();
        initPara();
    }


    private void initPara(){
        multimeter = new Multimeter(this);
        miniMac = getResources().getInteger(R.integer.minRssi);
        Position = -1;
        Tag = getClass().getSimpleName();
        context = MainActivity.this;
        progressDialogManager = new ProgressDialogManager(MainActivity.this);
        connectBond = new ApiBleConnectBond();
        Connector.getDatabase();
    }

    private void initRermission(){
        PermissionUtil.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.BLUETOOTH_PRIVILEGED,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                });

        if (enableBluetooth()) {

        } else {
            MyToast("请打开蓝牙");
        }
    }

    private void initView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        deviceAdapter = new DeviceAdapter();
        recyclerView.setAdapter(deviceAdapter);
        deviceAdapter.setClickItem(new DeviceAdapter.ClickItem() {
            @Override
            public void clickItem(final View view, final int position) {

                //避免不停点击连接
                if(-1 != Position){
                    Destroy();
                }

                deviceInfo = allListDeviceInfo.get(position);
                if (deviceInfo.rssi < miniMac) {
                    Toast.makeText(context, "信号强度太小，请靠近并重新扫描", Toast.LENGTH_LONG).show();
                    Position = -1;
                    deviceInfo = null;
                    return;
                }
                Position = position;
                address = deviceInfo.device.getAddress();
                progressDialogManager.showLaoding(getString(R.string.connecting));
                progressDialogManager.backPressed(new BackbackPressed() {
                    @Override
                    public void onCallback() {
                        Destroy();
                    }
                });
                connectBond.connect(MainActivity.this, address, new ApiCallConnection() {
                    @Override
                    public void onConnSuccess() {
                        deviceInfo.status = 1;
                        deviceInfo.realEle = multimeter.getEleInt();
                        deviceInfo.electricQuantity = ApiBleCode.getEle();
                        allListDeviceInfo.set(position, deviceInfo);
                        deviceAdapter.notifyDataSetChanged();
                        progressDialogManager.dismiss();
                        deviceInfo = null;
                        BlueToothSDK.toast("连接成功" + address);
//                        view.setBackgroundColor(getResources().getColor(R.color.green));
                    }

                    @Override
                    public void onConnFail(String mag) {
                        progressDialogManager.dismiss();
                        BlueToothSDK.toast("连接失败" + address);
                        Destroy();
//                        view.setBackgroundColor(getResources().getColor(R.color.font_red));
                    }
                });
            }
        });
    }

    private void initScan(){
        scanPt = new ScanPt(this, new ScanView() {
            @Override
            public void onScanSuccess(List<DeviceInfo> listDevices) {
                allListDeviceInfo.clear();
                allListDeviceInfo.addAll(listDevices);
                deviceAdapter.setDataList(allListDeviceInfo);
            }

            @Override
            public void onFail() {
                MyToast(getString(R.string.scan_Fail));
            }

            @Override
            public void onStop() {
                MyToast(getString(R.string.scan_Stop));
            }
        });
        scanPt.register();
    }

    @OnClick({R.id.button_multimeter_connect,R.id.button_rescan,R.id.button_open_lock})
    public void onViewClicked(final View view){
        switch(view.getId()){
            case R.id.button_multimeter_connect:
                multimeter.connect(this);
                Logutil.e(Tag,getString(R.string.click_conn_multimeter));
                break;
            case R.id.button_rescan:
                Logutil.e(Tag,getString(R.string.click_rescan));

                //避免不停点击连接
                if(-1 != Position){
                    Destroy();
                }

                scanPt.search();
                break;
            case R.id.button_open_lock:
                Logutil.e(Tag,getString(R.string.click_open_lock));

                if (AntiShake.check(view.getId())) {
                    BlueToothSDK.toast(getString(R.string.hold_on));
                    return;
                }
                if(-1 == Position){
                    MyToast(getString(R.string.select_lock));
                    break;
                }

                String mac = allListDeviceInfo.get(Position).device.getAddress();

                final Openlock openlock = new Openlock();
                openlock.open(mac,MainActivity.this,MainActivity.this, new ApiCallOperateBack() {
                    @Override
                    public void onSuccess(String message) {
                        deviceInfo = allListDeviceInfo.get(Position);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        deviceInfo.timeOpen = df.format(new Date());
                        deviceInfo.resultOpne = openlock.openSuccess;
                        allListDeviceInfo.set(Position,deviceInfo);
                        deviceAdapter.notifyDataSetChanged();
                        Save.saveLock(deviceInfo);
                    }
                    @Override
                    public void onFaile(int code) {
                        deviceInfo = allListDeviceInfo.get(Position);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        deviceInfo.timeOpen = df.format(new Date());
                        deviceInfo.resultOpne = code;
                        allListDeviceInfo.set(Position,deviceInfo);
                        deviceAdapter.notifyDataSetChanged();
                        Save.saveLock(deviceInfo);
                        Destroy();
                    }
                });
                break;
            default:
                break;
        }
    }


    private void Destroy(){
        address = allListDeviceInfo.get(Position).device.getAddress();
        Logutil.e(Tag,getString(R.string.connect_stop));
        connectBond.onDestroy(address);
        BluetoothDevice mDevice = BluetoothUtils.getRemoteDevice(address);
        Tools.removeCompair(mDevice);
        deviceInfo = null;
        Position = -1;
    }

    //蓝牙
    private BluetoothAdapter mBluetoothAdapter;
    public final int REQUEST_BT_BLUETOOTH = 0x0001;
    public boolean enableBluetooth() {
        BluetoothManager bluetoothManager =
                (BluetoothManager) this.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        //正常的蓝牙处理
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtBluetooth, REQUEST_BT_BLUETOOTH);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
