package com.example.librafingergenerallibrary.listenner;

/**
 * 接口 返回成功失败和信息
 * Created by zxl on 2017/9/29.
 */

public interface OnZZFingerOperateListener {
    public void onSuccess(byte[] data);

    public void onFaild(int errNo);

    public void onMsg(int msgNo);
}
