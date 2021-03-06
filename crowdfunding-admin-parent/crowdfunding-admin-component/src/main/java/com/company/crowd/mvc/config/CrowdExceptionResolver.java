package com.company.crowd.mvc.config;

import com.company.crowd.constant.CrowdConstant;
import com.company.crowd.exception.LoginAcctAlreadyInUseException;
import com.company.crowd.exception.LoginFailedException;
import com.company.crowd.exception.RoleExistException;
import com.company.crowd.util.CrowdUtil;
import com.company.crowd.util.ResultEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * // @ControllerAdvice表示当前类是一个基于注解的异常处理器类
 *
 * @author: yaochunguang
 * @date: 2020-11-17 16:29
 * @description: 基于注解的异常处理器类
 **/
@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resolveMathException(ArithmeticException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(NullPointerException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 将一个具体的异常类型和一个方法关联起来
     *
     * @param viewName  异常处理完成后要去的页面
     * @param exception 实际捕获到的异常类型
     * @param request   当前请求对象
     * @param response  当前响应对象
     * @return
     * @throws IOException
     * @ExceptionHandler
     */
    private ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.判断当前请求类型
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        // 2.如果是Ajax请求
        if (judgeResult) {
            // 3.创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            // 4.创建Gson对象
            Gson gson = new Gson();
            // 5.将ResultEntity对象转换为JSON字符串
            String json = gson.toJson(resultEntity);
            // 6.将JSON字符串作为响应体返回给浏览器
            response.getWriter().write(json);
            // 7.由于上面已经通过原生的response对象返回了响应，所以不提供ModelAndView对象
            return null;
        }
        // 8.如果不是Ajax请求则创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        // 9.将Exception对象存入模型
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        // 10.设置对应的视图名称
        modelAndView.setViewName(viewName);
        // 11.返回modelAndView对象
        return modelAndView;
    }

    /**
     * 登录异常处理
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(LoginFailedException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 新增管理员异常处理
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(LoginAcctAlreadyInUseException exception, HttpServletRequest request,
                                                              HttpServletResponse response) throws IOException {
        // 只是指定当前异常对应的页面即可
        String viewName = "admin-add";
        return commonResolve(viewName, exception, request, response);
    }

    /**
     * 新增橘色异常处理
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = RoleExistException.class)
    public ModelAndView resolveRoleExistException(RoleExistException exception, HttpServletRequest request,
                                                  HttpServletResponse response) throws IOException {
        // 只是指定当前异常对应的页面即可
        String viewName = "role-add";
        return commonResolve(viewName, exception, request, response);
    }
}
