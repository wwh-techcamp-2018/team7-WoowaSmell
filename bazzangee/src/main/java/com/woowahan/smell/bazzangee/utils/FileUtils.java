package com.woowahan.smell.bazzangee.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class FileUtils {
    public static final int MAXIMUM_SIZE_MB = 1024 * 1024 * 10;
    public static final String[] IMAGE_EXT = new String[] { "jpg", "jpeg",
            "gif", "png", "bmp" };

    public static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        return "";
    }

    public static long getFileSizeAboutMB(String filename) {
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            System.out.println("File doesn\'t exist");
            return -1;
        }
        return file.length() / (1024 * 1024);
    }

    public static void makeDirs(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static boolean isExceedSize(MultipartFile multipartFile) {
        return getFileSizeAboutMB(multipartFile.getName()) > FileUtils.MAXIMUM_SIZE_MB;
    }

    public static boolean isValidImageExt(String fileName) {
        String fileExtension = getFileExtension(fileName);
        System.out.println(fileExtension);
        for (String exts : IMAGE_EXT) {
            if (exts.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;

    }
}