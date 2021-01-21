package com.company.crowd.constant;

import java.io.File;

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
    String MESSAGE_ROLE_NAME_EXIST = "抱歉！已经存在该名称的角色！";
    String MESSAGE_LOGINACCT_ISEMPTY = "管理员账号不可为空！";
    String MESSAGE_ROLE_NAME_ISEMPTY = "角色名称不可为空！";
    String MESSAGE_MENU_NAME_ISEMPTY = "菜单名称不可为空！";
    String MESSAGE_CODE_NOT_EXISTS = "抱歉！验证码不存在！";
    String MESSAGE_CODE_INVALID = "抱歉！验证码无效！";
    String MESSAGE_MEMBER_NOTEXISTS = "该用户不存在，请先注册！";
    String MESSAGE_HEADER_PIC_EMPTY = "项目头图为空，请先上传项目头图！";
    String MESSAGE_DETAIL_PIC_EMPTY = "项目详情图为空，请先上传项目详情图！";
    String MESSAGE_HEADER_PIC_UPLOAD_FAILED = "项目头图上传失败！";
    String MESSAGE_DETAIL_PIC_UPLOAD_FAILED = "项目详情图上传失败！";
    String MESSAGE_TEMPLE_PROJECT_MISSING = "临时存储的Project对象丢失！！";


    // 保存到redis的手机号的验证码的key的前缀
    String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX_";
    String ATTR_NAME_LOGIN_MEMBER = "loginMember";
    String ATTR_NAME_EXCEPTION = "exception";
    String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
    String ATTR_NAME_PAGE_INFO = "pageInfo";
    String ATTR_NAME_MESSAGE = "message";
    String ATTR_NAME_TEMPLE_PROJECT = "tempProject";
    /**
     * 上传图片路径 "/images/photo/"
     */
    String UPLOAD_IMAGE_PATH= File.separator + "images" + File.separator + "photo" + File.separator;
    /**
     * 图片保存路径
     */
    String UPLOAD_IMAGE_SAVE_PATH = "photo" + File.separator;

}
