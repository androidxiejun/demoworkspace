package com.example.librafingergenerallibrary.common;

/**
 * 常量
 * Created by dhht on 17/7/29.
 */

public class FingerConstants {
    /**
     * 默认数据传输超时100ms
     */
    public final static short DEFAULT_COMMAND_TIMEOUT = 1000;
    /**
     * 读取特征超时2000ms
     */
    public final static short READ_TEMPLATE_TIMEOUT = 2000;

    public static final int HEAD_LEN = 6;
    public static final int COMMAND_LEN = 24;
    public static final int RESPONSE_LAST_LEN = COMMAND_LEN - HEAD_LEN;
    public static final int THREAD_SLEEP_TIME = 10;
    public static final int CKS_LEN = 2;

    /**
     * 指令包帧前缀
     */
    public final static byte[] FRAME_PREFIX = new byte[]{(byte) 0x55, (byte) 0xAA};
    /**
     * 响应包帧前缀
     */
    public final static byte[] BACK_FRAME_PREFIX = new byte[]{(byte) 0xAA, (byte) 0x55};
    /**
     * 指令数据包数据帧前缀
     */
    public final static byte[] FRAME_DATA_PREFIX = new byte[]{(byte) 0x5A, (byte) 0xA5};
    /**
     * 响应数据包数据帧前缀
     */
    public final static byte[] BACK_FRAME_DATA_PREFIX = new byte[]{(byte) 0xA5, (byte) 0x5A};


