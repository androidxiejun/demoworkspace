package com.example.librafingergenerallibrary.operator;

import android.util.Log;

import com.example.librafingergenerallibrary.common.FingerConstants;
import com.example.librafingergenerallibrary.common.FingerPrintTemplate;
import com.example.librafingergenerallibrary.listenner.OnFingerOperateListener;
import com.example.librafingergenerallibrary.util.BaseUtil;
import com.example.librafingergenerallibrary.util.RadixUtil;
import com.example.librafingergenerallibrary.zfm.Packet;
import com.example.librafingergenerallibrary.zfm.PacketUtil;
import com.example.librafingergenerallibrary.zfm.ZfmFingerConstants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 业务处理，向外提供方法
 * 1.初始化
 * 2.口令验证
 * 3.指纹录入
 * 4.获取指纹特征
 * 5.写入指纹特征
 * 6.删除指纹
 * 7.清空指纹
 * 8.指纹识别
 * 9.获取空id
 * 10.获取指纹总数
 * 作者：sean
 * 类描述：
 * 备注消息：
 * 修改时间：2017/10/31
 */

public class ZfmFingerPrintOperator implements IFingerPrintOperator {
    private static final String TAG = "ZfmFingerPrintOperatorN";
    private static ZfmFingerPrintOperator mInstance;
    private int fingerTimeout;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    /**
     * 是否已超时
     */
    private boolean isSerialPortTimeout;
    /**
     * 效应定时器
     */
    private Timer mSerialPortTimer = new Timer();
    private TimerTask mSerialPortTimerTask;

    private ZfmFingerPrintOperator() {
    }

