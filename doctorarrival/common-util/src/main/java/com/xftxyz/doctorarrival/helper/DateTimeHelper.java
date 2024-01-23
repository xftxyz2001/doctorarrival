package com.xftxyz.doctorarrival.helper;

import com.xftxyz.doctorarrival.constant.Constants;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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


    public static Date getTodayStartDate() {
        Date today = new Date();
        return new Date(today.getYear(), today.getMonth(), today.getDate());
    }

    // 解析-分割的年月日为Date
    public static Date parseDateByDash(String dateString) {
        String[] dateArray = dateString.split("-");
        return new Date(Integer.parseInt(dateArray[0]) - 1900, Integer.parseInt(dateArray[1]) - 1, Integer.parseInt(dateArray[2]));
    }

    // 获取指定日期的星期
    public static String getDayOfWeek(Date workDate) {
        String[] dayOfWeekArray = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        return dayOfWeekArray[workDate.getDay()];
    }

    public static Date addDays(Date date, int days) {
        return new Date(date.getTime() + days * Constants.DAY_IN_MILLIS);
    }

    public static String formatDate(Date date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(calendar.getTime());
    }

    public static Object addMinutes(Date date, int minutes) {
        return new Date(date.getTime() + minutes * Constants.MINUTE_IN_MILLIS);
    }
}
