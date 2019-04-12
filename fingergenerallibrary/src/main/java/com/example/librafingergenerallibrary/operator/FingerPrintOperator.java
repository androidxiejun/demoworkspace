package com.example.librafingergenerallibrary.operator;

import android.util.Log;

import com.example.librafingergenerallibrary.common.BrokenInfo;
import com.example.librafingergenerallibrary.common.CommandDataFrame;
import com.example.librafingergenerallibrary.common.CommandFrame;
import com.example.librafingergenerallibrary.common.FingerConstants;
import com.example.librafingergenerallibrary.common.FingerPrintTemplate;
import com.example.librafingergenerallibrary.common.Head;
import com.example.librafingergenerallibrary.common.ResponseFrame;
import com.example.librafingergenerallibrary.listenner.OnFingerOperateListener;
import com.example.librafingergenerallibrary.util.BaseUtil;
import com.example.librafingergenerallibrary.util.RadixUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 业务处理，向外提供方法
 * 1.初始化
 * 2.比对指纹
 * 3.指纹识别
 * 4.删除指纹
 * 5.指纹录入
 * 6.获取指纹编号
 * 7.清空指纹
 * 8.取消操作
 * 9.开启连续指纹识别
 * 10.获取指纹特征
 * 11.写入指纹特征
 * 12.设置超时时间
 * 硬件数据通讯
 */
public class FingerPrintOperator implements IFingerPrintOperator {
    private static final String TAG = "FingerPrintOperator";
    private static FingerPrintOperator mInstance;
    private int fingerTimeout;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    public static boolean hasClickOtherWay = false;

    /**
     * 是否已取消
     */
    private boolean cancel = false;

    /**
     * 是否已超时
     */
    public static boolean isSerialPortTimeout;
    /**
     * 效应定时器
     */
    private Timer mSerialPortTimer = new Timer();
    private TimerTask mSerialPortTimerTask;

