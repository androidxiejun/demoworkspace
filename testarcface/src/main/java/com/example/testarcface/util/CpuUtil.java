package com.example.testarcface.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by AndroidXJ on 2019/8/22.
 */
public class CpuUtil {

    /**
     * 修改cpu模式和频率
     */
    public static void setCpuMsg() {
        try {
            File f = new File("/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor");
            OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(f));
            //conservative ondemand userspace powersave interactive performance
            os.write("userspace");
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File f = new File("/sys/devices/system/cpu/cpu0/cpufreq/scaling_setspeed");
            OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(f));
            //126000 216000 312000 408000 600000 696000 816000 1008000 1200000 1416000 1512000 1608000 1704000 1800000
            os.write("600000");
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//            Runtime.getRuntime().exec("echo userspace > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor");
//            Runtime.getRuntime().exec("echo 816000 > /sys/devices/system/cpu/cpu0/cpufreq/scaling_setspeed");
    }
}
