package com.company.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: yaochunguang
 * @date: 2021-01-20 11:26
 * @description: 文件上传配置类
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "file.upload")
public class UploadFileProperties {
    private String path;
    private String relative;
}
