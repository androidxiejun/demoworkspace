package com.device;

/**
 * @author HanPei
 * @date 2018/12/6  下午2:05
 */
public class Device {
    static {
        try {
            System.loadLibrary("Device");
        } catch (UnsatisfiedLinkError e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 获取指纹特征
     *
     * @param portName 串口名
     * @param baudRate 波特率(9600)
     * @param timeout  超时时间(毫秒)
     * @param finger   指纹特征(输出参数)
     * @param message  错误信息(输出参数)
     * @return 0-成功, 其它-失败
     */
    public native static int getFeature(String portName, int baudRate, int timeout, byte[] finger, byte[] message);

    /**
     * 获取指纹模板
     *
     * @param portName 串口名
     * @param baudRate 波特率(9600)
     * @param timeout  超时时间(毫秒)
     * @param finger   指纹模板(输出参数)
     * @param message  错误信息(输出参数)
     * @return 0-成功, 其它-失败
     */
    public native static int getTemplate(String portName, int baudRate, int timeout, byte[] finger, byte[] message);

    /**
     * 指纹比对
     *
     * @param mbFinger 指纹模板
     * @param tzFinger 指纹特征
     * @param level    安全级别(1-5,通常为3)
     * @return 0-指纹匹配, 非0-指纹不匹配
     */
    public native static int verifyFinger(String mbFinger, String tzFinger, int level);

    /**
     * 取消指纹采集
     *
     * @return
     */
    public native static int cancel();
}
