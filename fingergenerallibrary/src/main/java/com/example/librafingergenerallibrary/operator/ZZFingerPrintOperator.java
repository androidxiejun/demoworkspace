package com.example.librafingergenerallibrary.operator;

import android.util.Log;


import com.example.librafingergenerallibrary.common.FingerConstants;
import com.example.librafingergenerallibrary.common.FingerPrintTemplate;
import com.example.librafingergenerallibrary.listenner.OnFingerOperateListener;
import com.example.librafingergenerallibrary.listenner.OnZZFingerOperateListener;
import com.example.librafingergenerallibrary.zfm.ZfmFingerConstants;

import org.zz.sm821.FingerUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by AndroidXJ on 2018/6/27.
 * 专用于中正指纹数据的处理
 */

public class ZZFingerPrintOperator implements IZZFingerPrintOperator {
    public static final String TAG = "MainActivity";
    private static ZZFingerPrintOperator mInstance;
    private int fingerTimeout;
    private static String port;
    private static int baudrate;
    private InputStream mInputStream;
    private OutputStream mOutputStream;

    public static ZZFingerPrintOperator getInstance() {
        if (null == mInstance) {
            synchronized (ZZFingerPrintOperator.class) {
                mInstance = new ZZFingerPrintOperator();
            }
        }
        return mInstance;
    }

    @Override
    public void init(String port, int baudrate, int fingerTimeout) throws IOException {
        Log.i(TAG, "初始化中正指纹数据-----");
        this.port = port;
        this.baudrate = baudrate;
        this.fingerTimeout = fingerTimeout;
    }

    @Override
    public boolean clearTemplate(int num) {
        return mInputStream != null && mOutputStream != null;
    }

    @Override
    public boolean clearAllTemplate() {
        return true;
    }

    /**
     * 中正指纹特征获取
     *
     * @param listener 回调接口
     */
    @Override
    public void enroll(OnZZFingerOperateListener listener, List<byte[]> fingerPrintList) {
        if (fingerPrintList != null && fingerPrintList.size() > 0) {
            int status = FingerUtils.compareFeacher(fingerPrintList, port, baudrate);
            Log.i("ZW-------","status-----"+status);
            if (status == 0) {
                //数据库有该指纹,不能新增
                listener.onFaild(FingerConstants.ERR_DUPLICATION_ID);
            } else {
                fingetEnroll(listener);
            }
        } else {
            fingetEnroll(listener);
        }


    }

    @Override
    public FingerPrintTemplate readTemplate(int templateId) {
        return null;
    }

    @Override
    public int writeTemplate(byte[] template) {
        return 0;
    }

    @Override
    public boolean isConnected() {
        return mInputStream != null && mOutputStream != null;
    }

    @Override
    public boolean testConnection() {
        return true;
    }

    /**
     * 对指纹进行验证
     *
     * @param listener   结果回调
     * @param fingerList 已保存的指纹集合
     */
    @Override
    public void identify(OnFingerOperateListener listener, List<byte[]> fingerList, List<Long> idList) {
        int status = FingerUtils.compareFeacher(fingerList, port, baudrate);
        if (status == 0) {
            listener.onSuccess(idList.get(FingerUtils.feacherIndex));
        } else {
            listener.onFaild(ZfmFingerConstants.ERR);
        }
    }

    private void fingetEnroll(OnZZFingerOperateListener listener) {
        //数据库没有该指纹，可以注册
        byte[] data = FingerUtils.getFeacher(port, baudrate);

        if (data != null) {
            listener.onSuccess(data);
        } else {
            listener.onFaild(FingerConstants.ERR_BAD_QUALITY);
        }
    }
}
