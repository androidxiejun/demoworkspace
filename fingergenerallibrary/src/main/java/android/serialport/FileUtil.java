package android.serialport;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xyuxiao on 2016/9/23.
 */
public class FileUtil {
    public static String dir;

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
            com.example.librafingergenerallibrary.util.Util.saveErrorMsgToLocal(e.getMessage(), "seriallibrary");
            System.out.println("复制单个文件操作出错");
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
        void onSuccess();

        void onFail();
    }
}
