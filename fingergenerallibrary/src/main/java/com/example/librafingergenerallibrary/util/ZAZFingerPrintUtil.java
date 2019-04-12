package com.example.librafingergenerallibrary.util;

import android.util.Log;

import com.example.librafingergenerallibrary.listenner.FingerCompareListener;
import com.example.librafingergenerallibrary.listenner.FingerGetListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ZAZFingerPrintUtil {

    public static Long TIME_OUT = 5000l;
    public static int LIKE_LEVEL = 85;
    public static int QUALITY = 75;

    /*
     * 获取指纹图像
     *
     * @param listener*/

    public static synchronized void getFingerPrint(final FingerGetListener listener) {
        final Long startTime = System.currentTimeMillis();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                byte[] fingerData = ZazFingerprint.getInstance().getImage();
                if (fingerData != null) {
                    int quality = ZazFingerprint.getInstance().getImageQuality(fingerData);
                    Log.e("获取指纹图像", "指纹图像质量为：" + quality);
                    byte[] finger=ZazFingerprint.getInstance().createTemp(fingerData);
                    if (finger!=null&&quality > QUALITY) {
                        listener.getZazFinagerOk(ZazFingerprint.getInstance().createTemp(fingerData));
                        this.cancel();
                    } else if (finger!=null &&quality>55&& quality < QUALITY) {
                        listener.getZazFinagerFail("指纹质量低");
                        this.cancel();
                    }
                } else {
                    if (System.currentTimeMillis() - startTime > TIME_OUT) {
                        listener.getZazFinagerFail("获取指纹信息超时");
                        this.cancel();
                    }
                }
            }
        }, 100, 100);
    }


    /*
     * 指纹对比
     *
     * @param listener
     * @param finger
     * @param fingers
     */

    public static synchronized void compareFingerPrint(final byte[] finger, final List<byte[]> fingers, final FingerCompareListener listener) {
        if (finger == null || fingers == null) {
            listener.compareFail();
            return;
        }
        final ZazFingerprint zazFingerprint = ZazFingerprint.getInstance();
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < fingers.size(); i++) {
                    int like = zazFingerprint.compareTemps(finger, fingers.get(i));
                    Log.e("指纹对比", "指纹相似度为：" + like);
                    if (like > LIKE_LEVEL) {
                        listener.compareOk(i);
                        return;
                    }
                }
                listener.compareFail();
            }
        }).start();
    }


}
