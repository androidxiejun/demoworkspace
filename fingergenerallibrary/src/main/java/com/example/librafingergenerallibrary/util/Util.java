package com.example.librafingergenerallibrary.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author xyuxiao
 * @date 2018/1/9
 */
public class Util {
    /**
     * 保存错误日志到本地
     */
    public static void saveErrorMsgToLocal(String msg) {
        saveErrorMsgToLocal(msg, "error");
    }

    public static void saveErrorMsgToLocal(String msg, String fileName) {
        try {
            File file = new File(android.serialport.FileUtil.getPath() + File.separator + fileName + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(getFormatTime() + "---------->" + msg + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e1) {
            Util.saveErrorMsgToLocal(e1.getMessage(), "seriallibrary");
            e1.printStackTrace();
        }
    }

    /**
     * 获取格式化之后的时间
     *
     * @return
     */
    public static String getFormatTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(Calendar.getInstance().getTime());
    }
}
