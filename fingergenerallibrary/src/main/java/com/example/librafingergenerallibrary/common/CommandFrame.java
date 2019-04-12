package com.example.librafingergenerallibrary.common;


import com.example.librafingergenerallibrary.util.BaseUtil;
import com.example.librafingergenerallibrary.util.RadixUtil;

/**
 * 生成发送指令
 */
public class CommandFrame {

    /**
     * 组合数据帧
     *
     * @param cmd  命令
     * @param data 数据
     */
    public static byte[] generateFrame(byte[] cmd, byte[] data) {
        try {
            byte[] frame = new byte[FingerConstants.COMMAND_LEN];
            frame[0] = FingerConstants.FRAME_PREFIX[0];
            frame[1] = FingerConstants.FRAME_PREFIX[1];
            frame[2] = cmd[0];
            frame[3] = cmd[1];
            if (data != null) {
                int dataLen = data.length;
                frame[4] = (byte) (dataLen & 0xFF);
                frame[5] = (byte) ((dataLen >> 8) & 0xFF);
                System.arraycopy(data, 0, frame, 6, dataLen);
            }

            byte[] cks = RadixUtil.checkSum(frame, FingerConstants.COMMAND_LEN - FingerConstants.CKS_LEN);
            frame[FingerConstants.COMMAND_LEN - 2] = cks[0];
            frame[FingerConstants.COMMAND_LEN - 1] = cks[1];
            return frame;
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            return null;
        }
    }
}
