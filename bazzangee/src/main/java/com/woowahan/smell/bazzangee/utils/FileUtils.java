package com.woowahan.smell.bazzangee.utils;

import java.io.File;

public class FileUtils {

    public static final int MAXIMUM_SIZE_MB = 1024 * 1024 * 10;

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);

        return "";
    }

    public static long getFileSize(String filename) {
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            System.out.println("File doesn\'t exist");
            return -1;
        }
        return file.length();
    }

    public static void makeDirs(String filePath) {
        File file = new File(filePath);
        // check if the pathname already exists
        // if not create it
        if(!file.exists()) {
            // create the full path name
            file.mkdirs();
        }
    }

}