    /**
     * 指纹验证
     */
    public final static byte[] CMD_VERIFY = new byte[]{(byte) 0x01, (byte) 0x01};
    /**
     * 指纹识别（指定指纹ID)
     */
    public final static byte[] CMD_IDENTIFY = new byte[]{(byte) 0x02, (byte) 0x01};
    /**
     * 指纹录入
     */
    public final static byte[] CMD_ENROLL = new byte[]{(byte) 0x03, (byte) 0x01};
    /**
     * 一次录入指纹
     */
    public final static byte[] CMD_ENROLL_ONETIME = new byte[]{(byte) 0x04, (byte) 0x01};
    /**
     * 删除指定的指纹模板
     */
    public final static byte[] CMD_CLEAR_TEMPLATE = new byte[]{(byte) 0x05, (byte) 0x01};
    /**
     * 删除所有指纹模板
     */
    public final static byte[] CMD_CLEAR_ALL_TEMPLATE = new byte[]{(byte) 0x06, (byte) 0x01};
    /**
     * 获取可注册模板编号
     */
    public final static byte[] CMD_GET_EMPTY_ID = new byte[]{(byte) 0x07, (byte) 0x01};
    /**
     * 获取指定编号的模板状态
     */
    public final static byte[] CMD_GET_TEMPLATE_STATUS = new byte[]{(byte) 0x08, (byte) 0x01};
    /**
     * 获取指纹模板数据库完整性
     */
    public final static byte[] CMD_GET_BROKEN_TEMPLATE = new byte[]{(byte) 0x09, (byte) 0x01};
    /**
     * 读取指纹数据库模板
     */
    public final static byte[] CMD_READ_TEMPLATE = new byte[]{(byte) 0x0A, (byte) 0x01};
    /**
     * 写入指纹数据库模板
     */
    public final static byte[] CMD_WRITE_TEMPLATE = new byte[]{(byte) 0x0B, (byte) 0x01};
    /**
     * 设置识别安全等级
     */
    public final static byte[] CMD_SET_SECURITY_LEVEL = new byte[]{(byte) 0x0C, (byte) 0x01};
    /**
     * 获取识别安全等级
     */
    public final static byte[] CMD_GET_SECURITY_LEVEL = new byte[]{(byte) 0x0D, (byte) 0x01};
    /**
     * 设置超时时间
     */
    public final static byte[] CMD_SET_FINGER_TIMEOUT = new byte[]{(byte) 0x0E, (byte) 0x01};
    /**
     * 获取超时时间
     */
    public final static byte[] CMD_GET_FINGER_TIMEOUT = new byte[]{(byte) 0x0F, (byte) 0x01};
    /**
     * 设置设备号
     */
    public final static byte[] CMD_SET_DEVICE_ID = new byte[]{(byte) 0x10, (byte) 0x01};
    /**
     * 获取设备号
     */
    public final static byte[] CMD_GET_DEVICE_ID = new byte[]{(byte) 0x11, (byte) 0x01};
    /**
     * 获取固件版本号
     */
    public final static byte[] CMD_GET_FW_VERSION = new byte[]{(byte) 0x12, (byte) 0x01};
    /**
     * 检测是否有指纹按下
     */
    public final static byte[] CMD_FINGER_DETECT = new byte[]{(byte) 0x13, (byte) 0x01};
    /**
     * 设置波特率
     */
    public final static byte[] CMD_SET_BAUDRATE = new byte[]{(byte) 0x14, (byte) 0x01};
    /**
     * 设置指纹重复检查
     */
    public final static byte[] CMD_SET_DUPLICATION_CHECK = new byte[]{(byte) 0x15, (byte) 0x01};
    /**
     * 获取指纹重复检查状态
     */
    public final static byte[] CMD_GET_DUPLICATION_CHECK = new byte[]{(byte) 0x16, (byte) 0x01};
    /**
     * 控制模块进入待机状态
     */
    public final static byte[] CMD_ENTER_STADBY_MODE = new byte[]{(byte) 0x17, (byte) 0x01};
    /**
     * 注册指纹并缓存在RAM中
     */
    public final static byte[] CMD_ENROLL_AND_STORE_IN_RAM = new byte[]{(byte) 0x18, (byte) 0x01};
    /**
     * 获取注册指纹的模板数据
     */
    public final static byte[] CMD_GET_ENROLL_DATA = new byte[]{(byte) 0x19, (byte) 0x01};
    /**
     * 采集图像并获取指纹特征值上传
     */
    public final static byte[] CMD_GET_FEATURE_DATA_OF_CAPTURED_FP = new byte[]{(byte) 0x1A, (byte) 0x01};
    /**
     * 下载特征数据与采集的指纹进行比对
     */
    public final static byte[] CMD_VERIFY_DOWNLOADED_FEATURE_WIDTH_CAPTURED_FP = new byte[]{(byte) 0x1B, (byte) 0x01};
    /**
     * 下载两枚特征数据与采集的指纹进行比对
     */
    public final static byte[] CMD_IDENTIFY_DOWNLOADED_FEATURE_WIDTH_CAPTURED_FP = new byte[]{(byte) 0x1C, (byte) 0x01};
    /**
     * 获取设备名称
     */
    public final static byte[] CMD_GET_DEVICE_NAME = new byte[]{(byte) 0x21, (byte) 0x01};
    /**
     * 背景光控制
     */
    public final static byte[] CMD_SENSOR_LED_CONTROL = new byte[]{(byte) 0x24, (byte) 0x01};
    /**
     * 连续识别
     */
    public final static byte[] CMD_IDENTIFY_FREE = new byte[]{(byte) 0x25, (byte) 0x01};
    /**
     * 设置通讯口令密码
     */
    public final static byte[] CMD_SET_DEVICE_PASSWORD = new byte[]{(byte) 0x26, (byte) 0x01};
    /**
     * 验证通讯口令密码
     */
    public final static byte[] CMD_VERIFY_DEVICE_PASSWORD = new byte[]{(byte) 0x27, (byte) 0x01};
    /**
     * 获取已注册指纹数
     */
    public final static byte[] CMD_GET_ENROLL_COUNT = new byte[]{(byte) 0x28, (byte) 0x01};
    /**
     * 更新模板
     */
    public final static byte[] CMD_CHANGE_TEMPLATE = new byte[]{(byte) 0x29, (byte) 0x01};
    /**
     * 设置工作模式
     */
    public final static byte[] CMD_SET_OPERATION_MODE = new byte[]{(byte) 0x1D, (byte) 0x01};
    /**
     * 获取工作模式
     */
    public final static byte[] CMD_GET_OPERATION_MODE = new byte[]{(byte) 0x1E, (byte) 0x01};
    /**
     * 取消操作
     */
    public final static byte[] CMD_CANCEL = new byte[]{(byte) 0x30, (byte) 0x01};
    /**
     * 指纹连接测试
     */
    public final static byte[] CMD_TEST_CONNECTION = new byte[]{(byte) 0x50, (byte) 0x01};
    /**
     * 发生错误时的响应（通讯错误、误码等）
     */
    public final static byte[] CMD_INCORRECT = new byte[]{(byte) 0x60, (byte) 0x01};


    /**指纹录入代码*/
    /**
     * 指纹超时
     */
    public final static byte[] BACK_DATA_TIMEOUT = new byte[]{(byte) 0x23, (byte) 0x00};
    /**
     * 录入指纹重复
     */
    public final static byte[] BACK_DATA_ENROLL_REPEATE = new byte[]{(byte) 0x19, (byte) 0x00};
    /**
     * 录入指纹失败
     */
    public final static byte[] BACK_DATA_ENROLL_FAIL = new byte[]{(byte) 0x30, (byte) 0x00};
    /**
     * 录入指纹图形错误ERR_BAD_QUALITY
     */
    public final static byte[] BACK_DATA_ENROLL_ERR_BAD_QUALITY = new byte[]{(byte) 0x21, (byte) 0x00};
    /**
     * 指纹ID已占用
     */
    public final static byte[] BACK_DATA_ENROLL_ID_OCCYUP = new byte[]{(byte) 0x14, (byte) 0x00};


