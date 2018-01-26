package com.qk365.bluetooth.util;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by YanZi on 2017/5/26.
 * describe：
 * modify:
 * modify date:
 */
public class BluetoothAlgorithm {


    /**
     *  将需要校验的数据 加上 校验码 得到最终的数据
     * @param bufData 需要校验的数据
     * @param buflen 校验数据的长度
     * @param pcrc 校验码
     * @return
     */
    public static int get_crc16 (byte[] bufData, int buflen, byte[] pcrc)
    {
        int ret = 0;
        int CRC = 0X6363;
        int POLYNOMIAL =0x8408;
        int i, j;


        if (buflen == 0)
        {
            return ret;
        }
        for (i = 0; i < buflen; i++)
        {
            CRC ^= ((int)bufData[i] & 0x000000ff);
            for (j = 0; j < 8; j++)
            {
                if ((CRC & 0x00000001) != 0)
                {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                }
                else
                {
                    CRC >>= 1;
                }
            }
            //System.out.println(Integer.toHexString(CRC));
        }
//        Log.e("yan","crc"+Integer.toHexString(CRC));
        pcrc[0] = (byte)(CRC & 0x00ff);
        pcrc[1] = (byte)(CRC >> 8);

        return ret;
    }

    /**
     * 反射来调用BluetoothDevice.removeBond取消设备的配对
     * @param device
     */
    public static void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            Log.e("mate", e.getMessage());
        }
    }

}
