package com.example.librafingergenerallibrary.listenner;

public interface FingerGetListener {
    void getZazFinagerOk(byte[] fingerData);
    void getZazFinagerFail(String msg);
}
