package com.xftxyz.doctorarrival.common.helper;

import java.util.UUID;

public class FileHelper {

    /**
     * 生成uuid文件名
     *
     * @param fileName 原始文件名
     * @return uuid文件名
     */
    public static String uuidFileName(String fileName) {
        // 获取文件后缀名
        int lastIndexOfDot = fileName == null ? -1 : fileName.lastIndexOf(".");
        String suffix = lastIndexOfDot == -1 ? "" : fileName.substring(lastIndexOfDot);

        // 生成uuid文件名并返回
        String uuidWithoutDash = UUID.randomUUID().toString();
        return uuidWithoutDash + suffix;
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName 文件名
     * @return 文件后缀名
     */
    public static String getSuffix(String fileName) {
        int lastIndexOfDot = fileName == null ? -1 : fileName.lastIndexOf(".");
        return lastIndexOfDot == -1 ? "" : fileName.substring(lastIndexOfDot);
    }
}
