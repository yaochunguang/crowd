package com.company.crowd.util;

import com.company.crowd.constant.CrowdConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: yaochunguang
 * @date: 2021-01-15 15:50
 * @description: 判断资源是否可以不登录访问
 **/
public class AccessPassResourcesUtil {

    /**
     * 不登录直接可以访问的资源
     */
    public static final Set<String> PASS_RES_SET = new HashSet<>();

    /**
     * 静态资源
     */
    public static final Set<String> STATIC_RES_SET = new HashSet<>();

    static {
        // 主页、注册、登录、退出、发送短信验证码
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/auth/member/to/reg/page");
        PASS_RES_SET.add("/auth/member/to/login/page");
        PASS_RES_SET.add("/auth/member/logout");
        PASS_RES_SET.add("/auth/member/do/login");
        PASS_RES_SET.add("/auth/do/member/register");
        PASS_RES_SET.add("/auth/member/send/short/message.json");

        // 静态资源不需要登录，可以直接访问
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }

    /**
     * 判断某个ServletPath是否对应一个静态资源
     * @param servletPath
     * @return
     */
    public static boolean judgeCurrentServletPathWetherStaticResource(String servletPath) {
        if (StringUtils.isEmpty(servletPath)) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        // 根据/切分servletPath
        String[] split = servletPath.split("/");
        // 考虑到第一个斜杠左边经过拆分后得到一个空字符串是数组的第一个元素，所以需 要使用下标 1 取第二个元素
        return STATIC_RES_SET.contains(split[1]);
    }
}
