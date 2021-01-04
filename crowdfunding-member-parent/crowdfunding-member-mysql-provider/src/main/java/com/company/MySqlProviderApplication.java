package com.company;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: yaochunguang
 * @date: 2021-01-04 16:16
 * @description: mysql服务
 **/
@MapperScan("com.company.crowd.mapper")
@SpringBootApplication
public class MySqlProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySqlProviderApplication.class, args);
    }
}
