package com.xftxyz.doctorarrival.common.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {

    /**
     * 获取指定格式的当前时间字符串
     *
     * @param format 时间格式
     * @return 指定格式的当前时间
     */
    public static String getCurrentTime(String format) {
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
    }
}
