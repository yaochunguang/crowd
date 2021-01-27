package com.company.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author: yaochunguang
 * @date: 2021-01-08 10:05
 * @description: 配置类
 **/
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {

    /**
     * 文件保存路径
     */
    @Value("${file.upload.diskPath}")
    private String diskPath;
    /**
     * 显示相对地址
     */
    @Value("${file.upload.relative}")
    private String fileRelativePath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 浏览器访问的地址
        String urlPath = "/auth/member/to/reg/page";
        // 目标视图名称，将来拼接： prefix: classpath:/templates/ suffix: .html
        String viewName = "member-reg";
        registry.addViewController(urlPath).setViewName(viewName);
        registry.addViewController("/auth/member/to/login/page").setViewName("member-login");
        registry.addViewController("/auth/member/to/center/page").setViewName("member-center");
        // 前往我的众筹项目
        registry.addViewController("/member/my/crowd").setViewName("member-crowd");
    }

    /**
     * 赋值文件读写权限
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileRelativePath).
                addResourceLocations("file:/" + diskPath);
    }
}
