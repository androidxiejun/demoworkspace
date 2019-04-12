package org.zz.sm821;

import android.util.Log;

import org.zz.api.MXComModuleAPI;

import java.util.List;

/**
 * Created by AndroidXJ on 2018/6/26.
 */

public class FingerUtils {
    public static final String TAG = "MainActivity";
    // Jni库
    private static MXComModuleAPI devDriver;
    private static byte[] feacherData = new byte[512];
    private static byte[] feacherData2 = new byte[512];
    private static int m_nTimeOut = 10 * 1000;
    private static int m_iImageArea = 50;
    private static boolean identitySuc = false;
    public static int feacherIndex;


    /**
     * 将获取到的指纹特征数据返回给调用者
     *
     * @return
     */
    public static byte[] getFeacher(String port, int baudrate) {
        if (null == devDriver) {
            devDriver = new MXComModuleAPI();
        }
        int status = GetTz(port, baudrate);
        if (status != 0) {
            return null;
        }
        return feacherData;
    }


    /**
     * 获取指纹特征
     */
    public static int GetTz(String port, int baudrate) {
        int nRet = 0;
        String strDevName = port;
        int iBaudRate = baudrate;
        nRet = devDriver.zzExtractFingerprint(strDevName, iBaudRate,
                feacherData, m_nTimeOut, m_iImageArea);
        if (nRet != 0) {
            Log.i("MainActivity", "获取特征失败----");
        } else {
            Log.i("MainActivity", "获取特征成功----");
        }
        devDriver.zzCancel();
        return nRet;
    }


    /**
     * 对比指纹特征
     *
     * @param feacgherList
     * @param port
     * @param baudrate
     * @return
     */
    public static int compareFeacher(List<byte[]> feacgherList, String port, int baudrate) {
        if (null == devDriver) {
            devDriver = new MXComModuleAPI();
        }
        int nRet = 0;
        String strDevName = port;
        int iBaudRate = baudrate;
        nRet = devDriver.zzExtractFingerprint(strDevName, iBaudRate,
                feacherData2, m_nTimeOut, m_iImageArea);
        if (nRet != 0) {
            return nRet;
        }
        int index = feacgherList.size();
        for (int i = 0; i < index; i++) {
            nRet = devDriver.zzMatchFingerprint(strDevName, iBaudRate,
                    feacgherList.get(i), feacherData2);
            if (nRet == 0) {
                feacherIndex = i;
                break;
            }
        }
        devDriver.zzCancel();
        return nRet;
    }
}
