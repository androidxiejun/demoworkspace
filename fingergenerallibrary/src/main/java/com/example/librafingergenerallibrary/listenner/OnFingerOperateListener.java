package com.example.librafingergenerallibrary.listenner;

/**
 * 接口 返回成功失败和信息
 * Created by zxl on 2017/9/29.
 */

public interface OnFingerOperateListener {
    public void onSuccess(long id);

    public void onFaild(int errNo);

    public void onMsg(int msgNo);
}
