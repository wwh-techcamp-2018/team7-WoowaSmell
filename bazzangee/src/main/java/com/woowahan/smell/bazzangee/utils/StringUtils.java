package com.woowahan.smell.bazzangee.utils;

public class StringUtils {
    private static final int TEXT_MAX_LENGTH = 200;

    public static boolean isValidTextLength(String str) {
        return str.length() <= TEXT_MAX_LENGTH;
    }
}
