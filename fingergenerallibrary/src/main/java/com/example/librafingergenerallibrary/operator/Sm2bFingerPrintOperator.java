package com.example.librafingergenerallibrary.operator;


import com.device.Device;
import com.example.librafingergenerallibrary.common.FingerConstants;
import com.device.FingerSm2bUtil;
import com.example.librafingergenerallibrary.common.FingerPrintTemplate;
import com.example.librafingergenerallibrary.listenner.OnFingerOperateListener;
import com.example.librafingergenerallibrary.listenner.OnZZFingerOperateListener;
import com.example.librafingergenerallibrary.zfm.ZfmFingerConstants;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author HanPei
 * @date 2018/12/6  下午2:05
 */
public class Sm2bFingerPrintOperator implements IZZFingerPrintOperator {

    private static Sm2bFingerPrintOperator sInstance;
    private static String sPort;
    private static int sBaudrate;

    private Timer mTimer;

    private Sm2bFingerPrintOperator() {
    }

    public static Sm2bFingerPrintOperator getInstance() {
        if (sInstance == null) {
            sInstance = new Sm2bFingerPrintOperator();
        }
        return sInstance;
    }


    @Override
    public void init(String port, int baudrate, int fingerTimeout) throws IOException {
        sPort = port;
        sBaudrate = baudrate;
    }

    @Override
    public boolean clearTemplate(int num) {
        return true;
    }

    @Override
    public boolean clearAllTemplate() {
        return true;
    }

    /**
     * 注册指纹
     *
     * @param listener        回调接口
     * @param fingerPrintList 指纹模板集合
     */
    @Override
    public void enroll(final OnZZFingerOperateListener listener, List<byte[]> fingerPrintList) {
        byte[] data = FingerSm2bUtil.getFeatureTemplate(sPort, sBaudrate);
//        mTimer.cancel();
        if (data == null) {
            listener.onFaild(ZfmFingerConstants.ERR);
            return;
        }
        if (fingerPrintList == null || fingerPrintList.size() == 0) {
            listener.onSuccess(data);
            return;
        }
        if (FingerSm2bUtil.isFeatureExist(fingerPrintList, data, sPort, sBaudrate)) {
            listener.onFaild(FingerConstants.ERR_DUPLICATION_ID);
        } else {
            listener.onSuccess(data);
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
        return true;
    }

    @Override
    public boolean testConnection() {
        return true;
    }

    /**
     * 指纹验证
     *
     * @param listener        结果回调
     * @param fingerPrintList 指纹集合
     * @param userId          用户ID
     */
    @Override
    public void identify(final OnFingerOperateListener listener, List<byte[]> fingerPrintList, List<Long> userId) {
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(FingerPrintOperator.hasClickOtherWay){
                    Device.cancel();
                }
            }
        },0,100);
        int status = FingerSm2bUtil.compareFeature(fingerPrintList, sPort, sBaudrate);
        mTimer.cancel();
        if (status != -1) {
            listener.onSuccess(userId.get(status));
        } else {
            listener.onFaild(ZfmFingerConstants.ERR);
        }
    }

}