    /**
     * 获取实例
     */
    public static ZfmFingerPrintOperator getInstance() {
        if (mInstance == null) {
            synchronized (ZfmFingerPrintOperator.class) {
                mInstance = new ZfmFingerPrintOperator();
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @param inputStream  端口输入流
     * @param outputStream 端口输出流
     */
    @Override
    public void init(InputStream inputStream, OutputStream outputStream, int fingerTimeout) throws IOException {
        mInputStream = inputStream;
        mOutputStream = outputStream;
        this.fingerTimeout = fingerTimeout;
    }

    @Override
    public boolean isConnected() {
        return mInputStream != null && mOutputStream != null;
    }

    @Override
    public boolean testConnection() {
        return vfyPwd();
    }

    /**
     * @param timeStartTag
     * @param listener
     * @return
     */
    private boolean isTimeOut(long timeStartTag, OnFingerOperateListener listener) {
        long x = System.currentTimeMillis() - timeStartTag;
        if (x > fingerTimeout) {
            isSerialPortTimeout = true;
            if (listener != null) {
                listener.onFaild(ZfmFingerConstants.MSG_TIME_OUT);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 读取len个字节
     */
    private byte[] readByte(int len) {
        try {
            byte[] readByte = new byte[len];
            long time = 0;
            while (time < len) {
                if (mInputStream.available() >= len) {
                    mInputStream.read(readByte);
                    return readByte;
                }
                Thread.sleep(1);
                time += 1;
            }
        } catch (IOException e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            e.printStackTrace();
        } catch (InterruptedException e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 清空数据流
     */
    private void cleanInputStrem() {
        int available = 0;
        try {
            if (mInputStream != null && (available = mInputStream.available()) > 1) {
                mInputStream.read(new byte[available]);
            }
        } catch (IOException e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            e.printStackTrace();
        }
    }

    /**
     * 读取输入流生成包
     */
    private Packet makePacket() {
        Packet packet = new Packet();

        byte[] start = new byte[2];
        byte[] addr = new byte[4];
        byte[] pid = new byte[1];
        byte[] length = new byte[2];
        byte[] data = null;
        byte[] sum = new byte[2];

        Log.w(TAG, "makePacket-");
        //检查头
        start = readByte(2);

        if (!Arrays.equals(start, ZfmFingerConstants.FRAME_START)) {
            Log.w(TAG, "makePacket-start读取失败");
            cleanInputStrem();
            return null;
        } else {
            packet.setStart(start);
        }
        //检查地址
        addr = readByte(4);
        if (!Arrays.equals(addr, ZfmFingerConstants.FRAME_DEFAULT_ADDR)) {
            Log.w(TAG, "makePacket-addr读取失败");
            cleanInputStrem();
            return null;
        } else {
            packet.setAddr(addr);
        }
        pid = readByte(1);
        if (!PacketUtil.checkPid(pid[0])) {
            Log.w(TAG, "makePacket-pid读取失败");

            cleanInputStrem();
            return null;
        } else {
            packet.setPid(pid);
        }

        length = readByte(2);
        int len = RadixUtil.byteToShort(length);
        if (len - 2 > 0) {
            //获取数据字节
            data = readByte(len - 2);
        }
        sum = readByte(2);

        if (sum != null && sum.length > 0) {
            int sumLen = RadixUtil.byteToShort(sum);
        } else {
            Log.w(TAG, "makePacket-sum读取失败");
            cleanInputStrem();
            return null;
        }

        byte[] check = new byte[3 + len - 2];
        System.arraycopy(pid, 0, check, 0, 1);
        System.arraycopy(length, 0, check, 1, 2);
        if (data != null && data.length > 0) {
            System.arraycopy(data, 0, check, 3, data.length);
        }
        byte[] checkSum = PacketUtil.doCheckSum(check);

        if (Arrays.equals(checkSum, sum)) {
            packet.setLength(length);
            packet.setData(data);
            packet.setSum(sum);
            Log.w(TAG, "makePacket成功");
        } else {
            Log.w(TAG, "makePacket校验失败");
            return null;
        }
        return packet;

    }

    /**
     * 执行一次响应的操作
     *
     * @param cmd  指令
     * @param data 数据
     * @return
     */
    private Packet operateOneResponse(final byte[] cmd, byte[] data) {
        synchronized (this) {
            sendCommand(cmd, data);
            return readResponse();
        }
    }

    /**
     * 发送命令
     *
     * @param cmd  指令
     * @param data 数据
     */
    private void sendCommand(byte[] cmd, byte[] data) {
        byte[] packet = PacketUtil.makeCommandPacket(cmd, data);
        send(packet);
    }

    /**
     * 发送帧
     *
     * @param packet 要发送的数据包
     * @return
     */
    private boolean send(byte[] packet) {
        synchronized (this) {
            int available = 0;
            try {
                //清空之前没有读完的信息
                if ((available = mInputStream.available()) > 0) {
                    //Log.w(TAG, "===================>串口存在未读缓存数据：" + available + " 字节");
                    byte[] bytes = new byte[available];
                    mInputStream.read(bytes);
                    //Log.w(TAG, "===================>未读内容：" + RadixUtil.bytes2Hex(bytes));
                }

                //Log.w(TAG, "===================>发送的数据：" + Arrays.toString(packet));
                mOutputStream.write(packet);
                mOutputStream.flush();
                return true;
            } catch (IOException e) {
                BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
                e.printStackTrace();
            }
            //Log.e(TAG, "===================>串口通讯异常！");
            return false;
        }
    }

    /**
     * 接收响应帧
     *
     * @return 响应帧
     */
    private Packet readResponse() {
        synchronized (this) {
            int available = 0;
            try {
                long time = 0;
                while (time < FingerConstants.DEFAULT_COMMAND_TIMEOUT) {
                    available = mInputStream.available();
                    if (available > 0) {
                        return makePacket();
                    }
                    time += 10;
                    Thread.sleep(10);
                }
            } catch (IOException e) {
                BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
                e.printStackTrace();
            } catch (InterruptedException e) {
                BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
                e.printStackTrace();
            }
            Log.e(TAG, "串口通讯异常！");
            return null;
        }
    }

    /**
     * 验证口令
     */
    public synchronized boolean vfyPwd() {
        Packet packet = operateOneResponse(ZfmFingerConstants.CMD_VFY_PWD, ZfmFingerConstants.FRAME_DEFAULT_PWD);
        if (packet != null) {
            byte tag = packet.getData()[0];
            if (tag == ZfmFingerConstants.MSG_CODE_OK) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void waitFingerUp() {
        Packet packet;

        try {
            //发送图片识别
            while (true) {

                //发送识别图像指令
                packet = operateOneResponse(ZfmFingerConstants.CMD_GEN_IMG, null);

                if (packet != null && packet.getData()[0] != ZfmFingerConstants.MSG_CODE_OK) {
                    return;
                }
            }
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            e.printStackTrace();
        }
    }

    /**
     * 指纹录入
     */
    @Override
    public synchronized void enroll(final OnFingerOperateListener listener) {
        synchronized (this) {
            final int pageId = getFingerId();
            if (pageId == -1) {
                listener.onFaild(ZfmFingerConstants.MSG_FINGER_FULL);
                return;
            }
            try {
                int searchResult = -1;
                listener.onMsg(ZfmFingerConstants.MSG_FINGER_DOWN);
                searchResult = search((byte) 1);
                if (searchResult != -2) {
                    if (searchResult >= 0) {
                        listener.onFaild(ZfmFingerConstants.MSG_FINGER_HAS);
                    } else {
                        listener.onFaild(ZfmFingerConstants.MSG_TIME_OUT);
                    }
                    return;
                }

                listener.onMsg(ZfmFingerConstants.MSG_FINGER_UP);
                waitFingerUp();


                listener.onMsg(ZfmFingerConstants.MSG_FINGER_DOWN);
                searchResult = search((byte) 2);
                if (searchResult == -2) {
                    if (storeModel(pageId)) {
                        listener.onSuccess(pageId);
                    } else {
                        listener.onFaild(ZfmFingerConstants.ERR);
                    }
                } else {
                    if (searchResult >= 0) {
                        listener.onFaild(ZfmFingerConstants.MSG_FINGER_HAS);
                    } else {
                        listener.onFaild(ZfmFingerConstants.MSG_TIME_OUT);
                    }
                }

            } catch (Exception e) {
                BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
                e.printStackTrace();
            }
        }
    }

    private boolean regModel() {
        //特征合成模板
        Packet packet = operateOneResponse(ZfmFingerConstants.CMD_REG_MODEL, null);
        if (packet == null) {
            Log.d(TAG, "======特征合成模板======>packet==null");
            return false;
        }
        if (packet.getData()[0] != ZfmFingerConstants.MSG_CODE_OK) {
            return false;
        }
        return true;

    }

    private boolean storeModel(int id) {
        byte[] idByties = RadixUtil.shortToByte((short) id);
        //存储模板文件
        Packet packet = operateOneResponse(ZfmFingerConstants.CMD_STORE, new byte[]{0x01, idByties[1], idByties[0]});
        if (packet == null) {
            Log.d(TAG, "============>packet==null");
            return false;
        }
        if (packet.getData()[0] != ZfmFingerConstants.MSG_CODE_OK) {
            return false;
        }
        return true;
    }

    /**
     * 根据指纹id获取指纹特征
     */
    public synchronized void readTemplate(final short pageId, final ZfmFingerPrintOperatorListen listener) {
        synchronized (this) {
            Packet packet = null;
            byte[] idByte = new byte[2];
            for (int i = 0; i < 2; i++) {
                idByte[i] = (byte) (pageId >> 8 * (1 - i) & 0xFF);
            }
            boolean doNext = false;
            //将数据库中指定的id号指纹库 读入到缓存区0x01中
            packet = operateOneResponse(ZfmFingerConstants.CMD_LOAD_CHAR, new byte[]{0x01, idByte[0], idByte[1]});

            if (packet == null || packet.getData()[0] != ZfmFingerConstants.MSG_CODE_OK) {
                listener.onFaild(ZfmFingerConstants.ERR);
                return;
            }

            //将指纹库文件读出到上位机
            packet = operateOneResponse(ZfmFingerConstants.CMD_UP_CHAR, new byte[]{0x01});
            if (packet == null || packet.getData()[0] != ZfmFingerConstants.MSG_CODE_OK) {
                listener.onFaild(ZfmFingerConstants.ERR);
                return;
            }


            List<byte[]> dateList = new ArrayList<>();

            while (true) {

                packet = makePacket();
                if (packet == null) {
                    listener.onFaild(ZfmFingerConstants.ERR);
                    return;
                }

                dateList.add(packet.getData());

                if (packet.getPid()[0] == ZfmFingerConstants.FRAME_PID_END_DATA_PACKET) {
                    break;
                }
            }
            int restulLen = 0;
            //整合数据
            for (int i = 0; i < dateList.size(); i++) {
                restulLen += dateList.get(i).length;
            }
            byte[] result = new byte[restulLen];
            int lenFlag = 0;
            for (int i = 0; i < dateList.size(); i++) {
                System.arraycopy(dateList.get(i), 0, result, lenFlag, dateList.get(i).length);
                lenFlag += dateList.get(i).length;
            }
            Log.i("========", "特征长度：" + result.length);
            listener.success(result);
        }
    }

    /**
     * 获取指纹特征
     *
     * @param templateId 指纹ID
     * @return
     */
    @Override
    public FingerPrintTemplate readTemplate(int templateId) {

        synchronized (this) {
            Packet packet = new Packet();
            byte[] idByte = new byte[2];
            for (int i = 0; i < 2; i++) {
                idByte[i] = (byte) (templateId >> 8 * (1 - i) & 0xFF);
            }
            boolean doNext = false;
            //将数据库中指定的id号指纹库 读入到缓存区0x01中
            packet = operateOneResponse(ZfmFingerConstants.CMD_LOAD_CHAR, new byte[]{0x01, idByte[0], idByte[1]});

            if (packet != null && packet.getData() != null && packet.getData().length > 0) {
                if (packet.getData()[0] == ZfmFingerConstants.MSG_CODE_OK) {
                    doNext = true;
                } else {
                    return null;
                }
            } else {
                return null;
            }

            //将指纹库文件读出到上位机
            if (doNext) {
                packet = operateOneResponse(ZfmFingerConstants.CMD_UP_CHAR, new byte[]{0x01});
                if (packet != null && packet.getData() != null && packet.getData().length > 0) {
                    if (packet.getData()[0] == ZfmFingerConstants.MSG_CODE_OK) {
                        List<byte[]> dateList = new ArrayList<>();
                        Long timeStartTag = System.currentTimeMillis();

                        boolean flag = false;
                        while (!flag) {
                            flag = isTimeOut(timeStartTag, null);
                            if (flag) {
                                return null;
                            }
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
                                e.printStackTrace();
                            }
                            packet = makePacket();
                            if (packet != null && packet.getPid() != null && packet.getPid().length > 0) {
                                if (packet.getPid()[0] == ZfmFingerConstants.FRAME_PID_DATA_PACKET) {
                                    dateList.add(PacketUtil.getPacketByte(packet));
                                } else if (packet.getPid()[0] == ZfmFingerConstants.FRAME_PID_END_DATA_PACKET) {
                                    dateList.add(PacketUtil.getPacketByte(packet));
                                    break;
                                }
                            } else {
                                return null;
                            }
                        }
                        int restulLen = 0;
                        //整合数据
                        for (int i = 0; i < dateList.size(); i++) {
                            restulLen += dateList.get(1).length;
                        }
                        byte[] result = new byte[restulLen];
                        int lenFlag = 0;
                        for (int i = 0; i < dateList.size(); i++) {
                            System.arraycopy(dateList.get(i), 0, result, lenFlag, dateList.get(i).length);
                            lenFlag += dateList.get(i).length;
                        }

                        FingerPrintTemplate fingerPrintTemplate = new FingerPrintTemplate(templateId, result);
                        //返回结果集合
                        return fingerPrintTemplate;
                    } else {

                        return null;
                    }
                } else {
                    return null;
                }
            }

            return null;
        }
    }

    /**
     * 删除摸一个指纹
     *
     * @param num
     * @param listener
     */
    public synchronized void clearTemplate(final int num, final OnFingerOperateListener listener) {
        synchronized (this) {
            Packet packet = new Packet();
            byte[] bytes = new byte[2];
            for (int i = 0; i < 2; i++) {
                bytes[i] = (byte) (num >> 8 * (1 - i) & 0xFF);
            }
            packet = operateOneResponse(ZfmFingerConstants.CMD_DELET_CHAR, new byte[]{bytes[0], bytes[1], 0x00, 0x01});
            if (packet != null && packet.getData() != null && packet.getData().length > 0) {
                if (packet.getData()[0] == ZfmFingerConstants.MSG_CODE_OK) {
                    listener.onSuccess(ZfmFingerConstants.MSG_CODE_OK);
                } else {
                    listener.onFaild(packet.getData()[0]);
                }
            } else {
                listener.onFaild(ZfmFingerConstants.ERR);
            }
        }
    }

    /**
     * 删除摸一个指纹
     *
     * @param num
     */
    @Override
    public synchronized boolean clearTemplate(final int num) {
        synchronized (this) {
            byte[] bytes = new byte[2];
            for (int i = 0; i < 2; i++) {
                bytes[i] = (byte) (num >> 8 * (1 - i) & 0xFF);
            }
            Packet packet = operateOneResponse(ZfmFingerConstants.CMD_DELET_CHAR, new byte[]{bytes[0], bytes[1], 0x00, 0x01});
            if (packet != null && packet.getData() != null && packet.getData().length > 0) {
                if (packet.getData()[0] == ZfmFingerConstants.MSG_CODE_OK) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * 清空数据
     *
     * @param listener
     */
    public synchronized void clearAllTemplate(final OnFingerOperateListener listener) {
        synchronized (this) {
            Packet packet = operateOneResponse(ZfmFingerConstants.CMD_EMPTY, null);
            if (packet != null && packet.getData() != null && packet.getData().length > 0) {
                if (packet.getData()[0] == ZfmFingerConstants.MSG_CODE_OK) {
                    listener.onSuccess(ZfmFingerConstants.MSG_CODE_OK);
                } else {
                    listener.onFaild(packet.getData()[0]);
                }
            } else {
                listener.onFaild(ZfmFingerConstants.ERR);
            }
        }
    }

    /**
     * 清空数据
     */
    @Override
    public synchronized boolean clearAllTemplate() {
        synchronized (this) {
            Packet packet = operateOneResponse(ZfmFingerConstants.CMD_EMPTY, null);
            if (packet != null && packet.getData() != null && packet.getData().length > 0) {
                if (packet.getData()[0] == ZfmFingerConstants.MSG_CODE_OK) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    private int search(byte bufferId) {
        boolean cancel = false;
        try {
            Long timeStartTag = System.currentTimeMillis();
            //发送图片识别
            while (!cancel) {
                cancel = isTimeOut(timeStartTag, null);
                if (cancel) {
                    return -1;
                }

                if (!genImgAndMkTz(bufferId)) {
                    return -1;
                }

                Packet packet = operateOneResponse(ZfmFingerConstants.CMD_SERACH, new byte[]{bufferId, 0x00, 0x00, 0x03, (byte) 0xe8});
                Log.i("", "");
                if (packet == null) {
                    return -1;
                }
                byte flag = packet.getData()[0];
                if (flag == 0) {
                    byte[] pageIdByties = new byte[]{packet.getData()[2], packet.getData()[1]};
                    return RadixUtil.byte2int(pageIdByties);
                } else if (flag == 9) {
                    return -2;
                } else {
                    return -1;
                }

            }
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    /**
     * 搜索指纹
     *
     * @param listener
     */
    @Override
    public synchronized void identify(final OnFingerOperateListener listener) {
        synchronized (this) {
            int id = search((byte) 1);
            if (id >= 0) {
                listener.onSuccess(id);
            } else {
                listener.onFaild(ZfmFingerConstants.ERR);
            }
        }

    }

    private synchronized boolean genImgAndMkTz(byte bufferId) {
        Packet packet;

        boolean flag = false;
        try {
            mSerialPortTimerTask = new MyTimerTask();
            mSerialPortTimer.schedule(mSerialPortTimerTask, fingerTimeout);
            isSerialPortTimeout = false;
            //发送图片识别
            while (!isSerialPortTimeout) {

                //发送识别图像指令
                packet = operateOneResponse(ZfmFingerConstants.CMD_GEN_IMG, null);

                if (packet != null && packet.getData() != null && packet.getData().length > 0) {
                    switch (packet.getData()[0]) {
                        case ZfmFingerConstants.MSG_CODE_OK:
                            Log.i("genImgAndMkTz", "检测到手指");
                            if (img2Tz(bufferId)) {
                                mSerialPortTimerTask.cancel();
                                return true;
                            }
                            return false;
                        case ZfmFingerConstants.MSG_CODE_NO_FINGER:
                            Log.i("genImgAndMkTz", "未检测到手指");
                            break;
                        case 01:
                            Log.i("genImgAndMkTz", "数据包错误");
                            break;
                        case 03:
                            Log.i("genImgAndMkTz", "录入失败");
                            mSerialPortTimerTask.cancel();
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            e.printStackTrace();
        }
        Log.i("genImgAndMkTz", "指纹等待超时");
        return false;
    }

    private boolean img2Tz(byte bufferId) {
        Packet packet = operateOneResponse(ZfmFingerConstants.CMD_IMG_2_TZ, new byte[]{bufferId});
        if (packet == null) {
            Log.i("genImgAndMkTz", "图像生成特征未响应");
            return false;
        }
        if (packet.getData()[0] != ZfmFingerConstants.MSG_CODE_OK) {
            Log.i("genImgAndMkTz", "图像生成特征失败");
            return false;
        }
        Log.i("genImgAndMkTz", "图像生成特征成功");
        return true;
    }

    /**
     * 向设备写入指纹特征
     *
     * @param template
     */
    @Override
    public int writeTemplate(final byte[] template) {
        synchronized (this) {


            final int id = getFingerId();
            if (id == -1) {
                return -1;
            }
            Packet packet;
            //发送写入特征指令
            packet = operateOneResponse(ZfmFingerConstants.CMD_DOWN_CHR, new byte[]{0x02});


            if (packet == null || packet.getData()[0] != ZfmFingerConstants.MSG_CODE_OK) {
                return -1;
            }


            int sendLength;
            int pos = 0;
            byte flag = 2;
            while (pos < template.length) {
                if (pos + 128 < template.length - 1) {
                    sendLength = 128;
                } else {
                    sendLength = template.length - pos;
                    flag = 8;
                }
                byte[] data = new byte[sendLength];
                System.arraycopy(template, pos, data, 0, sendLength);
                PacketUtil.makeDataPacket(flag, data);
                send(data);
                pos += sendLength;
            }


            byte[] idByte = RadixUtil.shortToByte((short) id);

            //发送写入特征指令
            packet = operateOneResponse(ZfmFingerConstants.CMD_STORE, new byte[]{0x02, idByte[1], idByte[0]});
            if (packet.getData() == null || packet.getData()[0] != ZfmFingerConstants.MSG_CODE_OK) {
                return -1;
            }
            return id;

        }
    }

    /**
     * 返回指纹库中的不存在的指纹id编号
     *
     * @return
     */
    private int getFingerId() {

        Packet packet = new Packet();
        for (int i = 0; i < 4; i++) {

            packet = operateOneResponse(ZfmFingerConstants.CMD_READ_CON_LIST, new byte[]{(byte) i});
            if (packet != null && packet.getData() != null && packet.getData().length > 0) {
                if (packet.getData()[0] == ZfmFingerConstants.MSG_CODE_OK) {
                    byte[] bytes = new byte[32];
                    System.arraycopy(packet.getData(), 1, bytes, 0, 32);
                    if (bytes != null) {
                        for (int j = 0; j < bytes.length; j++) {
                            byte b8 = bytes[j];
                            for (int k = 0; k < 8; k++) {
                                if ((byte) ((b8 >> k) & 0x1) == 0) {
                                    return (8 * j + k) * (i + 1);
                                }
                            }
                        }
                    }
                    continue;

                } else {
                    return -1;
                }
            }
        }

        return -1;
    }

    /**
     * 获取指纹数总数
     *
     * @param listener
     */
    public void templateNum(final OnFingerOperateListener listener) {

        synchronized (this) {
            Packet packet = new Packet();
            packet = operateOneResponse(ZfmFingerConstants.CMD_READ_CON_LIST, null);
            if (packet != null && packet.getData() != null && packet.getData().length > 0) {
                if (packet.getData()[0] == ZfmFingerConstants.MSG_CODE_OK) {

                    listener.onSuccess(RadixUtil.byte2int(packet.getData()));
                } else {
                    listener.onFaild(packet.getData()[0]);
                }
            } else {
                listener.onFaild(ZfmFingerConstants.MSG_SYS);
            }
        }
    }

    public interface ZfmFingerPrintOperatorListen extends OnFingerOperateListener {
        void success(byte[] bytes);
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            isSerialPortTimeout = true;
        }
    }
}
