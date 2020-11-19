package com.company.crowd.constant;

/**
 * @description: 常量类
 * @author: chunguang.yao
 * @date: 2020-11-19 0:03
 */
public interface CrowdConstant {

    String MESSAGE_LOGIN_FAILED = "抱歉！账号密码错误！请重新输入！";
    String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "抱歉！这个账号已经被使用了！";
    String MESSAGE_ACCESS_FORBIDEN = "请登录以后再访问！";
    String MESSAGE_STRING_INVALIDATE = "字符串不合法！请不要传入空字符串！";
    String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "系统错误：登录账号不唯一！";

    String ATTR_NAME_EXCEPTION = "exception";
    String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
    String ATTR_NAME_PAGE_INFO = "pageInfo";

}