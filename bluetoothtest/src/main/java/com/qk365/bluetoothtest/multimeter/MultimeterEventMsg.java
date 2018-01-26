package com.qk365.bluetoothtest.multimeter;

/**
 * Created by qkz on 2018/1/9.
 */

public class MultimeterEventMsg {
    private int chara;
    private byte[] value;

    private byte flag;

    private double measuredValue;

    private final byte voltageMV2 = 0x1A;   //    电压档位量程60.00MV，数据精确到小数点后2位，标志位0X1A。
    private final byte voltageMV1 = 0x19;   //    电压档位量程600.0MV，数据精确到小数点后1位，标志位0X19。
    private final byte voltageV3 = 0x23;   //    电压档位量程6.000V，  数据精确到小数点后3位，标志位0X23。
    private final byte voltageV2 = 0x22;   //    电压档位量程60.00V，  数据精确到小数点后2位，标志位0X22。

    private final byte currentUA1 = -111;     //    电压档位量程600. 0UA，数据精确到小数点后1位，标志位0X91。
    private final byte currentMA2 = -102;     //    电压档位量程60.00MA，数据精确到小数点后2位，标志位0X9A。
    private final byte currentMA1 = -103;     //    电压档位量程600.0MA，数据精确到小数点后1位，标志位0X99。
    private final byte currentA3 = -93;       //    电压档位量程6.000A，  数据精确到小数点后3位，标志位0XA3。

    public MultimeterEventMsg(int chara, byte[] value){
        this.chara = chara;
        this.value = value;
    }

    public int getChara() {
        return chara;
    }

    public byte getFlag(){
        return value[0];
    }

    public double getMeasuredValue(){

        switch (value[0]){
            case voltageMV2:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/100;
                break;
            case voltageMV1:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/10;
                break;
            case voltageV3:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/1000;
                break;
            case voltageV2:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/100;
                break;
            case currentUA1:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/10;
                break;
            case currentMA2:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/100;
                break;
            case currentMA1:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/10;
                break;
            case currentA3:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/1000;
                break;
            default:
                measuredValue = 0;
        }
        return ((value[5]&0x80) == 0x80 ? -1:1) *measuredValue;
    }

    public String getMeasured(){
        String measure;
        switch (value[0]){
            case voltageMV2:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/100;
                measure = ((value[5]&0x80) == 0x80 ? -1:1) *measuredValue + "MV";
                break;
            case voltageMV1:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/10;
                measure = ((value[5]&0x80) == 0x80 ? -1:1) *measuredValue + "MV";
                break;
            case voltageV3:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/1000;
                measure = ((value[5]&0x80) == 0x80 ? -1:1) *measuredValue + "V";
                break;
            case voltageV2:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/100;
                measure = ((value[5]&0x80) == 0x80 ? -1:1) *measuredValue + "V";
                break;
            case currentUA1:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/10;
                measure = ((value[5]&0x80) == 0x80 ? -1:1) *measuredValue + "UA";
                break;
            case currentMA2:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/100;
                measure = ((value[5]&0x80) == 0x80 ? -1:1) *measuredValue + "MA";
                break;
            case currentMA1:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/10;
                measure = ((value[5]&0x80) == 0x80 ? -1:1) *measuredValue + "MA";
                break;
            case currentA3:
                measuredValue = ((value[5] & 0x70)/16 * Math.pow(16,3) + (value[5] & 0x0F) * Math.pow(16,2) + (value[4] & 0xF0)/16 * 16 + (value[4] & 0x0F))/1000;
                measure = ((value[5]&0x80) == 0x80 ? -1:1) *measuredValue + "A";
                break;
            default:
                measure = null;
        }
        return measure;
    }


}
