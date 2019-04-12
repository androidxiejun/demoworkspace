package com.example.librafingergenerallibrary.operator;

import com.example.librafingergenerallibrary.common.FingerPrintTemplate;
import com.example.librafingergenerallibrary.listenner.OnFingerOperateListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 接口 供两种指纹模块实现
 * 作者：sean
 * 类描述：
 * 备注消息：
 * 修改时间：2017/11/9
 */

public interface IFingerPrintOperator {
    /**
     * 初始化
     */
    void init(InputStream inputStream, OutputStream outputStream, int fingerTimeout) throws IOException;

    /**
     * @param num 删除指纹模块
     */
    boolean clearTemplate(int num);

    /**
     * 清空中文模块
     */
    boolean clearAllTemplate();

    /**
     * 录入指纹
     *
     * @param listener 回调接口
     */
    void enroll(final OnFingerOperateListener listener);

    /**
     * 读取指纹特征
     *
     * @param templateId 指纹号
     */
    FingerPrintTemplate readTemplate(int templateId);

    /**
     * 写入特征
     *
     * @param template 特征数组集合
     * @return 指纹机id编号
     */
    int writeTemplate(byte[] template);

    /**
     * 检测是否已经连接过连接
     *
     * @return
     */
    boolean isConnected();

    /**
     * 检测是否连接上
     *
     * @return
     */
    boolean testConnection();

    /**
     * 验证指纹是否存在
     *
     * @param listener 结果回调
     */
    void identify(OnFingerOperateListener listener);

}
