package com.example.librafingergenerallibrary.common;


import com.example.librafingergenerallibrary.util.BaseUtil;
import com.example.librafingergenerallibrary.util.RadixUtil;

import java.util.Arrays;

/**
 * 生成发送指令数据包
 */
public class CommandDataFrame {

    /**
     * 命令码 Command Code
     */
    private byte[] byteCmd = new byte[2];
    /**
     * 命令参数Command Parameter
     */
    private byte[] byteData = new byte[16];
    private byte[] byteCommandFrame = null;

    /**
     * 组合数据帧
     *
     * @param cmd  前缀
     * @param data 命令
     */
    public CommandDataFrame(byte[] cmd, byte[] data) throws Exception {
        try {
            this.byteCmd = cmd;
            this.byteData = data;
            this.byteCommandFrame = new byte[8 + data.length];
            System.arraycopy(FingerConstants.FRAME_DATA_PREFIX, 0, this.byteCommandFrame, 0, 2);
            System.arraycopy(cmd, 0, this.byteCommandFrame, 2, 2);
            System.arraycopy(RadixUtil.shortToByte((short) data.length), 0, this.byteCommandFrame, 4, 2);
            System.arraycopy(data, 0, this.byteCommandFrame, 6, data.length);
            byte[] cks = RadixUtil.checkSum(byteCommandFrame, this.byteCommandFrame.length);
            System.arraycopy(cks, 0, this.byteCommandFrame, 6 + data.length, 2);
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            throw new Exception();
        }
    }

    /**
     * 组合数据帧
     *
     * @param cmd  命令
     * @param data 数据
     */
    public static byte[] generateFrame(byte[] cmd, byte[] data) {
        try {
            byte[] frame = null;
            if (data != null && data.length > 16) {
                frame = new byte[data.length + 8];
            } else {
                frame = new byte[FingerConstants.COMMAND_LEN];
            }
            frame[0] = FingerConstants.FRAME_DATA_PREFIX[0];
            frame[1] = FingerConstants.FRAME_DATA_PREFIX[1];
            frame[2] = cmd[0];
            frame[3] = cmd[1];
            if (data != null) {
                int dataLen = data.length;
                frame[4] = (byte) (dataLen & 0xFF);
                frame[5] = (byte) ((dataLen >> 8) & 0xFF);
                System.arraycopy(data, 0, frame, 6, dataLen);
            }

            byte[] cks = RadixUtil.checkSum(frame, frame.length);
            frame[frame.length - 2] = cks[0];
            frame[frame.length - 1] = cks[1];
            return frame;
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            return null;
        }
    }

    public static CommandDataFrame initCommandDataFrame(byte[] cmd, byte[] data) {
        try {
            return new CommandDataFrame(cmd, data);
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            return null;
        }
    }

    public byte[] getByteCmd() {
        return byteCmd;
    }

    public void setByteCmd(byte[] byteCmd) {
        this.byteCmd = byteCmd;
    }

    public byte[] getByteData() {
        return byteData;
    }

    public void setByteData(byte[] byteData) {
        this.byteData = byteData;
    }

    public byte[] getByteCommandFrame() {
        return byteCommandFrame;
    }

    public void setByteCommandFrame(byte[] byteCommandFrame) {
        this.byteCommandFrame = byteCommandFrame;
    }

    @Override
    public String toString() {
        return "CommandDataFrame{" +
                "byteCmd=" + Arrays.toString(byteCmd) +
                ", byteData=" + Arrays.toString(byteData) +
                ", byteCommandFrame=" + Arrays.toString(byteCommandFrame) +
                '}';
    }
}
