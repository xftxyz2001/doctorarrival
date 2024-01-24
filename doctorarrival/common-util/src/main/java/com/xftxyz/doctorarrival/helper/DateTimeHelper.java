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

    // 格式化
    public static String formatDate(Date date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(calendar.getTime());
    }

    // public static Date parseDate(String dateString, String pattern) {
    //     SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    //     try {
    //         return dateFormat.parse(dateString);
    //     } catch (Exception e) {
    //         return null;
    //     }
    // }

    // 修改时间
    public static Date addMinutes(Date date, int minutes) {
        return new Date(date.getTime() + minutes * Constants.MINUTE_IN_MILLIS);
    }

    public static Date addDays(Date date, int days) {
        return new Date(date.getTime() + days * Constants.DAY_IN_MILLIS);
    }

    public static Date addMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date addYears(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getTheDayEndDate(Date date) {
        return new Date(date.getYear(), date.getMonth(), date.getDate(), 23, 59, 59);
    }

    public static Date getTheMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getTheYearEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return new Date(calendar.getTimeInMillis());
    }

}
