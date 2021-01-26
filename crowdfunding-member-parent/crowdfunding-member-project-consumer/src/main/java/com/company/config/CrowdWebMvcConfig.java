package com.company.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: yaochunguang
 * @date: 2021-01-08 10:05
 * @description: 配置类
 **/
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {

    /**上传地址*/
    @Value("${file.upload.path}")
    private String filePath;
    /**显示相对地址*/
    @Value("${file.upload.relative}")
    private String fileRelativePath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // view-controller是在project-consumer内部定义的，所以这里是一个不经过Zuul访问的地址，所以这个路径前面不加路由规则中定义的前缀：“/project”
        registry.addViewController("/agree/protocol/page").setViewName("project-agree");
        registry.addViewController("/launch/project/page").setViewName("project-launch");
        registry.addViewController("/return/info/page").setViewName("project-return");
        registry.addViewController("/create/confirm/page").setViewName("project-confirm");
        registry.addViewController("/create/success").setViewName("project-success");
        registry.addViewController("/show/detail").setViewName("project-detail");
    }

    /**
     * 赋值文件读写权限
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileRelativePath).
                addResourceLocations("file:/" + filePath);
    }
}
