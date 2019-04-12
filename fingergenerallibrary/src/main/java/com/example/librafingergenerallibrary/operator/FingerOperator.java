package com.example.librafingergenerallibrary.operator;

import android.app.Activity;
import android.serialport.SerialPort;

import com.example.librafingergenerallibrary.common.FingerPrintTemplate;
import com.example.librafingergenerallibrary.listenner.FingerCompareListener;
import com.example.librafingergenerallibrary.listenner.FingerOperateCallback;
import com.example.librafingergenerallibrary.listenner.FingerGetListener;
import com.example.librafingergenerallibrary.listenner.OnFingerOperateListener;
import com.example.librafingergenerallibrary.listenner.OnZZFingerOperateListener;
import com.example.librafingergenerallibrary.util.ZAZFingerPrintUtil;
import com.example.librafingergenerallibrary.util.ZazFingerprint;
import com.example.librafingergenerallibrary.zfm.ZfmFingerConstants;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by AndroidXJ on 2019/4/1.
 */

public class FingerOperator {
    public static final int IDENTITY_TYPE_ZFM = 0x00;
    public static final int IDENTITY_TYPE_CAMA = 0x01;
    public static final int IDENTITY_TYPE_ZZ = 0x02;
    public static final int IDENTITY_TYPE_ZIANG = 0x03;
    public static final int IDENTITY_TYPE_ZZ_SM2B = 0x04;

    private static  int fingerType;

    /**
     * 指纹
     */
    private IFingerPrintOperator mFingerPrintOperator;
    private IZZFingerPrintOperator mZZFingerPrintOperator;


    private static FingerOperator mInstance;

