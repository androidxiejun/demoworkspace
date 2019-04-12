package com.example.librafingergenerallibrary.util;

/**
 * @author: 范建海
 * @createTime: 2016/10/30 13:49
 * @className: RadixUtil
 * @description: 十六进制数相关工具类
 * @changed by:
 */
public class RadixUtil {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final static byte[] HEX = "0123456789ABCDEF".getBytes();

    /**
     * 16进制转byte数组
     *
     * @param data 16进制字符串
     * @return byte数组
     * @throws Exception 转化失败的异常
     */
    public static byte[] hex2Bytes(final String data) throws Exception {
        final int len = data.length();

        if ((len & 0x01) != 0) {
            throw new Exception("Odd number of characters.");
        }

        final byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data.charAt(j), j) << 4;
            j++;
            f = f | toDigit(data.charAt(j), j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return out;
    }

    /**
     * bytes数组转16进制String
     *
     * @param data bytes数组
     * @return 转化结果
     */
    public static String bytes2Hex(final byte[] data) {
        return bytes2Hex(data, true);
    }

    /**
     * bytes数组转16进制String
     *
     * @param data        bytes数组
     * @param toLowerCase 是否小写
     * @return 转化结果
     */
    public static String bytes2Hex(final byte[] data, final boolean toLowerCase) {
        return bytes2Hex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * bytes数组转16进制String
     *
     * @param data     bytes数组
     * @param toDigits DIGITS_LOWER或DIGITS_UPPER
     * @return 转化结果
     */
    private static String bytes2Hex(final byte[] data, final char[] toDigits) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return new String(out);
    }

    /**
     * 16转化为数字
     *
     * @param ch    16进制
     * @param index 索引
     * @return 转化结果
     * @throws Exception 转化失败异常
     */
    private static int toDigit(final char ch, final int index)
            throws Exception {
        final int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new Exception("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }

    /**
     * 将short 转化成字节
     */
    public static byte[] shortToByte(short shortValue) {
        byte[] b = new byte[2];
        for (int i = 0; i < 2; i++) {
            b[1 - i] = (byte) (shortValue >> 8 * (1 - i) & 0xFF);
        }
        return b;
    }

    /**
     * 将Byte 转换成 short
     */
    public static short byteToShort(byte[] b) {
        short shortValue = 0;
        for (int i = 0; i < b.length; i++) {
            shortValue += (b[i] & 0xFF) << (8 * (1 - i));
        }
        return shortValue;
    }

    /**
     * 将byte数组转换为16位int
     *
     * @param res byte[]
     * @return int
     */
    public static int byte2int(byte[] res) {
        if (res != null) {
            // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
            int targets;
            if (res.length == 1) {
                targets = (res[0] & 0xff);
            } else {
                targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00);
            }
            return targets;
        }
        return 0;
    }

    /**
     * 将byte转换为16位int
     *
     * @param res byte[]
     * @return int
     */
    public static int byte2int(byte res) {
        return (res & 0xff);
    }

    /**
     * 求CKS
     *
     * @param frameBytes
     * @return
     */
    public static byte countCheckSum(byte[] frameBytes) {
        byte result = 0;
        for (int i = 0; i < frameBytes.length - 1; i++) {
            result += frameBytes[i];
        }
        return result;
    }

    /**
     * 求CKS
     *
     * @param packet
     * @param length
     * @return
     */
    public static byte[] checkSum(byte[] packet, int length) {
        short result = 0;
        for (int i = 0; i < length - 1; i++) {
            int a = (int) packet[i];
            a = a & 0xff;
            result += a;
        }
        return shortToByte(result);
    }

    public static byte[] getCrc(byte[] data) throws Exception {
        int high;
        int flag;

        // 16位寄存器，所有数位均为1
        int wcrc = 0xffff;
        for (int i = 0; i < data.length; i++) {
            // 16 位寄存器的高位字节
            high = wcrc >> 8;
            // 取被校验串的一个字节与 16 位寄存器的高位字节进行“异或”运算
            wcrc = high ^ data[i];

            for (int j = 0; j < 8; j++) {
                flag = wcrc & 0x0001;
                // 把这个 16 寄存器向右移一位
                wcrc = wcrc >> 1;
                // 若向右(标记位)移出的数位是 1,则生成多项式 1010 0000 0000 0001 和这个寄存器进行“异或”运算
                if (flag == 1) {
                    wcrc ^= 0xa001;
                }
            }
        }
        return hex2Bytes(Integer.toHexString(wcrc));
    }

    /**
     * 将字节数组转化成字符
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 十六进制转二进制
     *
     * @param hexString
     * @return
     */
    public static String hex2binary(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * 从字节数组到十六进制字符串转换
     */
    public static String toHexString(byte[] b) {
        byte[] buff = new byte[2 * b.length];
        for (int i = 0; i < b.length; i++) {
            buff[2 * i] = HEX[(b[i] >> 4) & 0x0f];
            buff[2 * i + 1] = HEX[b[i] & 0x0f];
        }
        return new String(buff);
    }

    /**
     * 从十六进制字符串到字节数组转换
     */
    public static byte[] toBytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a') {
            return (c - 'a' + 10) & 0x0f;
        }
        if (c >= 'A') {
            return (c - 'A' + 10) & 0x0f;
        }
        return (c - '0') & 0x0f;
    }

    /**
     * int转16进制
     *
     * @param integer
     * @return
     */
    public static String intToHex(int integer) {
        StringBuffer buf = new StringBuffer(2);
        if ((integer & 0xff) < 0x10) {
            buf.append("0");
        }
        buf.append(Long.toString(integer & 0xff, 16));
        return buf.toString();
    }

    /**
     * 16进制转10进制
     */
    public static Integer hexToInt(String hexString) {
        return Integer.parseInt(hexString, 16);
    }
}