    /**
     * 指纹超时
     */
    public final static String MSG_ENROLL_TIMEOUT = "指纹等待超时";
    /**
     * 录入指纹重复
     */
    public final static String MSG_ENROLL_REPEATE = "指纹重复";
    /**
     * 入指纹失败
     */
    public final static String MSG_ENROLL_FAIL = "指纹录入失败";
    /**
     * 录入指纹图形错误ERR_BAD_QUALITY
     */
    public final static String MSG_ENROLL_BAD_QUALITY = "指纹录入错误";
    /**
     * 指纹ID已占用
     */
    public final static String MSG_ENROLL_ID_OCCYUP = "指纹ID重复";
    /**
     * 指纹ID已占用
     */
    public final static String MSG_ENROLL_EMPTY_ID_NOEXIST = "无可用的指纹ID";


    /**响应码*/
    /**
     * 指令处理成功。
     */
    public final static byte[] RET_SUCCESS = new byte[]{(byte) 0x00, (byte) 0x00};
    /**
     * 指令处理失败。
     */
    public final static byte[] RET_FAIL = new byte[]{(byte) 0x01, (byte) 0x00};


    /**验证代码*/
    /**
     * 指纹验证成功
     */
    public final static byte[] BACK_DATA_VERIFY_SUCCESS = new byte[]{(byte) 0x01, (byte) 0x00};
    /**
     * 指纹验证失败
     */
    public final static byte[] BACK_DATA_VERIFY_FAIL = new byte[]{(byte) 0x11, (byte) 0x00};
    /**
     * 指纹识别失败
     */
    public final static byte[] BACK_DATA_IDENTIFY_FAIL = new byte[]{(byte) 0x12, (byte) 0x00};


    /**
     * 第一次指纹输入等待状态
     */
    public final static byte[] GD_NEED_FIRST_SWEEP = new byte[]{(byte) 0xF1, (byte) 0xFF};
    /**
     * 第二次指纹输入等待状态
     */
    public final static byte[] GD_NEED_SECOND_SWEEP = new byte[]{(byte) 0xF2, (byte) 0xFF};
    /**
     * 第三次指纹输入等待状态
     */
    public final static byte[] GD_NEED_THIRD_SWEEP = new byte[]{(byte) 0xF3, (byte) 0xFF};
    /**
     * 离开手指
     */
    public final static byte[] GD_NEED_RELEASE_FINGER = new byte[]{(byte) 0xF4, (byte) 0xFF};
    /**
     * 发出FingerDetect指令时刻，检测到有指纹输入
     */
    public final static byte[] GD_DETECT_FINGER = new byte[]{(byte) 0x01, (byte) 0x00};
    /**
     * 发出FingerDetect指令时刻，没检测到有指纹输入
     */
    public final static byte[] GD_NO_DETECT_FINGER = new byte[]{(byte) 0x00, (byte) 0x00};
    /** */
    public final static byte GD_DOWNLOAD_SUCCESS = (byte) 0xA1;
    /** */
    public final static byte GD_TEMPLATE_NOT_EMPTY = 0x01;
    /** */
    public final static byte ERR_TEMPLATE_EMPTY = 0x00;


    /**
     * Access Reader工作模式
     */
    public final static int OPERATION_MODE_ACCESS_READER = 1;
    /**
     * command工作模式
     */
    public final static int OPERATION_MODE_COMMAND = 2;


    /**
     * LED状态-亮
     */
    public final static int LED_ON = 1;
    /**
     * LED状态-灭
     */
    public final static int LED_OFF = 0;


    /**
     * 检测指纹重复
     */
    public final static int DUPLICATION_CHECK = 1;
    /**
     * 不检测指纹重复
     */
    public final static int DUPLICATION_NOCHECK = 0;


    /**
     * 波特率9600bps
     */
    public final static int BAUDRATE_9600 = 1;
    /**
     * 波特率19200bps
     */
    public final static int BAUDRATE_19200 = 2;
    /**
     * 波特率38400bps
     */
    public final static int BAUDRATE_38400 = 3;
    /**
     * 波特率57600bps
     */
    public final static int BAUDRATE_57600 = 4;
    /**
     * 波特率115200bps
     */
    public final static int BAUDRATE_115200 = 5;


