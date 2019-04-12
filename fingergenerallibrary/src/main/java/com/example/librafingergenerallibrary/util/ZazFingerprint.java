package com.example.librafingergenerallibrary.util;

import android.app.Activity;
import android.util.Log;

import com.IDWORLD.LAPI;


public class ZazFingerprint {


    private static ZazFingerprint instance;

    public static ZazFingerprint getInstance() {
        if(instance==null){
            synchronized (ZazFingerprint.class){
                if(instance==null){
                    instance=new ZazFingerprint();
                }
            }
        }
        return instance;
    }

    private static int m_hDevice = 0;
    private static LAPI m_cLAPI;



    public static void init(final Activity activity) {
        new Thread(new Runnable() {
            public void run() {
                m_cLAPI = new LAPI(activity);
                openFinger();
            }
        }).start();
    }


    /**
     * 打开指纹模块
     *
     * @return
     */
    public static boolean openFinger() {
        if (m_hDevice == 0) {
            m_hDevice = m_cLAPI.OpenDeviceEx();
        }
        if (m_hDevice == 0) {
            Log.e("打开指纹模块","打开指纹模块失败");
            return false;
        } else {
            Log.e("打开指纹模块","打开指纹模块成功");
            return true;
        }
    }


    /**
     * 获取指纹图像
     */
    public byte[] getImage() {
        byte[] m_image = new byte[LAPI.WIDTH * LAPI.HEIGHT];
        int ret;
        ret = m_cLAPI.GetImage(m_hDevice, m_image);
        if (ret != LAPI.TRUE) {//Can't get image !
            return null;
        } else {
            return m_image;
        }
    }


    /**
     * 获取指纹质量
     *
     * @param m_image，指纹数据
     * @return
     */
    public int getImageQuality(byte[] m_image) {
        int qr;
        qr = m_cLAPI.GetImageQuality(m_hDevice, m_image);
        return qr;
    }


    /**
     * 获取指纹特征
     *
     * @param m_image
     * @return
     */
    public byte[] createTemp(byte[] m_image) {
        int ret;
        ret = m_cLAPI.IsPressFinger(m_hDevice, m_image);
        if (ret == 0) {
            return null;
        }
        byte[] m_itemplate_1 = new byte[LAPI.FPINFO_STD_MAX_SIZE];
        ret = m_cLAPI.CreateTemplate(m_hDevice, m_image, m_itemplate_1);
        if (ret == 0) {
            return null;
        } else {
            return m_itemplate_1;
        }
    }

    public int compareTemps(byte[] m_itemplate_1, byte[] m_itemplate_2) {
        int score;
        score = m_cLAPI.CompareTemplates(m_hDevice, m_itemplate_1, m_itemplate_2);
        return score;
    }


    public void close() {
        if (m_hDevice == 0) return;
        m_cLAPI.CloseDeviceEx(m_hDevice);
        m_hDevice = 0;
        Log.e("ZazFinger","关闭了");
    }


}
