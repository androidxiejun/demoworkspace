package com.example.librafingergenerallibrary.listenner;

/**
 * Created by AndroidXJ on 2019/4/1.
 */

public interface FingerOperateCallback {
    void success(long templateId,byte[] data);
    void success(boolean isSuc);
    void onFail(int errNo);
    void onMsg(int msgNo);
}