    public static FingerOperator getInstance() {
        if (mInstance == null) {
            synchronized (FingerOperator.class) {
                if (mInstance == null) {
                    mInstance = new FingerOperator();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加指纹
     * 根据id获取用户信息，添加指纹并添加指纹信息和修改用户信息
     *
     * @param userId                    用户id
     */
    public void insertFingerPrintForUser(final Long userId, FingerOperateCallback listenner, List<byte[]> fingerPrintList) {
        switch (fingerType) {
            case IDENTITY_TYPE_ZFM:
            case IDENTITY_TYPE_CAMA:
                normalFingerEnroll(listenner);
                break;
            case IDENTITY_TYPE_ZZ_SM2B:
            case IDENTITY_TYPE_ZZ:
                zzFingerEnroll(userId, listenner, fingerPrintList);
                break;
            case IDENTITY_TYPE_ZIANG:
                ziAngFingerEnroll(userId, listenner);
                break;
        }

    }


    /**
     * 添加指纹
     * 从服务器获取指纹信息并插入
     *
     */
    public void insertFingerPrintForUserByServer(byte[] feature,int oldTemplateId, final FingerOperateCallback listenner) {
        switch (fingerType) {
            case IDENTITY_TYPE_ZFM:
            case IDENTITY_TYPE_CAMA:
                if (oldTemplateId != 0) {
                    mFingerPrintOperator.clearTemplate(oldTemplateId);
                }
                int templateId = mFingerPrintOperator.writeTemplate(feature);
                listenner.success(templateId, null);
                break;
            case IDENTITY_TYPE_ZZ_SM2B:
            case IDENTITY_TYPE_ZZ:
            case IDENTITY_TYPE_ZIANG:
                listenner.success(0, null);
                break;
        }

    }

    /**
     * 删除指纹
     * 根据id获取用户信息，根据手指位置获取指纹信息，回调
     */
    public void deleteFingerPrintForUser(int templateId, FingerOperateCallback listenner) {
        switch (fingerType) {
            case IDENTITY_TYPE_ZFM:
            case IDENTITY_TYPE_CAMA:
                boolean isClear = mFingerPrintOperator.clearTemplate(templateId);
                listenner.success(isClear);
                break;
            case IDENTITY_TYPE_ZZ:
            case IDENTITY_TYPE_ZIANG:
            case IDENTITY_TYPE_ZZ_SM2B:
                listenner.success(true);
                break;
        }
    }


    /**
     * 修改指纹
     * 根据id获取用户信息，并根据指纹位置添加指纹并修改指纹信息，假如修改失败删除新录入指纹并返回失败
     *
     * @param id                        用户id
     */
    public void updateFingerPrintForUser(final Long id,int templateId, List<byte[]> dataList, FingerOperateCallback listenner) {
        switch (fingerType) {
            case IDENTITY_TYPE_ZFM:
            case IDENTITY_TYPE_CAMA:
                normalUpdateFinger(templateId, listenner);
                break;
            case IDENTITY_TYPE_ZZ:
            case IDENTITY_TYPE_ZZ_SM2B:
            case IDENTITY_TYPE_ZIANG:
                zzUpdateFinger(id, dataList, listenner);
                break;

        }
    }

    /**
     * 根据id删除指纹
     *
     * @param templateId
     * @return
     */
    public boolean clearTemplate(Integer templateId) {
        try {
            return mFingerPrintOperator.clearTemplate(templateId);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证指纹机里是否存在改指纹
     *
     * @param callback 回调
     */
    public void identify(final FingerOperateCallback callback, final List<byte[]> dataList, final List<Long> idList) {
        switch (fingerType) {
            case IDENTITY_TYPE_ZFM:
            case IDENTITY_TYPE_CAMA:
                mFingerPrintOperator.identify(new OnFingerOperateListener() {
                    @Override
                    public void onSuccess(long id) {
                        callback.success(id,null);
                    }

                    @Override
                    public void onFaild(int errNo) {
                        callback.onFail(errNo);
                    }

                    @Override
                    public void onMsg(int msgNo) {
                        callback.onMsg(msgNo);
                    }
                });
                break;
            case IDENTITY_TYPE_ZZ:
                if (null == mZZFingerPrintOperator) {
                    mZZFingerPrintOperator = ZZFingerPrintOperator.getInstance();
                }
                startVerify(callback, dataList, idList);
                break;
            case IDENTITY_TYPE_ZZ_SM2B:
                if (null == mZZFingerPrintOperator) {
                    mZZFingerPrintOperator = Sm2bFingerPrintOperator.getInstance();
                }
                startVerify(callback, dataList, idList);
                break;
            case IDENTITY_TYPE_ZIANG:
                ZAZFingerPrintUtil.getFingerPrint(new FingerGetListener() {
                    @Override
                    public void getZazFinagerOk(byte[] fingerData) {
                        ZAZFingerPrintUtil.compareFingerPrint(fingerData, dataList, new FingerCompareListener() {
                            @Override
                            public void compareOk(int position) {
                                callback.success(idList.get(position),null);
                            }

                            @Override
                            public void compareFail() {
                                callback.onFail(ZfmFingerConstants.ERR);
                            }
                        });
                    }

                    @Override
                    public void getZazFinagerFail(String msg) {
                        callback.onFail(ZfmFingerConstants.ERR);
                    }
                });

                break;
        }
    }

    /**
     * 清空所有指纹信息
     */
    public void clearAllTemplate(int type) {
        switch (type) {
            case IDENTITY_TYPE_ZFM:
            case IDENTITY_TYPE_CAMA:
                if (mFingerPrintOperator != null)
                    mFingerPrintOperator.clearAllTemplate();
                break;
            case IDENTITY_TYPE_ZIANG:
            case IDENTITY_TYPE_ZZ:
            case IDENTITY_TYPE_ZZ_SM2B:
                break;
        }
    }

    /**
     * 初始化指纹
     */
    public void initFinger(boolean isNeedConfig, Activity activity, int type, String port, int baudrate) {
        fingerType=type;
        switch (type) {
            case IDENTITY_TYPE_ZFM:
            case IDENTITY_TYPE_CAMA:
                init(port, baudrate, type);
//                if (!testConnection()) {
//                    EventBus.getDefault().post(new ShowToastEvent("指纹连接出错"));
//                }
                break;
            case IDENTITY_TYPE_ZZ:
            case IDENTITY_TYPE_ZZ_SM2B:
                init(port, baudrate, type);
                break;
            case IDENTITY_TYPE_ZIANG:
                ZazFingerprint.init(activity);
                break;
        }

        if (isNeedConfig) {
            //清空指纹模块
            clearAllTemplate(type);
        }
    }

    /**
     * 初始化
     */
    public void init(String port, int baudrate, int type) {
        switch (type) {
            case IDENTITY_TYPE_ZFM:
                mFingerPrintOperator = ZfmFingerPrintOperator.getInstance();
                break;
            case IDENTITY_TYPE_CAMA:
                mFingerPrintOperator = FingerPrintOperator.getInstance();
                break;
            case IDENTITY_TYPE_ZZ:
                mZZFingerPrintOperator = ZZFingerPrintOperator.getInstance();
                break;
            case IDENTITY_TYPE_ZIANG:

                break;
            case IDENTITY_TYPE_ZZ_SM2B:
                mZZFingerPrintOperator = Sm2bFingerPrintOperator.getInstance();
                break;
            default:
                mFingerPrintOperator = ZfmFingerPrintOperator.getInstance();
                break;
        }
        if (mFingerPrintOperator != null) {
            if (!mFingerPrintOperator.isConnected()) {
                try {
                    SerialPort serialPort = new SerialPort(new File(port), baudrate);
                    switch (type) {
                        case IDENTITY_TYPE_ZFM:
                            mFingerPrintOperator.init(serialPort.getInputStream(), serialPort.getOutputStream(), 11000);
                            break;
                        case IDENTITY_TYPE_CAMA:
                            mFingerPrintOperator.init(serialPort.getInputStream(), serialPort.getOutputStream(), 11);
                            break;
                        case IDENTITY_TYPE_ZZ_SM2B:
                        case IDENTITY_TYPE_ZZ:
                            mZZFingerPrintOperator.init(port, baudrate, 1500);
                            break;
                        case IDENTITY_TYPE_ZIANG:

                            break;
                        default:
                            mFingerPrintOperator.init(serialPort.getInputStream(), serialPort.getOutputStream(), 11000);
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                mZZFingerPrintOperator.init(port, baudrate, 1500);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 最初两种指纹注册方法
     */
    private void normalFingerEnroll(final FingerOperateCallback callback) {
        mFingerPrintOperator.enroll(new OnFingerOperateListener() {
            @Override
            public void onSuccess(long id) {
                FingerPrintTemplate template = mFingerPrintOperator.readTemplate((int) id);
                if (template == null) {
                    callback.onFail(0);
                    return;
                }
                callback.success(template.getId(), template.getTemplateData());
            }

            @Override
            public void onFaild(int errNo) {
                callback.onFail(errNo);

            }

            @Override
            public void onMsg(final int msgNo) {
                callback.onMsg(msgNo);
            }


        });
    }

    /**
     * 中正指纹注册的方法
     *
     * @param userId
     */
    private void zzFingerEnroll(final Long userId,final FingerOperateCallback listenner
            , List<byte[]> fingerPrintList) {
        mZZFingerPrintOperator.enroll(new OnZZFingerOperateListener() {
            @Override
            public void onSuccess(byte[] data) {
                if (null == data) {
                    listenner.onFail(0);
                }
                listenner.success(userId, data);


            }

            @Override
            public void onFaild(int errNo) {
                listenner.onFail(errNo);
            }

            @Override
            public void onMsg(int msgNo) {
                listenner.onMsg(msgNo);
            }
        }, fingerPrintList);
    }

    /**
     * 指昂指纹注册的方法
     *
     * @param userId
     */
    private void ziAngFingerEnroll(final Long userId, final FingerOperateCallback listenner) {
        ZAZFingerPrintUtil.getFingerPrint(new FingerGetListener() {
            @Override
            public void getZazFinagerOk(byte[] data) {
                if (null == data) {
                    listenner.onFail(0);
                }
                long id = userId;
                listenner.success(id, data);
            }

            @Override
            public void getZazFinagerFail(String errNo) {
//                EventBus.getDefault().post(new ShowToastEvent(errNo));
                listenner.onFail(Integer.valueOf(errNo));
            }
        });
    }

    private void startVerify(final FingerOperateCallback callback, List<byte[]> dataList, List<Long> idList) {
        mZZFingerPrintOperator.identify(new OnFingerOperateListener() {
            @Override
            public void onSuccess(long id) {
                callback.success(id,null);
            }

            @Override
            public void onFaild(int errNo) {
                callback.onFail(errNo);
            }

            @Override
            public void onMsg(int msgNo) {
                callback.onMsg(msgNo);
            }
        }, dataList, idList);
    }

    /**
     * 终端更新指纹操作
     */
    private void normalUpdateFinger(final int templateId, final FingerOperateCallback listenner) {
        try {
            //假如指纹模块id不为0则进行修改指纹操作
            if (templateId != 0) {
                final int finalTemplateId = templateId;
                mFingerPrintOperator.enroll(new OnFingerOperateListener() {
                    @Override
                    public void onSuccess(long id) {
                        //录入指纹之后进行删除原指纹操作
                        boolean isClear = mFingerPrintOperator.clearTemplate(finalTemplateId);
                        if (isClear) {
                            FingerPrintTemplate template = mFingerPrintOperator.readTemplate((int) id);
                            if (template != null) {
                                listenner.success(template.getId(), template.getTemplateData());
                            } else {
                                mFingerPrintOperator.clearTemplate((int) id);
                                listenner.onFail(-1);
                            }
                        } else {
                            mFingerPrintOperator.clearTemplate((int) id);
                            listenner.onFail(-1);
                        }
                    }

                    @Override
                    public void onFaild(final int errNo) {
                        listenner.onFail(errNo);
                    }

                    @Override
                    public void onMsg(final int msgNo) {
                        listenner.onMsg(msgNo);
                    }
                });
            } else {
                listenner.onMsg(0);
            }
        } catch (Exception e) {
            listenner.onFail(0);
        }

    }

    private void zzUpdateFinger(final Long id, List<byte[]> dataList, final FingerOperateCallback listenner) {
        //在开始注册前先检查是否已经又该指纹
        mZZFingerPrintOperator.enroll(new OnZZFingerOperateListener() {
            @Override
            public void onSuccess(byte[] data) {
                listenner.success(id, data);
            }


            @Override
            public void onFaild(int errNo) {
                listenner.onFail(errNo);
            }

            @Override
            public void onMsg(int msgNo) {
                listenner.onMsg(msgNo);
            }
        }, dataList);
    }

    /**
     * 是否连接成功
     */
    public boolean testConnection() {
        try {
            return mFingerPrintOperator.testConnection();
        } catch (Exception e) {
            return false;
        }
    }
}
