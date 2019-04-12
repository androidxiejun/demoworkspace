package com.example.testcrash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
/**
 * 错误日志收集
 * -----------对错误日志进行上传
 */
public class CrashHandler implements UncaughtExceptionHandler, OnClickListener {
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String LOG_URI = "/sdcard/crash/"; //崩溃日志路径
    /**
     * CrashHandler实例
     */
    private static CrashHandler INSTANCE;
    private Context mContext;
    private String response = null;
    /**
     * 使用Properties来保存设备的信息和错误堆栈信息
     */
    private Properties mDeviceCrashInfo = new Properties();
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(ex);
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(null, ex);
        } else {
            ex.printStackTrace();
            // Sleep一会后结束程序
            // 来让线程停止一会是为了显示Toast信息给用户，然后Kill程序
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                CrashHandler.getInstance().handleExceptionByCatch(e);
                e.printStackTrace();
                Log.e("cmd", "Error : ", e);
            }
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(10);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        try {
            if (ex == null) {
                return true;
            }
            final String msg = ex.getLocalizedMessage();
            Writer info = new StringWriter();
            PrintWriter printWriter = new PrintWriter(info);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }

            // toString() 以字符串的形式返回该缓冲区的当前值。
            String res;
            if (response != null) {
                res = info.toString() + "response:" + response;
            } else {
                res = info.toString();
            }
            Log.e("=============>", res);
            final String result = res;
            printWriter.close();
            deleteOverDayLogs(LOG_URI);
            saveErrorMsgToLocal(result, getFormatTime2());
        } catch (Exception e) {
            CrashHandler.getInstance().handleExceptionByCatch(e);
        }
        return true;
    }

    /**
     * try catch捕获的错误信息的提交
     *
     * @param e
     */

    public void handleExceptionByCatch(Throwable e) {
        if (e != null) {
            handleException(e);
        }
    }

    @Override
    public void onClick(DialogInterface arg0, int arg1) {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    public static void saveErrorMsgToLocal(String msg, String fileName) {
        try {
            String pathFile=LOG_URI + File.separator;
            File rootFile=new File(pathFile);
            if(!rootFile.exists()){
                rootFile.mkdir();
            }
            File file = new File(pathFile + fileName + ".txt");
            Log.i("MainActivity", "file----------" + file.getPath());
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(getFormatTime() + "---------->" + msg + "\n");
            fileWriter.flush();
            fileWriter.close();
            Log.i("MainActivity", "file-保存成功");
        } catch (IOException e1) {
            e1.printStackTrace();
            Log.i("MainActivity", "file-保存失败："+e1.getMessage());
        }
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(Calendar.getInstance().getTime());
    }

    /**
     * 删除超过30天的日志
     * @param path  崩溃日志保存路径   /sdcard/guncabinet/crash/
     * @return
     */
    public static void deleteOverDayLogs(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) {
            Log.e("error", "空目录");
            return;
        }
        List<String> filesAllName = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            filesAllName.add(files[i].getName());
        }
        String nowDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for (int i=0;i<filesAllName.size();i++){
            String data=filesAllName.get(i);
            String oldDay=data.substring(0,data.indexOf("."));
            int dayLong=getTwoDay(nowDay,oldDay);
            if(dayLong>30){//超过30天则删除
                files[i].delete();
            }
        }
    }
    /**
     * 比较两个日期相差的天数 返回一个int
     *
     * @param nowDay 格式："2016-04-07"
     * @param oldDay 格式："2016-04-07"
     * @return
     */
    public static int getTwoDay(String nowDay, String oldDay) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            java.util.Date date = myFormatter.parse(nowDay);
            java.util.Date mydate = myFormatter.parse(oldDay);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return -1;
        }
        day = Math.abs(day);
        return (int) day;
    }

}
