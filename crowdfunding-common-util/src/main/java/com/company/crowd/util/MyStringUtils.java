package com.company.crowd.util;

/**
 * @author: yaochunguang
 * @date: 2020-11-26 15:10
 * @description: 字符串工具类
 **/
public class MyStringUtils {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return str != null || str.length() > 0;
    }
}
