package com.company.handler;

import com.company.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author: yaochunguang
 * @date: 2021-01-20 11:07
 * @description: 上传处理handler;
 * 文件上传参考： https://www.cnblogs.com/zhainan-blog/p/11169163.html
 **/
@Controller
public class UploadHandler {

    private Logger logger = LoggerFactory.getLogger(UploadHandler.class);

    /**
     * 上传地址
     */
    @Value("${file.upload.path}")
    private String filePath;

    /**
     * 前往上传页面
     *
     * @return
     */
    @RequestMapping("/test/to/upload/page")
    public String toUploadPage() {
        return "uploadPage";
    }

    /**
     * 测试执行上传
     *
     * @param file
     * @param model
     * @return
     */
    @RequestMapping("/testUpload")
    public String testUpload(@RequestParam("file") MultipartFile file, Model model) {
        // 获取上传的文件名
        String filename = file.getOriginalFilename();
        // 定义上传文件保存路劲
        String path = filePath + "photo/";
        // 新建文件
        File filepath = new File(path, filename);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        try {
            // 写入文件
            file.transferTo(new File(path + File.separator + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将src路径发送至html页面
        model.addAttribute("filename", "/images/photo/" + filename);
        return "uploadPage";
    }
}
