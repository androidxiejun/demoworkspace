package com.example.serialtest;

import android.util.Log;

/**
 * Created by AndroidXJ on 2019/4/29.
 */

public class Util {
    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static char szstr2bin[] = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0, 0, 0, 0,
            0, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };

    public static byte[] str2byte(String str) {
        int length = str.length();
        if (length < 1) return null;
        if (length % 2 != 0) return null;
        byte[] result = new byte[str.length() / 2];
        for (int i = 0; i < length; ) {
            char H = szstr2bin[str.charAt(i++) & 0xFF];
            char L = szstr2bin[str.charAt(i++) & 0xFF];
            result[(i / 2) - 1] = (byte) (H * 16 + L);
        }
        return result;
    }

    /**
     * 检测1-8号红外状态，以及四号位上的无线开关状态
     * @param data
     * @return
     */
    public static int doIt(byte data) {
        for (int j = 0; j < 8; j++) {
            int result = data & (0x01 << j);
            Log.i(MainActivity.TAG,"状态------"+result);
        }
        return 0;
    }
}
