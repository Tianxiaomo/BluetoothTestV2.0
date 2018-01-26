package com.qk365.bluetooth.util;

/**
 * Created by YanZi on 2017/6/1.
 * describe：
 * modify:
 * modify date:
 */
public class DigitalTrans {

    //16进制字符串转字节数组
    public static byte[] hexStrToBytes(String str)
    {
        //如果字符串长度不为偶数，则追加0
        if(str.length() % 2 !=0){
            str = "0"+str;
        }

        byte[] b = new byte[str.length() / 2];
        byte c1, c2;
        for (int y = 0, x = 0; x < str.length(); ++y, ++x)
        {
            c1 = (byte)str.charAt(x);
            if (c1 > 0x60) c1 -= 0x57;
            else if (c1 > 0x40) c1 -= 0x37;
            else c1 -= 0x30;
            c2 = (byte)str.charAt(++x);
            if (c2 > 0x60) c2 -= 0x57;
            else if (c2 > 0x40) c2 -= 0x37;
            else c2 -= 0x30;
            b[y] = (byte)((c1 << 4) + c2);
        }
        return b;
    }

    /**
     * 字节数组转16进制字符串
     * @param bytes
     * @return
     */
    public static String binaryToHexString(byte[] bytes){
        String hexStr =  "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for(int i=0;i<bytes.length;i++){
            //字节高4位
            hex = String.valueOf(hexStr.charAt((bytes[i]&0xF0)>>4));
            //字节低4位
            hex += String.valueOf(hexStr.charAt(bytes[i]&0x0F));
            result +=hex+" ";  //这里可以去掉空格，或者添加0x标识符。
        }
        return result;
    }


    /**
     * @Title:string2HexString
     * @Description:字符串转16进制字符串
     * @param strPart
     *            字符串
     * @return 16进制字符串
     * @throws
     */
    public static String string2HexString(String strPart) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();
    }

//    public static String bytesToHexString(byte b){
//        int v = b & 0xFF;
//        String hv = Integer.toHexString(v);
//        if (hv.length() < 2) {
//            hv="0"+hv;
//        }
//        return hv;
//    }

    public static String bytesToHexString(byte ...src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 字符串转换成十六进制字符串
     * @param str str 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str)
    {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++)
        {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 十六进制转换字符串
     * @param hexStr str Byte字符串(Byte之间无分隔符 如:[616C6B])
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr)
    {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++)
        {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }
}
