package com.company.crowd.mvc.interceptor;

import com.company.crowd.constant.CrowdConstant;
import com.company.crowd.exception.AccessForbiddenException;
import com.company.entity.Admin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: yaochunguang
 * @date: 2020-11-19 19:32
 * @description: 登录拦截器
 **/
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 通过request对象回去session对象
        HttpSession session = request.getSession();
        // 从session中获取已经登录的信息
        Admin admin = (Admin)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        if (admin == null) {
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
        }
        // 如果admin不为空，则返回true
        return true;
    }
}
