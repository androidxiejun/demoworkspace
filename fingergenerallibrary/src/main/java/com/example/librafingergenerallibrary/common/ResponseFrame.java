package com.example.librafingergenerallibrary.common;

import com.example.librafingergenerallibrary.util.BaseUtil;

/**
 * 生成返回指令
 */
public class ResponseFrame {
    /**
     * 头
     */
    private Head head;

    /**
     * 结果码 Result code
     */
    private byte[] ret;
    /**
     * 命令参数Command Parameter
     */
    private byte[] data;
    /**
     * 校验码
     */
    private byte[] cks;

    public ResponseFrame(Head head, byte[] lastData) throws Exception {
        try {
            this.head = head;
            ret = new byte[2];
            ret[0] = lastData[0];
            ret[1] = lastData[1];
            if (head.getDataLen() > 0) {
                data = new byte[head.getDataLen() - 2];
                System.arraycopy(lastData, 2, data, 0, data.length);
            }
            cks = new byte[2];
            cks[0] = lastData[lastData.length - 2];
            cks[1] = lastData[lastData.length - 1];
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            throw new Exception();
        }
    }

    public ResponseFrame(byte[] responseBytes) throws Exception {
        try {
            byte[] headBytes = new byte[FingerConstants.HEAD_LEN];
            System.arraycopy(responseBytes, 0, headBytes, 0, FingerConstants.HEAD_LEN);
            head = Head.initHead(headBytes);

            ret = new byte[2];
            ret[0] = responseBytes[FingerConstants.HEAD_LEN + 0];
            ret[1] = responseBytes[FingerConstants.HEAD_LEN + 1];
            if (head.getDataLen() > 0) {
                data = new byte[head.getDataLen() - 2];
                System.arraycopy(responseBytes, FingerConstants.HEAD_LEN + 2, data, 0, data.length);
            }
            cks = new byte[2];
            cks[0] = responseBytes[responseBytes.length - 2];
            cks[1] = responseBytes[responseBytes.length - 1];
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            throw new Exception();
        }
    }

    public static ResponseFrame initResponseFrame(Head head, byte[] lastData) {
        try {
            return new ResponseFrame(head, lastData);
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            return null;
        }
    }

    public static ResponseFrame initResponseFrame(byte[] responseBytes) {
        try {
            return new ResponseFrame(responseBytes);
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            return null;
        }
    }

    public Head getHead() {
        return head;
    }

    public byte[] getRet() {
        return ret;
    }

    public byte[] getData() {
        return data;
    }

    public byte[] getCks() {
        return cks;
    }
}
