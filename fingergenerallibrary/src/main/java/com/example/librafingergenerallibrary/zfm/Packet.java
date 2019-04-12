package com.example.librafingergenerallibrary.zfm;

/**
 * 数据包
 * 作者：sean
 * 类描述：
 * 备注消息：
 * 修改时间：2017/10/31
 */

public class Packet {
    private byte[] start;
    private byte[] addr;
    private byte[] pid;
    private byte[] length;
    private byte[] data;
    private byte[] sum;

    public byte[] getStart() {
        return start;
    }

    public void setStart(byte[] start) {
        this.start = start;
    }

    public byte[] getAddr() {
        return addr;
    }

    public void setAddr(byte[] addr) {
        this.addr = addr;
    }

    public byte[] getPid() {
        return pid;
    }

    public void setPid(byte[] pid) {
        this.pid = pid;
    }

    public byte[] getLength() {
        return length;
    }

    public void setLength(byte[] length) {
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getSum() {
        return sum;
    }

    public void setSum(byte[] sum) {
        this.sum = sum;
    }

}
