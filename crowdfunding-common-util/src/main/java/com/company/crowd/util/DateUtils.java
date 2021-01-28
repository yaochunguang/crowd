package com.company.crowd.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: yaochunguang
 * @date: 2020-11-26 15:06
 * @description: 日期工具类
 **/
public class DateUtils {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    /**
     * 按照指定格式进行格式化时间
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        if (StringUtils.isEmpty(format)) {
            format = DATETIME_FORMAT;
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 将日期字符串转换成对应的日期，转换失败返回null
     * @param dateStr
     * @param format
     * @return
     */
    public static Date convertDateStrToDate(String dateStr, String format) {
        if (StringUtils.isEmpty(format)) {
            format = DATETIME_FORMAT;
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
           return null;
        }
    }
}