    /**
     * 与指定号码中Template的1:1比对失败
     */
    public final static byte ERR_VERIFY = 0x11;
    /**
     * 已进行1:N比对，但相同的Template不存在
     */
    public final static byte ERR_IDENTIFY = 0x12;
    /**
     * 在指定号码中不存在已登记的Template
     */
    public final static byte ERR_TMPL_EMPTY = 0x13;
    /**
     * 指定号码中已存在Template
     */
    public final static byte ERR_TMPL_NOT_EMPTY = 0x14;
    /**
     * 不存在已登记的template
     */
    public final static byte ERR_ALL_TMPL_EMPTY = 0x15;
    /**
     * 不存在可登记的template id
     */
    public final static byte ERR_EMPTY_ID_NOEXIST = 0x16;
    /**
     * 不存在已损坏的template
     */
    public final static byte ERR_BROKEN_ID_NOEXIST = 0x17;
    /**
     * 指定的template data无效
     */
    public final static byte ERR_INVALID_TMPL_DATA = 0x18;
    /**
     * 该指纹已登记过
     */
    public final static byte ERR_DUPLICATION_ID = 0x19;
    /**
     * 指纹图像质量不好
     */
    public final static byte ERR_BAD_QUALITY = 0x21;
    /**
     * 在timeout时间内没检测到指纹的输入
     */
    public final static byte ERR_TIMEOUT = 0x23;
    /**
     * 点击了其他鉴权方式
     */
    public final static byte FINGER_OTHER_WAY = 0x25;
    /**
     * 没有进行设备密码确认。若设备密码已被设定且没有利用verify device password进行确认，则除了test connection、verify device password指令外的所有指令返回该错误码
     */
    public final static byte ERR_NOT_AUTHORIZED = 0x24;
    /**
     * 登记template的制作失败
     */
    public final static byte ERR_GENERALIZE = 0x30;
    /**
     * 指令已被取消
     */
    public final static byte ERR_FP_CANCEL = 0x41;
    /**
     * 软件内部错误
     */
    public final static byte ERR_INTERNAL = 0x50;
    /**
     * 软件内部错误
     */
    public final static byte ERR_MEMORY = 0x51;
    /**
     * 软件内部错误
     */
    public final static byte ERR_EXCEPTION = 0x52;
    /**
     * 指定的template id无效
     */
    public final static byte ERR_INVALID_TMPL_NO = 0x60;
    /**
     * 指定的security值无效
     */
    public final static byte ERR_INVALID_SEC_VAL = 0x61;
    /**
     * 指定的timeout值无效
     */
    public final static byte ERR_INVALID_TIMEOUT_VAL = 0x62;
    /**
     * 指定的波特率值无效
     */
    public final static byte ERR_INVALID_BAUDRATE = 0x63;
    /**
     * 指定的重复检测值无效
     */
    public final static byte ERR_INVALID_DUP_VAL = 0x65;
    /**
     * 使用了无效参数
     */
    public final static byte ERR_INVALID_PARAM = 0x70;
    /**
     * 在identifyFree指令执行过程中，识别不成功的指纹未离开
     */
    public final static byte ERR_NO_RELEASE = 0x71;
    /**
     * 指定的工作方式不对
     */
    public final static byte ERR_INVALID_OPERATION_MODE = 0x72;

    public static String getErrMsg(int errNo) {
        switch (errNo) {
            case ERR_VERIFY:
                return "指纹比对失败";
            case ERR_IDENTIFY:
                return "指纹验证失败";
            case ERR_TMPL_EMPTY:
                return "指纹不存在";
            case ERR_TMPL_NOT_EMPTY:
                return "指纹已存在";
            case ERR_ALL_TMPL_EMPTY:
                return "指纹不存在";
            case ERR_EMPTY_ID_NOEXIST:
                return "指纹已满";
            case ERR_BROKEN_ID_NOEXIST:
                return "指纹未损坏";
            case ERR_INVALID_TMPL_DATA:
                return "指纹数据错误";
            case ERR_DUPLICATION_ID:
                return "指纹重复";
            case ERR_BAD_QUALITY:
                return "指纹图像错误";
            case ERR_TIMEOUT:
                return "已超时";
            case FINGER_OTHER_WAY:
                return "";
            case ERR_NOT_AUTHORIZED:
                return "指纹模块未验证";
            case ERR_GENERALIZE:
                return "指纹特征生成失败";
            case ERR_FP_CANCEL:
                return "操作已取消";
            case ERR_INTERNAL:
                return "未知错误";
            case ERR_MEMORY:
                return "未知错误";
            case ERR_EXCEPTION:
                return "未知错误";
            case ERR_INVALID_TMPL_NO:
                return "指纹编号错误";
            case ERR_INVALID_SEC_VAL:
                return "安全等级无效";
            case ERR_INVALID_TIMEOUT_VAL:
                return "超时值无效";
            case ERR_INVALID_BAUDRATE:
                return "波特率无效";
            case ERR_INVALID_DUP_VAL:
                return "重复检查值无效";
            case ERR_INVALID_PARAM:
                return "参数错误";
            case ERR_NO_RELEASE:
                return "手指未移开";
            case ERR_INVALID_OPERATION_MODE:
                return "工作模式错误";
            default:
                return "未知错误";
        }
    }
}
