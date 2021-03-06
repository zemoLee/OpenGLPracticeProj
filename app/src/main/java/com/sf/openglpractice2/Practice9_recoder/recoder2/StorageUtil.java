package com.sf.openglpractice2.Practice9_recoder.recoder2;

import java.io.File;

import android.os.Environment;

/**
 * Created By Chengjunsen on 2018/9/5
 */
public class StorageUtil {
    public static String getDirName() {
        return "SCamera";
    }

    public static String getSDPath() {
        // 判断是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return Environment.getRootDirectory().getAbsolutePath();
    }

    public static String getImagePath() {
        String path = getSDPath() + "/" + getDirName() + "/image/";
        checkDirExist(path);
        return path;
    }

    public static String getVedioPath(boolean checkDirExist) {
        String path = getSDPath() + "/" + getDirName() + "/video/";
        checkDirExist(path);
        return path;
    }

    public static boolean checkDirExist(String path) {
        File mDir = new File(path);
        if (!mDir.exists()) {
            return mDir.mkdirs();
        }
        return true;
    }
}