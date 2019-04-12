package com.example.librafingergenerallibrary.util;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;

/**
 * @author xyuxiao
 * @date 2018/2/7
 */
public class BaseUtil {
    /**
     * 获取当前时间
     */
    public static String getTimeInMillis() {
        return Calendar.getInstance().getTimeInMillis() + "";
    }

    /**
     * 对话框点击按钮之后是否关闭对话框
     *
     * @param dialogInterface
     * @param close
     */
    public static void canCloseDialog(DialogInterface dialogInterface, boolean close) {
        try {
            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialogInterface, close);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeight(ListView listView) {
        if (listView == null) {
            return;
        }
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getHostIP() {
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIp;
    }

    /**
     * 把格式化之后的时间还原
     *
     * @param time
     * @param oldTime
     * @return
     */
    public static long parseTime(String time, long oldTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return oldTime;
    }

    /**
     * 把格式化之后的时间还原
     *
     * @param time
     * @return
     */
    public static long parseTime(String time) {

        if(TextUtils.isEmpty(time))
            return 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取格式化之后的时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(Calendar.getInstance().getTime());
    }


    public static String getFormatTime2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取格式化之后的时间
     *
     * @return yyyyMMddHHmmss
     */
    public static String getSimpleFormatTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取格式化之后的时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return time == 0 ? "" : sdf.format(time);
    }

    /**
     * 获取格式化之后的时间
     *
     * @return yyyyMMddHHmmss
     */
    public static String getSimpleFormatTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return time == 0 ? "" : sdf.format(time);
    }

    /**
     * 保存错误日志到本地
     */
    public static void saveErrorMsgToLocal(String msg) {
        saveErrorMsgToLocal(msg, "error");
    }

    public static void saveErrorMsgToLocal(String msg, String fileName) {
        try {
            File file = new File(FileUtil.getPath() + File.separator + fileName + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(getFormatTime() + "---------->" + msg + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 使用adb shell命令来获取mac地址的方式
     *
     * @return
     */
    public static String getMacAddress() {
        String macSerial = null;
        String str = "";

        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/eth0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }

    /**
     * 保存cpu内存使用信息
     */
    public static void saveProcessRate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStreamWriter os = null;
                try {
                    String result;
                    File file_all = new File(FileUtil.getPath() + File.separator + "cpu_men_rate_all.txt");
                    if (!file_all.exists()) {
                        file_all.createNewFile();
                    }

                    os = new OutputStreamWriter(new FileOutputStream(file_all, true), "UTF-8");
                    Process p = Runtime.getRuntime().exec("top");
                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    while ((result = br.readLine()) != null) {
                        if (result.trim().length() < 1) {
                            continue;
                        } else {
                            if (result.contains("System")) {
                                os.write(getFormatTime() + "------------>" + result + "\n");
                                os.flush();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();
    }

    /**
     * 根据包名保存cpu内存使用信息
     */
    public static void saveProcessRate(String packageName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStreamWriter os = null;
                try {
                    String result;
                    File file_cpu = new File(FileUtil.getPath() + File.separator + "cpu_men_rate.txt");
                    if (!file_cpu.exists()) {
                        file_cpu.createNewFile();
                    }
                    os = new OutputStreamWriter(new FileOutputStream(file_cpu, true), "UTF-8");
                    Process p = Runtime.getRuntime().exec("top");
                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    while ((result = br.readLine()) != null) {
                        if (result.trim().length() < 1) {
                            continue;
                        } else {
                            if (result.contains("packageName")) {
                                os.write(getFormatTime() + "------------>" + result + "\n");
                                os.flush();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();
    }

    /**
     * 获取当前cpu使用率
     *
     * @return cpu使用率
     */
    public static String getCurCpu() {
        String result = "";
        try {
            Process p = Runtime.getRuntime().exec("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            result = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

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

//    /**
//     * 修改文字按下松开颜色更改
//     */
//    public static void setTextColorChangeWhenDownAndUp(final Context context, final View view,
//                                                       @ColorRes final int normalColor, @ColorRes final int pressColor) {
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    if (view instanceof TextView) {
//                        ((TextView) view).setTextColor(ContextCompat.getColor(context, pressColor));
//                    } else if (view instanceof Button) {
//                        ((Button) view).setTextColor(ContextCompat.getColor(context, pressColor));
//                    }
//                } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (view instanceof TextView) {
//                        ((TextView) view).setTextColor(ContextCompat.getColor(context, normalColor));
//                    } else if (view instanceof Button) {
//                        ((Button) view).setTextColor(ContextCompat.getColor(context, normalColor));
//                    }
//                } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
//                    if (view instanceof TextView) {
//                        ((TextView) view).setTextColor(ContextCompat.getColor(context, normalColor));
//                    } else if (view instanceof Button) {
//                        ((Button) view).setTextColor(ContextCompat.getColor(context, normalColor));
//                    }
//                }
//                return false;
//            }
//        });
//    }

//    /**
//     * 修改文字按下松开颜色更改
//     */
//    public static void setTextColorChangeWhenDownAndUp(final Context context, final View[] views,
//                                                       @ColorRes final int normalColor, @ColorRes final int pressColor) {
//        for (final View view : views) {
//            setTextColorChangeWhenDownAndUp(context, view, normalColor, pressColor);
//        }
//    }
}
