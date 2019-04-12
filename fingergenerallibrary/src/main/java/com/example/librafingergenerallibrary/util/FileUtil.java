package com.example.librafingergenerallibrary.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author xyuxiao
 * @date 2016/9/23
 */
public class FileUtil {
    public static String dir;

    /**
     * 储存空间的最小空闲值，再小就清理图片
     */
    private static int MIN_STORAGE_SIZE = 500;

    /**
     * 一次删除的图片数量
     */
    private static int PIC_CLEAR_ONCE = 50;

    static {
        dir = "x";
    }

    public static String getDirName() {
        return dir;
    }

    public static void setDirName(String name) {
        dir = name;
    }

    /**
     * 获取数据库路径
     */
    public static String getDBPath(Context context) {
        String path = "data" + Environment.getDataDirectory().getAbsolutePath() +
                File.separator + context.getPackageName() + File.separator + "databases";
        File pathFile = new File(path);
        if (!pathFile.exists() || !pathFile.isDirectory()) {
            pathFile.mkdir();
        }
        return path;
    }

    /**
     * 获取文件夹
     *
     * @param dir 文件夹名称
     * @return 路径
     */
    public static String getPath(String dir) {
        String path = FileUtil.getSDPath() + File.separator + dir;
        File pathFile = new File(path);
        if (!pathFile.exists() || !pathFile.isDirectory()) {
            pathFile.mkdir();
        }
        return path;
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }


    /**
     * 获取路径
     *
     * @return 路径
     */
    public static String getPath() {
        String path = FileUtil.getSDPath() + File.separator + dir;
        File pathFile = new File(path);
        if (!pathFile.exists() || !pathFile.isDirectory()) {
            pathFile.mkdir();
        }
        return path;
    }




//    /**
//     * 获取U盘
//     *
//     * @param context
//     * @return
//     */
//    public static FileSystem otgGet(Context context) {
//        UsbMassStorageDevice[] devices = UsbMassStorageDevice.getMassStorageDevices(context);
//        FileSystem currentFs = null;
//        //一般只有一个OTG借口，所以这里只取第一个
//        for (UsbMassStorageDevice device : devices) {
//            try {
//                device.init();
//                //如果设备不支持一些格式的U盘，这里会有异常
//                if (device == null || device.getPartitions() == null ||
//                        device.getPartitions().get(0) == null ||
//                        device.getPartitions().get(0).getFileSystem() == null) {
//                    return null;
//                }
//                currentFs = device.getPartitions().get(0).getFileSystem();
//                Log.e("OTG", "容量: " + currentFs.getCapacity());
//                Log.e("OTG", "已使用空间: " + currentFs.getOccupiedSpace());
//                Log.e("OTG", "剩余空间: " + currentFs.getFreeSpace());
//                Log.e("OTG", "block数目: " + currentFs.getChunkSize());
//            } catch (Exception e) {
//                return null;
//            }
//        }
//        return currentFs;
//    }


    /**
     * 获取文件夹下所有文件路径
     *
     * @param dir 文件夹名称
     * @return 数量
     */
    public static List<String> getAllFilePath(String dir) {
        List<String> filePaths = new ArrayList<>();
        File fileDir = new File(getPath(dir));
        if (fileDir.isDirectory()) {
            String[] children = fileDir.list();
            for (int i = 0; i < children.length; i++) {
                filePaths.add(getPath(dir) + File.separator + children[i]);
            }
        }
        return filePaths;
    }

    /**
     * 获取文件夹下所有文件路径
     *
     * @return 数量
     */
    public static List<String> getAllFilePath() {
        List<String> filePaths = new ArrayList<>();
        File fileDir = new File(getPath());
        if (fileDir.isDirectory()) {
            String[] children = fileDir.list();
            for (int i = 0; i < children.length; i++) {
                filePaths.add(getPath() + File.separator + children[i]);
            }
        }
        return filePaths;
    }

    /**
     * 获取文件夹下文件数量
     *
     * @param dir 文件夹名称
     * @return 数量
     */
    public static int getFileNumber(String dir) {
        File fileDir = new File(getPath(dir));
        if (fileDir.isDirectory()) {
            String[] children = fileDir.list();
            return children.length;
        }
        return 0;
    }

    /**
     * 获取文件夹下文件数量
     *
     * @return 数量
     */
    public static int getFileNumber() {
        File fileDir = new File(getPath());
        if (fileDir.isDirectory()) {
            String[] children = fileDir.list();
            return children.length;
        }
        return 0;
    }

