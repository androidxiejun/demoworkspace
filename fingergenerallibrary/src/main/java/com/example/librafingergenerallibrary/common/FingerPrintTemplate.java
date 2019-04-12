package com.example.librafingergenerallibrary.common;

/**
 * 返回上层使用的指纹特征
 * Created by zxl on 2017/9/29.
 */

public class FingerPrintTemplate {
    private int id;
    private byte[] templateData;

    public FingerPrintTemplate(int id, byte[] templateData) {
        this.id = id;
        this.templateData = templateData;
    }

    public int getId() {
        return id;
    }

    public byte[] getTemplateData() {
        return templateData;
    }
}