    private FingerPrintOperator() {
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static FingerPrintOperator getInstance() {
        if (mInstance == null) {
            mInstance = new FingerPrintOperator();
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @param inputStream   端口输入流
     * @param outputStream  端口输出流
     * @param fingerTimeout 指纹超时时间，单位：秒（不得小于1S）
     * @throws IOException
     */
    @Override
    public void init(InputStream inputStream, OutputStream outputStream, int fingerTimeout) throws IOException {
        mInputStream = inputStream;
        mOutputStream = outputStream;
        this.fingerTimeout = fingerTimeout;
        setFingerTimeout(fingerTimeout - 1);
    }

    /**
     * 是否已经连接
     */
    @Override
    public boolean isConnected() {
        return mInputStream != null && mOutputStream != null;
    }

    /**
     * 比对指纹
     *
     * @param id       要比对的模板id
     * @param listener 响应监听
     */
    public void verify(final int id, final OnFingerOperateListener listener) {
        operateMoreResponse(FingerConstants.CMD_VERIFY, id, listener);
    }

    /**
     * 指纹识别
     *
     * @param listener
     */
    @Override
    public void identify(OnFingerOperateListener listener) {
        operateMoreResponse(FingerConstants.CMD_IDENTIFY, null, listener);
    }

    /**
     * 测试指纹连接
     *
     * @return
     */
    @Override
    public boolean testConnection() {
        ResponseFrame responseFrame = operateOneResponse(FingerConstants.CMD_TEST_CONNECTION);
        return checkResponseSuccess(responseFrame);
    }

    /**
     * 指纹录入
     *
     * @param listener
     */
    @Override
    public void enroll(final OnFingerOperateListener listener) {
        synchronized (this) {
            Integer id = getEmptyId();
            if (id != null && id != 0) {
                operateMoreResponse(FingerConstants.CMD_ENROLL, id, listener);
            } else {
                listener.onFaild(FingerConstants.ERR_INVALID_TMPL_NO);
            }
        }
    }

    /**
     * 指纹录入
     *
     * @param listener
     */
    public void enrollOneTime(final OnFingerOperateListener listener) {
        synchronized (this) {
            Integer id = getEmptyId();
            if (id != null && id != 0) {
                operateMoreResponse(FingerConstants.CMD_ENROLL_ONETIME, id, listener);
            } else {
                listener.onFaild(FingerConstants.ERR_INVALID_TMPL_NO);
            }
        }
    }

    /**
     * 清除指定指纹
     *
     * @param id
     * @return
     */
    @Override
    public boolean clearTemplate(int id) {
        ResponseFrame responseFrame = operateOneResponse(FingerConstants.CMD_CLEAR_TEMPLATE, id);
        if (checkResponseSuccess(responseFrame)) {
            return true;
        }
        if (responseFrame == null || responseFrame.getData() == null || responseFrame.getData().length == 0) {
            return false;
        }
        if (responseFrame.getData()[0] == FingerConstants.ERR_TMPL_EMPTY) {
            return true;
        }
        return false;
    }

    /**
     * 清除所有指纹
     *
     * @return
     */
    @Override
    public boolean clearAllTemplate() {
        ResponseFrame responseFrame = operateOneResponse(FingerConstants.CMD_CLEAR_ALL_TEMPLATE);
        if (checkResponseSuccess(responseFrame)) {
            return true;
        }
        return false;
    }

    /**
     * 获取可注册指纹模块编号
     *
     * @return null：获取失败时
     */
    public Integer getEmptyId() {
        return operateGetInt(FingerConstants.CMD_GET_EMPTY_ID);
    }

    /**
     * 获取错误指纹信息
     *
     * @return
     */
    public BrokenInfo getBrokenTemplate() {
        ResponseFrame responseFrame = operateOneResponse(FingerConstants.CMD_GET_EMPTY_ID);
        if (!checkResponseSuccess(responseFrame)) {
            return null;
        }
        byte[] data = responseFrame.getData();
        byte[] total = new byte[2];
        total[0] = data[0];
        total[1] = data[1];
        byte[] first = new byte[2];
        first[0] = data[2];
        first[1] = data[3];

        return new BrokenInfo(RadixUtil.byte2int(total), RadixUtil.byte2int(first));
    }

    /**
     * 取消操作
     *
     * @return
     */
    public boolean fpCancel() {
        cancel = true;
        ResponseFrame responseFrame = operateOneResponse(FingerConstants.CMD_CANCEL);
        return checkResponseSuccess(responseFrame);
    }

    /**
     * 开启连续识别
     *
     * @param listener
     */
    public void startIdentifyFree(final OnFingerOperateListener listener) {
        final FingerPrintOperator my = this;
        new Thread() {
            @Override
            public void run() {
                synchronized (my) {
                    cancel = false;
                    sendCommand(FingerConstants.CMD_IDENTIFY_FREE);
                    ResponseFrame responseFrame;
                    while (!cancel) {
                        responseFrame = readResponse();
                        if (responseFrame == null) {
                            continue;
                        }
                        if (Arrays.equals(FingerConstants.RET_FAIL, responseFrame.getRet())) {
                            final ResponseFrame finalResponseFrame = responseFrame;
                            new Thread() {
                                @Override
                                public void run() {
                                    listener.onFaild(getIntData(finalResponseFrame));
                                }
                            }.start();
                        } else {
                            //提示消息
                            if (checkIsMsgGd(responseFrame)) {
                                final ResponseFrame finalResponseFrame = responseFrame;
                                new Thread() {
                                    @Override
                                    public void run() {
                                        listener.onMsg(getIntData(finalResponseFrame));
                                    }
                                }.start();
                            } else {
                                final ResponseFrame finalResponseFrame = responseFrame;
                                new Thread() {
                                    @Override
                                    public void run() {
                                        listener.onSuccess(getIntData(finalResponseFrame));
                                    }
                                }.start();
                            }
                        }
                    }
                    //为避免多线程调用导致指纹头死机，释放锁之前先发送fpCancel指令
                    fpCancel();
                }
            }
        }.start();
    }

    /**
     * 获取指纹特征
     *
     * @param templateId 指纹ID
     * @return
     */
    @Override
    public FingerPrintTemplate readTemplate(int templateId) {
        ResponseFrame responseFrame = operateReadData(FingerConstants.CMD_READ_TEMPLATE, templateId);
        if (responseFrame == null) {
            return null;
        }
        return formateTemplate(responseFrame);
    }

    /**
     * 写入指纹特征
     *
     * @param template
     * @return
     */
    @Override
    public int writeTemplate(byte[] template) {
        synchronized (this) {
            int id = getEmptyId();
            if (id == 0) {
                return 0;
            }
            byte[] idAndTemplate = new byte[template.length + 2];
            byte[] idBytes = RadixUtil.shortToByte((short) id);
            idAndTemplate[0] = idBytes[0];
            idAndTemplate[1] = idBytes[1];
            System.arraycopy(template, 0, idAndTemplate, 2, template.length);
            if (!operateSendData(FingerConstants.CMD_WRITE_TEMPLATE, idAndTemplate)) {
                return 0;
            }
            return id;
        }
    }

    /**
     * 设置安全等级
     *
     * @param value
     * @return
     */
    public boolean setSecurityLevel(int value) {
        return operateSetInt(FingerConstants.CMD_SET_SECURITY_LEVEL, value);
    }

    /**
     * 获取安全等级
     *
     * @return
     */
    public Integer getSecurityLevel() {
        return operateGetInt(FingerConstants.CMD_GET_SECURITY_LEVEL);
    }

    /**
     * 设置timeout值
     *
     * @param value
     * @return
     */
    public boolean setFingerTimeout(int value) {
        return operateSetInt(FingerConstants.CMD_SET_FINGER_TIMEOUT, value);
    }

    /**
     * 获取timeout值
     *
     * @return
     */
    public Integer getFingerTimeout() {
        return operateGetInt(FingerConstants.CMD_GET_FINGER_TIMEOUT);
    }

    /**
     * 设置设备id
     *
     * @param id
     * @return
     */
    public boolean setDeviceId(int id) {
        return operateSetInt(FingerConstants.CMD_SET_DEVICE_ID, id);
    }

    /**
     * 获取设备id
     *
     * @return
     */
    public Integer getDeviceId() {
        return operateGetInt(FingerConstants.CMD_GET_DEVICE_ID);
    }

    /**
     * 获取固件版本
     *
     * @return
     */
    public Integer getFwVersion() {
        return operateGetInt(FingerConstants.CMD_GET_FW_VERSION);
    }

    /**
     * 检测是否有指纹输入
     *
     * @return
     */
    public boolean fingerDetect() {
        return operateGetInt(FingerConstants.CMD_FINGER_DETECT) == 1;
    }

    /**
     * 设置波特率
     *
     * @param baudrateNo 波特率代号，具体值参见FingerConstants中的定义
     * @return
     */
    public boolean setBaudrate(int baudrateNo) {
        return operateSetInt(FingerConstants.CMD_SET_BAUDRATE, baudrateNo);
    }

    /**
     * 设置指纹重复检测
     *
     * @param check
     * @return
     */
    public boolean setDuplicationCheck(boolean check) {
        int intCheck = check ? 1 : 0;
        return operateSetInt(FingerConstants.CMD_SET_DUPLICATION_CHECK, intCheck);
    }

    /**
     * 获取指纹重复检查设置情况
     *
     * @return
     */
    public boolean getDuplicationCheck() {
        return operateGetInt(FingerConstants.CMD_GET_DUPLICATION_CHECK) == 1;
    }

    /**
     * 进入待机模式
     *
     * @return
     */
    public boolean enterStandbyMode() {
        ResponseFrame responseFrame = operateOneResponse(FingerConstants.CMD_ENTER_STADBY_MODE);
        return checkResponseSuccess(responseFrame);
    }

    /**
     * 采集指纹并将特征存放在内存中
     *
     * @param listener
     */
    public void enrollAndStoreInRam(OnFingerOperateListener listener) {
        operateMoreResponse(FingerConstants.CMD_ENROLL_AND_STORE_IN_RAM, null, listener);
    }

    /**
     * 获取内存中的指纹特征数据
     *
     * @return
     */
    public FingerPrintTemplate getEnrollData() {
        ResponseFrame responseFrame = operateReadData(FingerConstants.CMD_GET_ENROLL_DATA, null);
        return formateTemplate(responseFrame);
    }

    /**
     * 获取图像并采集指纹特征值上传
     *
     * @return
     */
    public FingerPrintTemplate getFeatureDataOfCapturedFp() {
        ResponseFrame responseFrame = operateReadData(FingerConstants.CMD_GET_FEATURE_DATA_OF_CAPTURED_FP, null);
        return formateTemplate(responseFrame);
    }

    /**
     * 下载一枚指纹与采集的指纹进行比对
     *
     * @param template
     * @return
     */
    public boolean verifyDownloadedFeatureWithCapturedFp(byte[] template) {
        return operateSendData(FingerConstants.CMD_VERIFY_DOWNLOADED_FEATURE_WIDTH_CAPTURED_FP, template);
    }

    /**
     * 下载二枚指纹与采集的指纹进行比对
     *
     * @param template1 模板1
     * @param template2 模板2
     * @return
     */
    public boolean identifyDownloadedFeatureWithCapturedFp(byte[] template1, byte[] template2) {
        byte[] idAndTemplate1 = new byte[template1.length + 2];
        idAndTemplate1[0] = 1;
        idAndTemplate1[1] = 0;
        System.arraycopy(template1, 0, idAndTemplate1, 2, template1.length);
        if (!operateSendData(FingerConstants.CMD_IDENTIFY_DOWNLOADED_FEATURE_WIDTH_CAPTURED_FP, idAndTemplate1)) {
            return false;
        }
        byte[] idAndTemplate2 = new byte[template2.length + 2];
        idAndTemplate2[0] = 2;
        idAndTemplate2[1] = 0;
        System.arraycopy(template2, 0, idAndTemplate2, 2, template2.length);
        return operateSendData(FingerConstants.CMD_IDENTIFY_DOWNLOADED_FEATURE_WIDTH_CAPTURED_FP, idAndTemplate2);
    }

    /**
     * 获取设备名称
     *
     * @return
     */
    public String getDeviceName() {
        ResponseFrame responseFrame = operateOneResponse(FingerConstants.CMD_GET_DEVICE_NAME);
        if (checkResponseSuccess(responseFrame)) {
            return new String(responseFrame.getData());
        }
        return null;
    }

    /**
     * 控制led
     *
     * @param on
     * @return
     */
    public boolean controlLed(boolean on) {
        int onInt = on ? 1 : 0;
        return operateSetInt(FingerConstants.CMD_SENSOR_LED_CONTROL, onInt);
    }

    /**
     * 设置密码
     *
     * @param password 密码
     * @return
     */
    public boolean setDevicePassword(byte[] password) {
        ResponseFrame responseFrame = operateOneResponse(FingerConstants.CMD_SET_DEVICE_PASSWORD, password);
        return checkResponseSuccess(responseFrame);
    }

    /**
     * 校验密码
     *
     * @param password 密码
     * @return
     */
    public boolean verifyDevicePassword(byte[] password) {
        ResponseFrame responseFrame = operateOneResponse(FingerConstants.CMD_VERIFY_DEVICE_PASSWORD, password);
        return checkResponseSuccess(responseFrame);
    }

    /**
     * 获取已登记指纹总数
     *
     * @return
     */
    public int getEnrollCount() {
        return operateGetInt(FingerConstants.CMD_GET_ENROLL_COUNT);
    }

    /**
     * 更新模板
     *
     * @param id       原模板id
     * @param listener
     */
    public void changeTemplate(int id, OnFingerOperateListener listener) {
        operateMoreResponse(FingerConstants.CMD_CHANGE_TEMPLATE, id, listener);
    }

    /**
     * 设置工作模式
     *
     * @param mode 新的工作模式
     * @return
     */
    public boolean setOperationMode(int mode) {
        return operateSetInt(FingerConstants.CMD_SET_OPERATION_MODE, mode);
    }

    /**
     * 获取工作模式
     *
     * @return
     */
    public int getOperationMode() {
        return operateGetInt(FingerConstants.CMD_GET_OPERATION_MODE);
    }

    /**
     * 发送指令
     *
     * @param cmd      指令
     * @param intValue int型数据
     * @return
     */
    private boolean sendCommand(byte[] cmd, int intValue) {
        return sendCommand(cmd, RadixUtil.shortToByte((short) intValue));
    }

    /**
     * 发送指令
     *
     * @param cmd 指令
     * @return
     */
    private boolean sendCommand(byte[] cmd) {
        return sendCommand(cmd, null);
    }

    /**
     * 发送指令
     *
     * @param frame 指令
     * @return
     */
    private boolean sendDataCommand(byte[] frame) {
        return send(frame);
    }

    /**
     * 发送命令
     *
     * @param cmd  指令
     * @param data 数据
     * @return
     */
    private boolean sendCommand(byte[] cmd, byte[] data) {
        byte[] frame = CommandFrame.generateFrame(cmd, data);
        return send(frame);
    }

    /**
     * 发送帧
     *
     * @param frame 要发送的数据包
     * @return
     */
    private boolean send(byte[] frame) {
        synchronized (this) {
            if (frame != null) {
                int available;
                try {
                    //清空之前没有读完的信息
                    if ((available = mInputStream.available()) > 0) {
                        byte[] bytes = new byte[available];
                        mInputStream.read(bytes);
                    }
                    //Log.i(TAG, "发送的数据：" + Arrays.toString(frame));
                    //发送数据
                    mOutputStream.write(frame);
                    mOutputStream.flush();
                    return true;
                } catch (Exception e) {
                    BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
                    e.printStackTrace();
                }
                Log.e(TAG, "串口通讯异常！");
            }
            return false;
        }
    }

    /**
     * 接收响应帧
     *
     * @return 响应帧
     */
    private ResponseFrame readResponse() {
        return readResponse(FingerConstants.DEFAULT_COMMAND_TIMEOUT);
    }

    /**
     * 接收响应帧
     *
     * @param timeout 超时时间，单位ms
     * @return 响应帧
     */
    private ResponseFrame readResponse(int timeout) {
        synchronized (this) {
            int available;
            try {
                //启动超时计时器
                mSerialPortTimerTask = new MyTimerTask();
                mSerialPortTimer.schedule(mSerialPortTimerTask, timeout);
                isSerialPortTimeout = false;

                byte[] responseBytes = new byte[FingerConstants.COMMAND_LEN];
                while (!isSerialPortTimeout) {
                    available = mInputStream.available();
                    if (available >= FingerConstants.HEAD_LEN) {
                        mInputStream.read(responseBytes);
                        //Log.i(TAG, "收到的数据：" + Arrays.toString(responseBytes));
                        return ResponseFrame.initResponseFrame(responseBytes);
                    }
                    Thread.sleep(100);
                }
                //Log.e(TAG, "串口通讯超时！" + Calendar.getInstance().getTimeInMillis());
                return null;
            } catch (Exception e) {
                BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
                e.printStackTrace();
            } finally {
                if (mSerialPortTimerTask != null) {
                    mSerialPortTimerTask.cancel();
                }
            }
            Log.e(TAG, "串口通讯异常！");
            return null;
        }
    }

    /**
     * 接收响应数据包
     *
     * @param timeout 超时时间，单位ms
     * @return 数据包
     */
    private ResponseFrame readDataResponse(int timeout) {
        synchronized (this) {
            int available;
            try {
                //启动超时计时器
                mSerialPortTimerTask = new MyTimerTask();
                mSerialPortTimer.schedule(mSerialPortTimerTask, timeout);
                isSerialPortTimeout = false;

                byte[] headBytes = new byte[FingerConstants.HEAD_LEN];
                while (!isSerialPortTimeout) {
                    available = mInputStream.available();
                    if (available >= FingerConstants.HEAD_LEN) {
                        mInputStream.read(headBytes);

                        Head head = Head.initHead(headBytes);

                        int lastDataLen = head.getDataLen() + FingerConstants.CKS_LEN;
                        byte[] lastData = new byte[lastDataLen];
                        while (!isSerialPortTimeout) {
                            available = mInputStream.available();
                            if (available >= lastDataLen) {
                                mInputStream.read(lastData);
                                return ResponseFrame.initResponseFrame(head, lastData);
                            }
                            Thread.sleep(50);
                        }
                        return null;
                    }
                    Thread.sleep(100);
                }
                Log.e(TAG, "串口通讯超时！" + Calendar.getInstance().getTimeInMillis());
                return null;
            } catch (IOException e) {
                BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
                e.printStackTrace();
            } catch (InterruptedException e) {
                BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
                e.printStackTrace();
            } finally {
                if (mSerialPortTimerTask != null) {
                    mSerialPortTimerTask.cancel();
                }
            }
            Log.e(TAG, "串口通讯异常！");
            return null;
        }
    }

    /**
     * 执行多次响应的操作。在线程中非阻塞执行
     *
     * @param cmd      指令
     * @param intValue int型数据，一般为id
     * @param listener
     */
    private void operateMoreResponse(byte[] cmd, int intValue, OnFingerOperateListener listener) {
        operateMoreResponse(cmd, RadixUtil.shortToByte((short) intValue), listener);
    }

    /**
     * 执行多次响应的操作。在线程中非阻塞执行
     *
     * @param cmd      指令
     * @param data
     * @param listener
     */
    private void operateMoreResponse(final byte[] cmd, final byte[] data, final OnFingerOperateListener listener) {
        final FingerPrintOperator my = this;
        new Thread() {
            @Override
            public void run() {
                synchronized (my) {
                    cancel = false;
                    sendCommand(cmd, data);

                    ResponseFrame responseFrame;
                    while (!cancel) {
                        responseFrame = readResponse(fingerTimeout * 1000);
                        if (!checkResponseSuccess(responseFrame)) {
                            final int errNo;
                            if (responseFrame != null) {
                                errNo = getIntData(responseFrame);
                            } else {
                                if (!hasClickOtherWay){
                                    errNo = FingerConstants.ERR_TIMEOUT;
                                }else {
                                    errNo = FingerConstants.FINGER_OTHER_WAY;
                                }
                            }
                            new Thread() {
                                @Override
                                public void run() {
                                    listener.onFaild(errNo);
                                }
                            }.start();
                            fpCancel();
                            break;
                        } else {
                            //提示消息
                            if (checkIsMsgGd(responseFrame)) {
                                final ResponseFrame finalResponseFrame = responseFrame;
                                new Thread() {
                                    @Override
                                    public void run() {
                                        listener.onMsg(getIntData(finalResponseFrame));
                                    }
                                }.start();
                            } else {
                                final ResponseFrame finalResponseFrame = responseFrame;
                                new Thread() {
                                    @Override
                                    public void run() {
                                        listener.onSuccess(getIntData(finalResponseFrame));
                                    }
                                }.start();
                                break;
                            }
                        }
                    }
                    //为避免多线程调用导致指纹头死机，释放锁之前先发送fpCancel指令
                    fpCancel();
                }
            }
        }.start();
    }

    /**
     * 执行读取数据
     *
     * @param cmd
     * @param data
     * @return
     */
    private ResponseFrame operateReadData(byte[] cmd, Integer data) {
        synchronized (this) {
            sendCommand(cmd, data);

            ResponseFrame responseFrame = readResponse();
            if (!checkResponseSuccess(responseFrame)) {
                return null;
            }

            return readDataResponse(FingerConstants.READ_TEMPLATE_TIMEOUT);
        }
    }

    /**
     * 执行发送数据
     *
     * @param cmd  指令
     * @param data 数据包。对于写入模板类的指令，数据包中一般需要包含模板id
     * @return true：写入成功
     */
    private boolean operateSendData(byte[] cmd, byte[] data) {
        synchronized (this) {
            if (!sendCommand(cmd, data.length - 2)) {
                return false;
            }

            ResponseFrame responseFrame = readResponse();
            if (!checkResponseSuccess(responseFrame)) {
                return false;
            }
            byte[] frame = CommandDataFrame.generateFrame(cmd, data);
            if (!sendDataCommand(frame)) {
                return false;
            }

            responseFrame = readDataResponse(FingerConstants.READ_TEMPLATE_TIMEOUT);

            return checkResponseSuccess(responseFrame);
        }
    }

    /**
     * 执行一次响应的操作
     *
     * @param cmd 指令
     * @return
     */
    private ResponseFrame operateOneResponse(final byte[] cmd) {
        return operateOneResponse(cmd, null);
    }

    /**
     * 执行一次响应的操作
     *
     * @param cmd      指令
     * @param intValue int型数据
     * @return
     */
    private ResponseFrame operateOneResponse(final byte[] cmd, final int intValue) {
        return operateOneResponse(cmd, RadixUtil.shortToByte((short) intValue));
    }

    /**
     * 执行一次响应的操作
     *
     * @param cmd  指令
     * @param data 数据
     * @return
     */
    private ResponseFrame operateOneResponse(final byte[] cmd, byte[] data) {
        synchronized (this) {
            sendCommand(cmd, data);
            return readResponse();
        }
    }

    /**
     * 发送int型设置参数。一般用于获取LED、波特率、工作模式等的配置
     *
     * @param cmd 获取指令
     * @return null：获取失败、int value配置值
     */
    private Integer operateGetInt(byte[] cmd) {
        ResponseFrame responseFrame = operateOneResponse(cmd);
        if (!checkResponseSuccess(responseFrame)) {
            return null;
        }
        return getIntData(responseFrame);
    }

    /**
     * 获取int型配置参数。一般用于ID、LED、波特率、工作模式等的设置
     *
     * @param cmd   设置指令
     * @param value 要设置的值
     * @return true：设置成功、false：设置失败
     */
    private boolean operateSetInt(byte[] cmd, int value) {
        ResponseFrame responseFrame = operateOneResponse(cmd, value);
        return checkResponseSuccess(responseFrame);
    }

    /**
     * 将响应包中的数据转换为特征对象
     *
     * @param responseFrame
     * @return
     */
    private FingerPrintTemplate formateTemplate(ResponseFrame responseFrame) {
        if (responseFrame == null) {
            return null;
        }
        byte[] data = responseFrame.getData();
        byte[] idBytes = new byte[2];
        idBytes[0] = data[0];
        idBytes[1] = data[1];
        byte[] template = new byte[data.length - 2];
        System.arraycopy(data, 2, template, 0, template.length);

        return new FingerPrintTemplate(RadixUtil.byte2int(idBytes), template);
    }

    /**
     * 判断响应包是否为提示信息
     *
     * @param responseFrame
     * @return
     */
    private boolean checkIsMsgGd(ResponseFrame responseFrame) {
        return Arrays.equals(FingerConstants.GD_NEED_RELEASE_FINGER, responseFrame.getData()) ||
                Arrays.equals(FingerConstants.GD_NEED_FIRST_SWEEP, responseFrame.getData()) ||
                Arrays.equals(FingerConstants.GD_NEED_SECOND_SWEEP, responseFrame.getData()) ||
                Arrays.equals(FingerConstants.GD_NEED_THIRD_SWEEP, responseFrame.getData());
    }

    /**
     * 获取数据部分的int值
     *
     * @param responseFrame
     * @return
     */
    private int getIntData(ResponseFrame responseFrame) {
        return RadixUtil.byte2int(responseFrame.getData());
    }

    /**
     * 校验是否响应成功
     *
     * @param responseFrame
     * @return
     */
    private boolean checkResponseSuccess(ResponseFrame responseFrame) {
        return responseFrame != null &&
                Arrays.equals(responseFrame.getRet(), FingerConstants.RET_SUCCESS) &&
                //不是错误指令
                !Arrays.equals(FingerConstants.CMD_INCORRECT, responseFrame.getHead().getCmd());
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            isSerialPortTimeout = true;
        }
    }
}
