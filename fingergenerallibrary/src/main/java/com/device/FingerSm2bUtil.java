package com.device;

import android.util.Log;

import com.device.Device;

import java.util.List;

/**
 * @author HanPei
 * @date 2018/12/6  下午1:54
 */
public class FingerSm2bUtil {

    private static int TIME_OUT = 7 * 1000;
    private static int FEACHER_RESULT_OK = 0;


    /**
     * 获取指纹特征
     *
     * @param port     端口号
     * @param baudrate 波特率
     * @return 指纹模板
     */
    public static byte[] getFeature(String port, int baudrate) {
        byte[] fingerDate = new byte[172];
        byte[] msgByte = new byte[512];
        int result = Device.getFeature(port, baudrate, TIME_OUT, fingerDate, msgByte);
        if (result == FEACHER_RESULT_OK) {
            return fingerDate;
        } else {
            Log.e("getFeacherErro：", new String(msgByte));
        }
        return null;
    }


    /**
     * 获取指纹模板，3次的指纹特征合成一个指纹模板
     *
     * @param port     端口号
     * @param baudrate 波特率
     * @return 指纹模板
     */
    public static byte[] getFeatureTemplate(String port, int baudrate) {
        byte[] fingerDate = new byte[344];
        byte[] msgByte = new byte[512];
        int result = Device.getTemplate(port, baudrate, TIME_OUT, fingerDate, msgByte);
        Log.i("MainActivity", "result------" + result);
        if (result == FEACHER_RESULT_OK) {
            return fingerDate;
        } else {
            Log.e("getFeacherErro1：", new String(msgByte));
            Log.e("getFeacherErro2：", byteArrayToHexStr(msgByte));
        }
        return null;
    }


    /**
     * 指纹验证
     *
     * @param feacgherList 已保存的指纹模板
     * @param port         端口号
     * @param baudrate     波特率
     * @return 指纹膜版序号 ,-1为失败
     */
    public static int compareFeature(List<byte[]> feacgherList, String port, int baudrate) {
        byte[] newFeature = getFeature(port, baudrate);
        if (newFeature == null) {
            return -1;
        }
        for (int i = 0; i < feacgherList.size(); i++) {
            byte[] template = feacgherList.get(i);
            int result = Device.verifyFinger(new String(template), new String(newFeature), 3);
            if (result == FEACHER_RESULT_OK) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 判断指纹是否已存在
     *
     * @param feacgherList
     * @param newFeature
     * @param port
     * @param baudrate
     * @return
     */
    public static boolean isFeatureExist(List<byte[]> feacgherList, byte[] newFeature, String port, int baudrate) {
        for (int i = 0; i < feacgherList.size(); i++) {
            byte[] template = feacgherList.get(i);
            int result = Device.verifyFinger(new String(template), new String(newFeature), 3);
            if (result == FEACHER_RESULT_OK) {
                return true;
            }
        }
        return false;
    }


    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
