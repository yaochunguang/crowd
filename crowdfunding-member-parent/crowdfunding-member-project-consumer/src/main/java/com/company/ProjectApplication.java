package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: yaochunguang
 * @date: 2021-01-20 17:45
 * @description: 项目模块启动类
 **/
// 一定要加入EnableFeignClients注解，否则找不到远程的服务
@EnableFeignClients
@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }
}
