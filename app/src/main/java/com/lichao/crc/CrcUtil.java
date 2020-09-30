package com.lichao.crc;

import android.util.Log;

/**
 * @author lichao
 * @date 2020/9/30
 */
public class CrcUtil {

    /**
     * CRC检验
     * @param source
     * @return
     */
    public static String getCRC16(String source) {
        int crc = 0xA1EC; 		 	 // 初始值
        int polynomial = 0x1021;	         // 校验公式 0001 0000 0010 0001
        byte[] bytes = stringToHexByte(source);  //把普通字符串转换成十六进制字符串

        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit)
                    crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        StringBuffer result = new StringBuffer(Integer.toHexString(crc));
        while (result.length() < 4) {		 //CRC检验一般为4位，不足4位补0
            result.insert(0, "0");
        }
        return result.toString().toUpperCase();
    }

    /**
     * 把字符串转换成十六进制字节数组
     *
     * @param s
     * @return byte[]
     */
    public static byte[] stringToHexByte(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * 检验和 异或 再 取反
     * @param in
     * @return
     */
    public static String checkSum_XOR(String in) {
        String bbc = null;
        int i = 0, j = 0;
        int len = in.length();
        short inb[] = new short[len];

        for(i = 0; i < len; i++) {
            //将String里的每一个char转换为Hex
            inb[i] = charToHex(in.charAt(i));
        }

        //将每两个Hex合并成一个byte
        for(i = 0; i < len; i++) {
            inb[j] = (byte) (((inb[i]<<4)&0x00f0)|((inb[i+1])&0x000f));
            i++;
            j++;
        }
        //校验值
        byte temp = 0x00;

        //异或
        for(i = 0; i < len / 2; i++) {
            temp ^= inb[i];
        }

        //取反  二进制Integer.toBinaryString()  十六进制Integer.toHexString()
        bbc = Integer.toHexString(~temp).toUpperCase();
        Log.i("lichao", "未截取校验和:" + bbc);
        return bbc.length() >= 2 ? bbc.substring(bbc.length() - 2, bbc.length()) : "0" + bbc;
    }

    /**
     * 将单个char转换为Hex
     * @param x
     * @return
     */
    private static short charToHex(char x) {
        short result = 0;
        switch(x){
            case 'a':
            case 'A':
                result=10;break;
            case 'b':
            case 'B':
                result=11;break;
            case 'c':
            case 'C':
                result=12;break;
            case 'd':
            case 'D':
                result=13;break;
            case 'e':
            case 'E':
                result=14;break;
            case 'f':
            case 'F':
                result=15;break;
            default:result = (short) Character.getNumericValue(x);break;
        }
        return result;
    }
}
