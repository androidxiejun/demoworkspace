package com.example.librafingergenerallibrary.common;

/**
 * 错误指纹信息
 * Created by zxl on 2017/9/29.
 */

public class BrokenInfo {
    private int brokenTotal;
    private int first;

    public BrokenInfo(int brokenTotal, int first) {
        this.brokenTotal = brokenTotal;
        this.first = first;
    }

    public int getBrokenTotal() {
        return brokenTotal;
    }

    public int getFirst() {
        return first;
    }
}
