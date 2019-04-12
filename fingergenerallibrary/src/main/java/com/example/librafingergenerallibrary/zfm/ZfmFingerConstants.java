package com.example.librafingergenerallibrary.zfm;

/**
 * 常量
 * Created by sean on 2017/10/30.
 */

public class ZfmFingerConstants {

    /**
     * 默认数据传输超时100ms
     */
    public final static short DEFAULT_COMMAND_TIMEOUT = 500;
    /**
     * 读取特征超时2000ms
     */
    public final static short READ_TEMPLATE_TIMEOUT = 2000;

    public static final int CMD_FIRST_LEN = 9;
    public static final int CKS_LEN = 2;

    /**
     * 指令包包头
     */
    public final static byte[] FRAME_START = new byte[]{(byte) 0xef, (byte) 0x01};
    /**
     * 指令包默认地址
     */
    public final static byte[] FRAME_DEFAULT_ADDR = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
    /**
     * 指令包默认地址
     */
    public final static byte[] FRAME_DEFAULT_PWD = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};


    /**
     * 包标识-命令包
     */
    public final static byte FRAME_PID_COMMAND_PACKET = 0x01;
    /**
     * 包标识-数据包-有后续包
     */
    public final static byte FRAME_PID_DATA_PACKET = 0x02;
    /**
     * 包标识-应答包
     */
    public final static byte FRAME_PID_ACK_PACKET = 0x07;
    /**
     * 包标识-结束包
     */
    public final static byte FRAME_PID_END_DATA_PACKET = 0x08;


    /**指纹命令
     *
     */
    /**
     * 指纹命令-录指纹图像
     */
    public final static byte[] CMD_GEN_IMG = new byte[]{(byte) 0x01};
    /**
     * 指纹命令-图像转特征
     */
    public final static byte[] CMD_IMG_2_TZ = new byte[]{(byte) 0x02};
    /**
     * 指纹命令-特征对比
     */
    public final static byte[] CMD_MATCH = new byte[]{(byte) 0x03};
    /**
     * 指纹命令-搜索指纹
     */
    public final static byte[] CMD_SERACH = new byte[]{(byte) 0x04};
    /**
     * 指纹命令-特征合成模板
     */
    public final static byte[] CMD_REG_MODEL = new byte[]{(byte) 0x05};
    /**
     * 指纹命令-存储模板
     */
    public final static byte[] CMD_STORE = new byte[]{(byte) 0x06};
    /**
     * 指纹命令-读出模板
     */
    public final static byte[] CMD_LOAD_CHAR = new byte[]{(byte) 0x07};
    /**
     * 指纹命令-上传特征
     */
    public final static byte[] CMD_UP_CHAR = new byte[]{(byte) 0x08};
    /**
     * 指纹命令-下载特征
     */
    public final static byte[] CMD_DOWN_CHR = new byte[]{(byte) 0x09};
    /**
     * 指纹命令-上传图像
     */
    public final static byte[] CMD_UP_IMAGE = new byte[]{(byte) 0x0a};
    /**
     * 指纹命令-下载图像
     */
    public final static byte[] CMD_DOWN_IMAGE = new byte[]{(byte) 0x0b};
    /**
     * 指纹命令-删除模板
     */
    public final static byte[] CMD_DELET_CHAR = new byte[]{(byte) 0x0c};
    /**
     * 指纹命令-清空指纹库
     */
    public final static byte[] CMD_EMPTY = new byte[]{(byte) 0x0d};
    /**
     * 指纹命令-设置系统参数
     */
    public final static byte[] CMD_SET_SYS_PARA = new byte[]{(byte) 0x0e};
    /**
     * 指纹命令-读系统参数
     */
    public final static byte[] CMD_READ_SYS_PARA = new byte[]{(byte) 0x0f};
    /**
     * 指纹命令-设置口令
     */
    public final static byte[] CMD_SET_PWD = new byte[]{(byte) 0x12};
    /**
     * 指纹命令-校验口令
     */
    public final static byte[] CMD_VFY_PWD = new byte[]{(byte) 0x13};
    /**
     * 指纹命令-采样随机数
     */
    public final static byte[] CMD_GET_RANDOM_CODE = new byte[]{(byte) 0x14};
    /**
     * 指纹命令-设置地址
     */
    public final static byte[] CMD_SET_ADDR = new byte[]{(byte) 0x15};
    /**
     * 指纹命令-写记事本
     */
    public final static byte[] CMD_WERITE_NOTEPAD = new byte[]{(byte) 0x18};
    /**
     * 指纹命令-读记事本
     */
    public final static byte[] CMD_READ_NOTEPAD = new byte[]{(byte) 0x19};
    /**
     * 指纹命令-读指纹模板
     */
    public final static byte[] CMD_TEMPLATE_NUM = new byte[]{(byte) 0x01d};
    /**
     * 指纹命令-读指纹模板索引表
     */
    public final static byte[] CMD_READ_CON_LIST = new byte[]{(byte) 0x01f};


    /**
     * 指令执行完毕或OK
     */
    public final static byte MSG_CODE_OK = 0x00;
    /**
     * 数据包接收错误
     */
    public final static byte MSG_CODE_DATA_ERR = 0x01;
    /**
     * 传感器上没有手指
     */
    public final static byte MSG_CODE_NO_FINGER = 0x02;
    /**
     * 录入指纹图像失败
     */
    public final static byte MSG_CODE_FINGER_IMG_FAIL = 0x03;
    /**
     * 指纹图像太乱而生不成特征
     */
    public final static byte MSG_CODE_FINGER_IMG_CANNOT_USE = 0x06;
    /**
     * 指纹图像正常，但特征点太少而生不成特征
     */
    public final static byte MSG_CODE_CANNOT_GET_FEATURE = 0x07;
    /**
     * 指纹不匹配
     */
    public final static byte MSG_CODE_FINGER_PRINT_CANNOT_MATCHING = 0x08;
    /**
     * 没搜索到指纹
     */
    public final static byte MSG_CODE_NO_FINGER_PRINT = 0x09;
    /**
     * 特征合并失败
     */
    public final static byte MSG_CODE_FEATURE_MERGE_FAIL = 0x0a;
    /**
     * 访问指纹库时地址序号超出指纹库范围
     */
    public final static byte MSG_CODE_OVER_SCOPE = 0x0b;
    /**
     * 从指纹库读模板出错
     */
    public final static byte MSG_CODE_FINGER_TEMPLE_FALE = 0x0c;
    /**
     * 上传特征失败
     */
    public final static byte MSG_CODE_UPLOAD_FEATURE_FAIL = 0x0d;
    /**
     * 模块不能接受后续数据包
     */
    public final static byte MSG_CODE_CANNOT_APPLY_DATA_PACKAGE = 0x0e;
    /**
     * 上传图像失败
     */
    public final static byte MSG_CODE_UPLOAD_IMG_FAIL = 0x0f;
    /**
     * 删除模板失败
     */
    public final static byte MSG_CODE_DELETE_TEMPLE_FAIL = 0x10;
    /**
     * 清空指纹库失败
     */
    public final static byte MSG_CODE_CLEAR_FAIL = 0x11;
    /**
     * 口令不正确
     */
    public final static byte MSG_CODE_PWD_ERR = 0x13;
    /**
     * 缓冲区内没有有效原始图而生不成图像
     */
    public final static byte MSG_CODE_HAVE_NOT_USE_IMG = 0x15;
    /**
     * 读写flash出错
     */
    public final static byte MSG_CODE_FLASH_ERR = 0x18;
    /**
     * 无效寄存器号
     */
    public final static byte MSG_CODE_INVALID_CPU_NO = 0x1a;
    /**
     * 地址码错误
     */
    public final static byte MSG_CODE_ADDRESS_ERR = 0x20;
    /**
     * 必须验证口令
     */
    public final static byte MSG_CODE_NEED_PWD = 0x21;
    /**
     * 请求超时
     */
    public final static byte MSG_TIME_OUT = 0x66;
    /**
     * 无数据
     */
    public final static byte MSG_NONE = 0x67;
    /**
     * 生成指纹编号错误
     */
    public final static byte ERR_FINGER_ID = 0x68;
    /**
     * 识别错误
     */
    public final static byte ERR = 0x69;
    /**
     * 请抬起手指
     */
    public final static byte MSG_FINGER_UP = 0x70;
    /**
     * 请录入下一个指纹
     */
    public final static byte MSG_SYS = 0x71;
    /**
     * 写入数据出错
     */
    public final static byte MSG_INPUT_ERR = 0x72;
    /**
     * 请录入下一个指纹
     */
    public final static byte MSG_FINGER_DOWN = 0x73;
    /**
     * 指纹已存在
     */
    public final static byte MSG_FINGER_HAS = 0x74;
    /**
     * 指纹库已满
     */
    public final static byte MSG_FINGER_FULL = 0x75;


    public static String getMsg(int code) {
        switch (code) {
            case MSG_CODE_OK:
                return "指令执行完毕或OK";
            case MSG_CODE_DATA_ERR:
                return "数据包接收错误";
            case MSG_CODE_NO_FINGER:
                return "传感器上没有手指";
            case MSG_CODE_FINGER_IMG_FAIL:
                return "录入指纹图像失败";
            case MSG_CODE_FINGER_IMG_CANNOT_USE:
                return "指纹图像太乱而生不成特征";
            case MSG_CODE_CANNOT_GET_FEATURE:
                return "指纹图像正常，但特征点太少而生不成特征";
            case MSG_CODE_FINGER_PRINT_CANNOT_MATCHING:
                return "指纹不匹配";
            case MSG_CODE_NO_FINGER_PRINT:
                return "没搜索到指纹";
            case MSG_CODE_FEATURE_MERGE_FAIL:
                return "特征合并失败";
            case MSG_CODE_OVER_SCOPE:
                return "访问指纹库时地址序号超出指纹库范围";
            case MSG_CODE_FINGER_TEMPLE_FALE:
                return "从指纹库读模板出错";
            case MSG_CODE_UPLOAD_FEATURE_FAIL:
                return "上传特征失败";
            case MSG_CODE_CANNOT_APPLY_DATA_PACKAGE:
                return "模块不能接受后续数据包";
            case MSG_CODE_UPLOAD_IMG_FAIL:
                return "上传图像失败";
            case MSG_CODE_DELETE_TEMPLE_FAIL:
                return "删除模板失败";
            case MSG_CODE_CLEAR_FAIL:
                return "清空指纹库失败";
            case MSG_CODE_PWD_ERR:
                return "口令不正确";
            case MSG_CODE_HAVE_NOT_USE_IMG:
                return "缓冲区内没有有效原始图而生不成图像";
            case MSG_CODE_FLASH_ERR:
                return "读写flash出错";
            case MSG_CODE_INVALID_CPU_NO:
                return "无效寄存器号";
            case MSG_CODE_ADDRESS_ERR:
                return "地址码错误";
            case MSG_CODE_NEED_PWD:
                return "必须验证口令";
            case MSG_TIME_OUT:
                return "请求超时";
            case MSG_NONE:
                return "无数据";
            case ERR_FINGER_ID:
                return "生成指纹编号错误";
            case ERR:
                return "识别错误";
            case MSG_FINGER_DOWN:
                return "请按压手指";
            case MSG_SYS:
                return "系统出错";
            case MSG_INPUT_ERR:
                return "写入数据出错";
            case MSG_FINGER_UP:
                return "请抬起手指";
            case MSG_FINGER_HAS:
                return "指纹已存在";
            case MSG_FINGER_FULL:
                return "指纹库已满";
            default:
                return "未知错误";
        }
    }
}
