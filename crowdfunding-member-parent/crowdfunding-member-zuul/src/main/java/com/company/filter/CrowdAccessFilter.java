package com.company.filter;

import com.company.crowd.constant.CrowdConstant;
import com.company.crowd.util.AccessPassResourcesUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: yaochunguang
 * @date: 2021-01-15 15:59
 * @description: Zuul拦截类
 **/
@Component
public class CrowdAccessFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(CrowdAccessFilter.class);

    @Override
    public String filterType() {
        // 这里返回"pre"意思是目标服务前执行过滤
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 获取RequestContext对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 获取当前请求
        HttpServletRequest request = requestContext.getRequest();
        // 获取servertPath
        String servletPath = request.getServletPath();
        boolean result = AccessPassResourcesUtil.PASS_RES_SET.contains(servletPath);
        if (result) {
            // 如果当前请求是可以直接放行的特定功能请求则返回false -- 放行
            return false;
        }
        // 判断当前请求是否为静态资源
        // 工具方法返回 true：说明当前请求是静态资源请求，取反为 false 表示放行不做登录 检查
        // 工具方法返回 false：说明当前请求不是可以放行的特定请求也不是静态资源，取反 为 true 表示需要做登录检查
        return !AccessPassResourcesUtil.judgeCurrentServletPathWetherStaticResource(servletPath);
    }

    @Override
    public Object run() throws ZuulException {
        // 获取当前请求对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        // 获取session
        HttpSession session = request.getSession();
        // 从session中获取已经登录的用户
        Object loginMember = session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        if (loginMember == null) {
            // currentContext中获取response对象
            HttpServletResponse response = currentContext.getResponse();
            session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
            // 重定向到auth-consumer工程的登录页面
            try {
                response.sendRedirect("/auth/member/to/login/page");
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return null;
    }
}