    /**
     * 获取文件夹下最新的文件
     *
     * @param dir 文件夹名称
     * @return 文件路径
     */
    public static String getLastFilePath(String dir, String type) {
        List<String> filePaths = getAllFilePath(dir);
        getOrderFilePathByName(filePaths, type);
        return filePaths.get(filePaths.size() - 1);
    }

    /**
     * 获取文件夹下最新的文件
     *
     * @return 文件路径
     */
    public static String getLastFilePath(String type) {
        List<String> filePaths = getAllFilePath();
        getOrderFilePathByName(filePaths, type);
        return filePaths.get(0);
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除文件夹下所有文件
     *
     * @param dir 文件夹名称
     */
    public static void deleteDir(String dir) {
        File fileDir = new File(getPath(dir));
        if (fileDir.isDirectory()) {
            String[] children = fileDir.list();
            for (int i = 0; i < children.length; i++) {
                new File(fileDir, children[i]).delete();
            }
        }
    }

    /**
     * 删除文件夹下所有文件
     */
    public static void deleteDir() {
        File fileDir = new File(getPath());
        if (fileDir.isDirectory()) {
            String[] children = fileDir.list();
            for (int i = 0; i < children.length; i++) {
                new File(fileDir, children[i]).delete();
            }
        }
    }

    /**
     * 删除文件夹下所有文件
     */
    public static void deleteDir(OnFileDone onFileDone) {
        File fileDir = new File(getPath());
        if (fileDir.isDirectory()) {
            String[] children = fileDir.list();
            for (int i = 0; i < children.length; i++) {
                new File(fileDir, children[i]).delete();
            }
        }
        onFileDone.onSuccess();
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String
     * @param newPath String
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 传入文件地址,对文件地址进行排序
     *
     * @param filePaths
     * @param type
     */
    public static void getOrderFilePathByName(List<String> filePaths, String type) {
        List<Long> filepaths = new ArrayList<>();
        for (String filePath : filePaths) {
            filepaths.add(Long.valueOf(
                    filePath.replace(getPath() + File.separator, "").replace(type, "")));
        }
        Collections.sort(filepaths, Collections.<Long>reverseOrder());
        filePaths.clear();
        for (Long filepath : filepaths) {
            filePaths.add(FileUtil.getPath() + File.separator + filepath + type);
        }
    }


    public static String getPicPath() {
        String filePath = getPath() + File.separator + "pic";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        return filePath;
    }


    public interface OnFileDone {

        /**
         * 成功
         */
        void onSuccess();

        /**
         * 失败
         */
        void onFail();
    }

    /**
     * 删除文件夹
     *
     * @param folder
     */
    public static void deleteDirectory(File folder) {
        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files == null) {
                return;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        folder.delete();
    }

    /**
     * 图片过多的删除
     */
    public static void checkStorage() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Camera App");
        FileUtil.deleteDirectory(mediaStorageDir);
        String state = Environment.getExternalStorageState();

        File lostDir = new File(Environment.getExternalStorageDirectory() + File.separator + "LOST.DIR");
        if (lostDir.exists() && lostDir.isDirectory()) {
            File[] fileList = lostDir.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                fileList[i].delete();
            }
        }
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            long size = availCount * blockSize / (1024 * 1024);
//            LogUtils.e("", "可用的block数目：:" + availCount + ",剩余空间:" + size + "MB");
            if (size < MIN_STORAGE_SIZE) {
                File fileDir = new File(FileUtil.getPath());
                if (fileDir.exists()) {
                    List<File> fileList = listFileSortByModifyTime(fileDir.getPath());
                    int count = fileList.size();
                    if (count > PIC_CLEAR_ONCE) {
                        count = PIC_CLEAR_ONCE;
                    }
                    for (int i = 0; i < count; i++) {
                        fileList.get(i).delete();
                    }
                }
            }
        }
    }


    /**
     * 获取目录下所有文件(按时间排序)
     *
     * @param path
     * @return
     */
    public static List<File> listFileSortByModifyTime(String path) {
        List<File> list = getFiles(path, new ArrayList<File>());
        if (list != null && list.size() > 0) {
            Collections.sort(list, new Comparator<File>() {
                @Override
                public int compare(File file, File newFile) {
                    if (file.lastModified() < newFile.lastModified()) {
                        return -1;
                    } else if (file.lastModified() == newFile.lastModified()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        }
        return list;
    }


    /**
     * 获取目录下所有文件
     *
     * @param realpath
     * @param files
     * @return
     */
    public static List<File> getFiles(String realpath, List<File> files) {
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), files);
                } else {
                    files.add(file);
                }
            }
        }
        return files;
    }

}
