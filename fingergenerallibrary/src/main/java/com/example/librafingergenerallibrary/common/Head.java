package com.example.librafingergenerallibrary.common;


import com.example.librafingergenerallibrary.util.BaseUtil;
import com.example.librafingergenerallibrary.util.RadixUtil;

/**
 * 生成返回指令头
 * Created by zxl on 2017/9/28.
 */

public class Head {
    private byte[] prefix;
    private byte[] cmd;
    private int dataLen;

    public Head(byte[] bytes) throws Exception {
        try {
            prefix = new byte[2];
            prefix[0] = bytes[0];
            prefix[1] = bytes[1];
            cmd = new byte[2];
            cmd[0] = bytes[2];
            cmd[1] = bytes[3];
            byte[] len = new byte[2];
            len[0] = bytes[4];
            len[1] = bytes[5];
            dataLen = RadixUtil.byte2int(len);
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            throw new Exception();
        }
    }

    public static Head initHead(byte[] bytes) {
        try {
            return new Head(bytes);
        } catch (Exception e) {
            BaseUtil.saveErrorMsgToLocal(e.getMessage(), "fingerlibrary");
            return null;
        }
    }

    public byte[] getPrefix() {
        return prefix;
    }

    public byte[] getCmd() {
        return cmd;
    }

    public int getDataLen() {
        return dataLen;
    }
}
